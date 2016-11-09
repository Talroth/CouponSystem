//package com.CouponSystem.Beans;
//
//import java.io.IOException;
//import java.time.LocalDateTime;
//
//import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
//
//import com.CouponSystem.JSONSerial.LocalDateTimeAdapter;
//import com.fasterxml.jackson.core.JsonParser;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.DeserializationContext;
//import com.fasterxml.jackson.databind.JsonDeserializer;
//
////TODO: delete it if at the end i won't use it
//public class CouponJsonDeserializer extends JsonDeserializer<Coupon> {
//
//	@Override
//	public Coupon deserialize(JsonParser jp, DeserializationContext context)
//			throws IOException, JsonProcessingException {
//		InnerPojo innerPojo = jp.readValueAs(InnerPojo.class);
//		return innerPojo.toPojo();
//	}
//	
//    private static class InnerPojo {
//    	public long id;
//    	public String title = null;
//    	@XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
//    	public LocalDateTime startDate;
//    	@XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
//    	public LocalDateTime endDate;
//    	public int amount = 0;
//    	public CouponType type = CouponType.OTHER;
//    	public String message = null;
//    	public double price = 0;
//    	public String image = "None";
//
//    	Coupon toPojo() {
//    		Coupon pojo = new Coupon();
//    		pojo.setId(id);
//    		pojo.setTitle(title);
//    		pojo.setStartDate(startDate);
//    		pojo.setEndDate(endDate);
//    		pojo.setAmount(amount);
//    		pojo.setType(type);
//    		pojo.setMessage(message);
//    		pojo.setPrice(price);
//    		pojo.setImage(image);
//    		
//    		System.out.println("ccc");
//    		if (startDate.isAfter(endDate) || endDate.isBefore(startDate))
//    		{
//    			throw new IllegalArgumentException();
//    		}
//            return pojo;
//        }
//
//    }
//
//}
