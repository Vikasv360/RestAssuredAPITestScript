package resources;

import files.CustomerAuthorizationPojo;
import files.SubmitOrderRequestPojo;

public class TestDataBuild {

public CustomerAuthorizationPojo custAuth(String clientName,String clientEmail) {
		
		
		CustomerAuthorizationPojo cap = new CustomerAuthorizationPojo();
		cap.setClientName(clientName);
		cap.setClientEmail(clientEmail);
		
		return cap;
	}
	
	public SubmitOrderRequestPojo submitOrderReq(Integer bookid, String clientName) {
		
		SubmitOrderRequestPojo so = new SubmitOrderRequestPojo();
		so.setCustomerName(clientName);
		return so;
	}
}
