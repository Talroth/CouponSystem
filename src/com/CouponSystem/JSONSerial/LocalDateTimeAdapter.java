package com.CouponSystem.JSONSerial;

import java.time.LocalDateTime;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {

    @Override
    public LocalDateTime unmarshal(String dateString) throws Exception {
        LocalDateTime dateTime = LocalDateTime.parse(dateString);
        return dateTime;
    }

    @Override
    public String marshal(LocalDateTime dateTime) throws Exception {
    	return dateTime.toString();
    }
}
