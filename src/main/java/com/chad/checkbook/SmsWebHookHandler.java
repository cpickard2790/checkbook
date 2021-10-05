package com.chad.checkbook;

import com.chad.checkbook.model.Bill;
import com.chad.checkbook.model.Item;
import com.chad.checkbook.repository.ItemRepository;
import com.chad.checkbook.service.BillService;
import com.chad.checkbook.service.ItemService;
import com.twilio.twiml.MessagingResponse;
import com.twilio.twiml.messaging.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
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

	@Autowired
	BillService billService;
	
    private final Map<String, Integer> messageCounts = new ConcurrentHashMap<>();

    @PostMapping(value = "/", produces = "application/xml")
    @ResponseBody
    public String handleSmsWebhook(
    	HttpServletRequest request,
    	HttpServletResponse response,
        @RequestParam("From") String from,
        @RequestParam("Body") String body) {

		HttpSession session = request.getSession(true);

		Integer counter = (Integer) session.getAttribute("counter");

		if (body.toLowerCase().contains("balance")) {
			return new MessagingResponse.Builder()
					.message(new Message.Builder("Current balance: " + service.getCurrentBalance()).build())
					.build().toXml();
		} else if (body.toLowerCase().contains("deposit") && Character.isWhitespace(body.charAt(7))) {
			int length = body.length();
			double deposit = Double.parseDouble(body.substring(8, length));
			Item item = new Item("deposit");
			item.setDeposit(deposit);
			item.setWithdraw(0.0);
			service.calculateDeposit(item);
			return new MessagingResponse.Builder()
					.message(new Message.Builder("You deposited: " + deposit).build())
					.build().toXml();
		} else if (body.toLowerCase().contains("withdraw") && Character.isWhitespace(body.charAt(8))) {
			int length = body.length();
			try {
				double withdraw = Double.parseDouble(body.substring(9, length));
				Item item = new Item("withdraw");
				item.setWithdraw(withdraw);
				item.setDeposit(0.0);
				service.calculateWithdraw(item);
				return new MessagingResponse.Builder()
						.message(new Message.Builder("You withdrew: " + withdraw).build())
						.build().toXml();
			} catch(Exception e) {
				return new MessagingResponse.Builder()
						.message(new Message.Builder("Not a recognized command, try again...").build())
						.build().toXml();
			}
		} else if (body.toLowerCase().contains("due")) {
			List<Bill> list = billService.getBillsDueToday();
			StringBuilder due = new StringBuilder("Due today: \n");
			for (Bill b: list) {
				due.append(b.getName());
				due.append(" - $").append(b.getAmount()).append("\n");
			}
			return new MessagingResponse.Builder()
					.message(new Message.Builder(due.toString()).build())
					.build().toXml();
		}
		else {
			return new MessagingResponse.Builder()
					.message(new Message.Builder("Not a recognized command, try one of the following:\n\n" +
							"Balance\n" +
							"Deposit 1050.28\n" +
							"Withdraw 55.34\n" +
							"Due\n\n" +
							"You can also visit www.twilio.com").build())
					.build().toXml();
		}
	}
/*
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
    */
}
