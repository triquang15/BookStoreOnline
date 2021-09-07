package com.triquang.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Details;
import com.paypal.api.payments.Item;
import com.paypal.api.payments.ItemList;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.PayerInfo;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.ShippingAddress;
import com.paypal.api.payments.Transaction;
import com.paypal.base.exception.PayPalException;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.triquang.entity.Book;
import com.triquang.entity.BookOrder;
import com.triquang.entity.Customer;
import com.triquang.entity.OrderDetail;

public class PaymentServices {
	private static final String CLIENT_ID = "AeWpNhJLY_mioNSgx3ui7MWuVC6EMB9Qdt3zHH0T5s6EUC9xRn-cYG1PHsvDjY6WPqPtCYCpzgkj_EE7";
	private static final String CLIENT_SECRET = "EC0FUhda46ekpTd865QRvYr17zG60iTH6ycr1K8n1K_DFbrfUrkqLg4SVuvKZI2SdVol1u9rpdXtkQdz";
	private String mode = "sandbox";

	private HttpServletRequest request;
	private HttpServletResponse response;

	public PaymentServices(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}

	public void authorizePayment(BookOrder bookOrder) throws ServletException {

		Payer payer = getPayerInformation(bookOrder);
		RedirectUrls redirectUrls = getRedirectUrls();
		
		List<Transaction> transactions = getTransactionInformation(bookOrder);
		
		// Request payment
		Payment requestPayment = new Payment();
		requestPayment.setPayer(payer)
					  .setRedirectUrls(redirectUrls)
					  .setIntent("authorize")
					  .setTransactions(transactions);
		
		System.out.println("============ REQUEST PAYMENT: =============");
		System.out.println(requestPayment);
		
		APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, mode);
		try {
			Payment authorizePayment = requestPayment.create(apiContext);
			System.out.println("============ AUTHORIZED PAYMENT: =============");
			System.out.println(authorizePayment);
			
			String approvalURL = getApprovalURL(authorizePayment);
			
			response.sendRedirect(approvalURL);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ServletException("Error in authorizing payment.");
		}
	
	}
	
	private String getApprovalURL(Payment authorizePayment) {
		// TODO Auto-generated method stub
		String approvalURL = null;
		
		List<Links> links = authorizePayment.getLinks();
		
		for (Links link : links) {
			if(link.getRel().equalsIgnoreCase("approval_url"));
			approvalURL = link.getHref();
			break;
		}
		return approvalURL;
	}

	private List<Transaction> getTransactionInformation(BookOrder bookOrder) {
		Transaction transaction = new Transaction();
		transaction.setDescription("Books ordered on Evergreen Books");
		Amount amount = getAmountDetails(bookOrder);
		transaction.setAmount(amount);
		
		ItemList itemList = new ItemList();
		ShippingAddress shippingAddress = getRecipientInformation(bookOrder);
		itemList.setShippingAddress(shippingAddress);
		
		List<Item> paypalList = new ArrayList<>();
		Iterator<OrderDetail> iterator = bookOrder.getOrderDetails().iterator();
		
		while(iterator.hasNext()) {
			OrderDetail orderDetail = iterator.next();
			Book book = orderDetail.getBook();
			Integer quantity = orderDetail.getQuantity();
			
			Item item = new Item();
			item.setCurrency("USD")
			.setName(book.getTitle())
			.setQuantity(String.valueOf(quantity))
			.setPrice(String.valueOf(book.getPrice()));
			
			paypalList.add(item);
		}
		
		itemList.setItems(paypalList);
		transaction.setItemList(itemList);
		
		List<Transaction> listTransactions = new ArrayList<>();
		listTransactions.add(transaction);
		
		return listTransactions;
		
	}
	
	private ShippingAddress getRecipientInformation(BookOrder bookOrder) {
		ShippingAddress shippingAddress = new ShippingAddress();
		String recipientName = bookOrder.getFirstname() + " " + bookOrder.getLastname();
		shippingAddress.setRecipientName(recipientName)
						.setPhone(bookOrder.getPhone())
						.setLine1(bookOrder.getAddressLine1())
						.setLine2(bookOrder.getAddressLine2())
						.setCity(bookOrder.getCity())
						.setState(bookOrder.getState())
						.setCountryCode(bookOrder.getCountry())
						.setPostalCode(bookOrder.getZipcode());
		
		
		return shippingAddress;
	}

	private Payer getPayerInformation(BookOrder bookOrder) {
		// Get payer infomation
		Payer payer = new Payer();
		payer.setPaymentMethod("paypal");

		Customer customer = bookOrder.getCustomer();

		PayerInfo payerInfo = new PayerInfo();
		payerInfo.setFirstName(customer.getFirstname());
		payerInfo.setLastName(customer.getLastname());
		payerInfo.setEmail(customer.getEmail());
		payer.setPayerInfo(payerInfo);

		return payer;

	}

	public RedirectUrls getRedirectUrls() {
		// get redirect URLs
		
		String requestURL = request.getRequestURL().toString();
		String requestURI = request.getRequestURI();
		String baseURL = requestURL.replace(requestURI, "").concat(request.getContextPath());

		RedirectUrls redirectUrls = new RedirectUrls();
		String cancelUrl = baseURL.concat("/view_cart");
		String returnUrl = baseURL.concat("/review_payment");
		
		System.out.println("Return URL: " + returnUrl);
		System.out.println("Cancel URI: " + cancelUrl);

		redirectUrls.setCancelUrl(cancelUrl);
		redirectUrls.setReturnUrl(returnUrl);
		
		return redirectUrls;
	}
	
	private Amount getAmountDetails(BookOrder bookOrder) {
		// get amount detail
		Details details = new Details();
		details.setShipping(String.valueOf( bookOrder.getShippingFee()));
		details.setTax(String.valueOf( bookOrder.getTax()));
		details.setSubtotal(String.valueOf(  bookOrder.getSubtotal()));

		Amount amount = new Amount();
		amount.setCurrency("USD");
		amount.setDetails(details);
		amount.setTotal(String.valueOf( bookOrder.getTotal()));

		return amount;
	}

	public void reviewPayment() throws ServletException {
		// TODO Auto-generated method stub
		String paymentId = request.getParameter("paymentId");
		String payerId = request.getParameter("PayerID");
		
		if(payerId == null || payerId == null) {
			throw new ServletException("Error");
		}
		
		APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, mode);
		try {
			
			Payment payment = Payment.get(apiContext, paymentId);
			PayerInfo payerInfo = payment.getPayer().getPayerInfo();
			Transaction transaction = payment.getTransactions().get(0);
			ShippingAddress shippingAddress = transaction.getItemList().getShippingAddress();
			
			request.setAttribute("payer", payerInfo);
			request.setAttribute("recipient", shippingAddress);
			request.setAttribute("transaction", transaction);
			
			
			String reviewPage = "frontend/review_payment.jsp?paymentId" + payerId + "&PayerID=" + payerId;
			request.getRequestDispatcher(reviewPage).forward(request, response);
			
		} catch (PayPalRESTException | IOException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ServletException("Error in getting payment details from Paypal.");
		}
		
	}

	public Payment executePayment() throws PayPalRESTException {
		String paymentId = request.getParameter("paymentId");
		String payerId = request.getParameter("PayerID");
		
		PaymentExecution paymentExecution = new PaymentExecution();
		paymentExecution.setPayerId(payerId);
		
		Payment payment = new Payment().setId(paymentId);
		
		APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, mode);
		
		return payment.execute(apiContext, paymentExecution);
		
	}

}
