
package winterschoolone.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient(name="Coupon", url="${api.url.Coupon}")
public interface CouponService {

    @RequestMapping(method= RequestMethod.POST, path="/coupons")
    public void use(@RequestBody Coupon coupon);

}