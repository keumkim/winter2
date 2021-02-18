package winterschoolone;

import winterschoolone.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{
    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }
    
    @Autowired
    CouponRepository couponRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverAssigned_(@Payload Assigned assigned){ //stampQty 증가
    	
    	if(assigned.isMe()){
        	Optional<Coupon> optional = couponRepository.findById(assigned.getOrderId());
        	if(optional != null && optional.isPresent())
        	{
        		Coupon coupon = optional.get();
        		
        		if(coupon.getStampQty()==null || coupon.getStampQty()==0) {
        			coupon.setStampQty(0);
        		}
        		if(coupon.getCouponQty()==null || coupon.getCouponQty()==0) {
        			coupon.setCouponQty(0);
        		}
        		
        		// stamp 추가 
        		coupon.setStampQty(coupon.getStampQty() + assigned.getQty());
        		
        		while(true) {
        			if(coupon.getStampQty()>=10) { //10개 이상일 경우 Coupon지급
        				coupon.setCouponQty(coupon.getCouponQty() + 1);
        				coupon.setStampQty(coupon.getStampQty() - 10);
        			}
        			else { 
        				break;
        			}			
        		}
        		
        		couponRepository.save(coupon);
        	}
            
            System.out.println("##### listener  : " + assigned.toJson());
        }
    }
  
}
