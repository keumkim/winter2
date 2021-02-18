package winterschoolone;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;

import winterschoolone.external.Coupon;
import winterschoolone.external.CouponService;
import winterschoolone.external.Payment;
import winterschoolone.external.PaymentService;

import java.util.List;

@Entity
@Table(name="SirenOrder_table")
public class SirenOrder {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String userId;
    private String menuId;
    private Integer qty;
    private String status;
    private String useCouponYN;
    private Integer couponQty;

    @PostPersist
    public void onPostPersist(){
    	Ordered ordered = new Ordered();
        BeanUtils.copyProperties(this, ordered);
        ordered.publishAfterCommit();

        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.
        if("Y".equals(this.getUseCouponYN()) && this.getCouponQty() >= this.getQty()) {//coupon 사
	        Coupon coupon = new Coupon();
	        coupon.setOrderId(this.getId());
	        coupon.setMenuId(this.menuId);
	        coupon.setQty(this.getQty());
	        coupon.setUserId(this.getUserId());
	        coupon.setCouponQty(this.getCouponQty() - this.getQty());
	        // mappings goes here
	        SirenOrderApplication.applicationContext.getBean(CouponService.class)
	        .use(coupon);
	        
        }else {
	        Payment payment = new Payment();
	        payment.setOrderId(this.getId());
	        payment.setMenuId(this.menuId);
	        payment.setQty(this.getQty());
	        payment.setUserId(this.getUserId());
	        // mappings goes here
	        SirenOrderApplication.applicationContext.getBean(PaymentService.class)
	        .pay(payment);
        }
    }

    @PostUpdate
    public void onPostUpdate(){
        Updated updated = new Updated();
        BeanUtils.copyProperties(this, updated);
        updated.publishAfterCommit();


    }

    @PreRemove
    public void onPreRemove(){
        OrderCancelled orderCancelled = new OrderCancelled();
        BeanUtils.copyProperties(this, orderCancelled);
        orderCancelled.publishAfterCommit();


    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }
    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getUseCouponYN() {
        return useCouponYN;
    }

    public void setUseCouponYN(String useCouponYN) {
        this. useCouponYN = useCouponYN;
    }
    public Integer getCouponQty() {
        return couponQty;
    }

    public void setCouponQty(Integer couponQty) {
        this.couponQty = couponQty;
    }




}