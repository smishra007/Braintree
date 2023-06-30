import com.braintreegateway.*;

import java.math.BigDecimal;
import java.io.*;


public class SaleSdk {

    public static void main (String args[]) {
         String aCustomerId = "12345";
         BraintreeGateway gateway = new BraintreeGateway(
                Environment.SANDBOX,
                "7ccbtyskqh6jfh64",
                "njr2ndt6vxjqmvc6",
                "62af5b4e5d047df8cb59c615d2eb4e81"
        );
        ClientTokenRequest clientTokenRequest = new ClientTokenRequest()
                .customerId(aCustomerId);
        // pass clientToken to your front-end
       // String clientToken = gateway.clientToken().generate(clientTokenRequest);
        gateway.paymentMethodNonce();
        String nonceFromTheClient= "27ff9e53-73b0-04bc-cabf-aa9f9ccc0ee7";
       // String deviceDataFromTheClient="1fcf4cddd8c19b9adadf7af5bf1a07b5";
       CustomerRequest requestCust = new CustomerRequest()
                .firstName("Mark")
                .lastName("Don")
                .company("Jones Co.")
                .email("mark.Don3@example.com")
                .fax("419-555-1234")
                .phone("614-555-1234")
                .website("http://example.com");
        Result<Customer> resultCustomer = gateway.customer().create(requestCust);
        resultCustomer.isSuccess();
        resultCustomer.getTarget().getId();
        PaymentMethodRequest request = new PaymentMethodRequest()
                .customerId(resultCustomer.getTarget().getId())
                .paymentMethodNonce(nonceFromTheClient);
        Result<? extends PaymentMethod> result = gateway.paymentMethod().create(request);
       // if (result.isSuccess()) {
            TransactionRequest requestDone = new TransactionRequest()
                    .amount(new BigDecimal("80.00"))
                    .paymentMethodNonce(nonceFromTheClient)
                    //.deviceData(deviceDataFromTheClient)
                    .options()
                    .submitForSettlement(false)
                    .done();
            try {
                Result<Transaction> results = gateway.transaction().sale(requestDone);
                Transaction t = results.getTarget();
                String transactionID = t.getId();
                System.out.println("Result Object:::" + results);
                System.out.println("Value::"+results.getParameters());
                System.out.println("TransactionID::" + transactionID);
                //System.out.println("PayPal Details"+results.getTransaction().getPayPalDetails().getImplicitlyVaultedPaymentMethodToken());
               // System.out.println("Result Object:::" +results.getTransaction().getPayPalDetails().getImplicitlyVaultedPaymentMethodToken());
            } catch(Exception e){
                e.printStackTrace();
            }
     //   }
    }
}
