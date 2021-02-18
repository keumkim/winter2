package winterschoolone;

public class Issued extends AbstractEvent {

    private Long id;
    private Long orderId;
    private String userId;
    private String menuId;
    private Integer qty;
    private Integer stampQty;
    private Integer couponQty;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
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
    

    public Integer getStampQty() {
        return stampQty;
    }

    public void setStampQty(Integer stampQty) {
        this.stampQty = stampQty;
    }
    public Integer getCouponQty() {
        return couponQty;
    }

    public void setCouponQty(Integer couponQty) {
        this.couponQty = couponQty;
    }
}