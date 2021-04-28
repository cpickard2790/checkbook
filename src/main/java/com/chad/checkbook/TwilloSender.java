package com.chad.checkbook;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class TwilloSender {
    // Find your Account Sid and Token at twilio.com/console
    // and set the environment variables. See http://twil.io/secure
    public static final String ACCOUNT_SID = "AC96ba09abd5d5cf9626296c19787b9cfd";
    public static final String AUTH_TOKEN = "df61fd6f00f7d2eb8efdc619e48d07e8";

    public static void sendMessage(double amount) {
    	try {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("+19198193556"),
                new com.twilio.type.PhoneNumber("+19709191073"),
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
                new com.twilio.type.PhoneNumber("+19198193556"),
                new com.twilio.type.PhoneNumber("+19709191073"),
                "How much?")
            .create();

        System.out.println(message.getSid());
    	} catch(Exception ex) {
    		ex.printStackTrace();
    	}
    }
}
