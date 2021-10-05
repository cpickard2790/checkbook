package com.chad.checkbook;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SystemConfig {

    @Value("${app.twilio_sid}")
    private String twilioSID;

    @Value("${app.twilio_auth}")
    private String twilioAUTH;

    public String getSID() {
        return twilioSID;
    }

    public String getAUTH() {
        return twilioAUTH;
    }
}
