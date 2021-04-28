package com.chad.checkbook;

import com.chad.checkbook.model.Item;
import com.chad.checkbook.repository.ItemRepository;
import com.chad.checkbook.service.ItemService;
import com.twilio.twiml.MessagingResponse;
import com.twilio.twiml.messaging.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
public class SmsWebHookHandler extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	HttpSession session;
	
	@Autowired
	ItemRepository repository;
	
	@Autowired
	ItemService service;
	
    private final Map<String, Integer> messageCounts = new ConcurrentHashMap<>();

    @PostMapping(value = "/", produces = "application/xml")
    @ResponseBody
    public String handleSmsWebhook(
    	HttpServletRequest request,
    	HttpServletResponse response,
        @RequestParam("From") String from,
        @RequestParam("Body") String body) {
    	
    	HttpSession session = request.getSession(true);
    	
        int thisMessageCount = messageCounts.compute(from, (k,v) -> (v == null) ? 1 : v+1);

        String plural = (thisMessageCount > 1) ? "messages" : "message";
        String message = String.format(
            "☎️ Hello from Twilio. You've sent %d %s, and this one said '%s'",
            thisMessageCount, plural, body);
        
        String messageTwo = "How much";

        Integer counter = (Integer) session.getAttribute("counter");

        if(message.toLowerCase().contains("balance")) {
        	return new MessagingResponse.Builder()
					.message(new Message.Builder("Current balance: " + String.valueOf(service.getCurrentBalance())).build())
					.build().toXml();
		}

        if (message.contains("deposit")) {
        	session = request.getSession();
            if (counter == null) {
            	counter = 0;
            	int count = 0;
            	count++;
            	session.setAttribute("counter", count);
            	Item item = new Item();
            	item.setType("deposit");
                session.setAttribute("item", item);
                return new MessagingResponse.Builder()
            			.message(new Message.Builder("How much?").build())
            			.build().toXml();
            }
        }
        
        if (startsWithNumber(body) && (counter == 1)) {
        	double amount = Double.parseDouble(body);
        	Item item = (Item) session.getAttribute("item");
        	item.setDeposit(amount);
        	int count = counter.intValue();
        	count++;
        	session.setAttribute("counter", count);
        	return new MessagingResponse.Builder()
        			.message(new Message.Builder("Description?").build())
        			.build().toXml();
        }
        
        if (counter == 2) {
        	String description = body.toString();
        	Item item = (Item) session.getAttribute("item");
        	item.setDescription(description);
        	int count = counter.intValue();
        	count++;
        	session.setAttribute("counter", count);

		}
    	return new MessagingResponse.Builder()
                .message(new Message.Builder(message).build())
                .build().toXml();
        
    }
    
    public boolean startsWithNumber(String body) {
    	char ch = body.charAt(0);
    	if (Character.isDigit(ch))
    		return true;
    	
    	return false;
    }
}
