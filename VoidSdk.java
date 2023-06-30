import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.ClientTokenRequest;
import com.braintreegateway.Environment;
import com.braintreegateway.Transaction;
import com.braintreegateway.Result;
import com.braintreegateway.ValidationError;
public class VoidSdk{
    public static void main(String args[]) {

        BraintreeGateway gateway = new BraintreeGateway(Environment.SANDBOX, "7ccbtyskqh6jfh64", "njr2ndt6vxjqmvc6", "62af5b4e5d047df8cb59c615d2eb4e81");
        String transactionID = "hgebqf8n";
        // Void Process
        Result<Transaction> result = gateway.transaction().voidTransaction(transactionID);
        if (result.isSuccess()) {
            System.out.println("Success");
            // transaction successfully voided
        } else {
            for (ValidationError error : result.getErrors().getAllDeepValidationErrors()) {
                System.out.println(error.getMessage());
            }
        }
        /*// Refund Process
        Result<Transaction> result_return = gateway.transaction().refund(transactionID);
        if(result_return.isSuccess()){
            System.out.println("Success");
        }else {
            for (ValidationError error : result_return.getErrors().getAllDeepValidationErrors()) {
                System.out.println(error.getMessage());
            }
        }*/
    }
}