package com.chad.checkbook;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class TwilloSender {
    // Find your Account Sid and Token at twilio.com/console
    // and set the environment variables. See http://twil.io/secure
    public static final String ACCOUNT_SID = "";
    public static final String AUTH_TOKEN = "";

    public static void sendMessage(double amount) {
    	try {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("+"),
                new com.twilio.type.PhoneNumber("+"),
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
                new com.twilio.type.PhoneNumber("+"),
                new com.twilio.type.PhoneNumber("+"),
                "How much?")
            .create();

        System.out.println(message.getSid());
    	} catch(Exception ex) {
    		ex.printStackTrace();
    	}
    }
}
