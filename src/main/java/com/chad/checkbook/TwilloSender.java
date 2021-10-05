package com.chad.checkbook;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.beans.factory.annotation.Autowired;

public class TwilloSender {

    @Autowired
    public static SystemConfig systemConfig;

    // Find your Account Sid and Token at twilio.com/console
    // and set the environment variables. See http://twil.io/secure

    public static final String ACCOUNT_SID = systemConfig.getSID();
    public static final String AUTH_TOKEN = systemConfig.getAUTH();

    public static void sendMessage(double amount) {
    	try {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("+19198193556"),
                new com.twilio.type.PhoneNumber("+12183068853"),
                "Your current balance is: $" + amount )
            .create();

        System.out.println(message.getSid());
    	} catch(Exception ex) {
    		ex.printStackTrace();
    	}
    }
    
    public static void sendMessage() {
    	try {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("+12183068853"),
                new com.twilio.type.PhoneNumber("+19198193556"),
                "How much?")
            .create();

        System.out.println(message.getSid());
    	} catch(Exception ex) {
    		ex.printStackTrace();
    	}
    }
}
