import com.braintreegateway.TransactionRequest;
import com.braintreegateway.Transaction;
import com.braintreegateway.Result;
import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Environment;

import java.math.BigDecimal;


public class SubmitSettlement {
    public static  void main(String args[]) {
        BraintreeGateway gateway = new BraintreeGateway(Environment.SANDBOX, "7ccbtyskqh6jfh64", "njr2ndt6vxjqmvc6", "62af5b4e5d047df8cb59c615d2eb4e81");

        TransactionRequest request = new TransactionRequest()
                .amount(new BigDecimal("17.00"));
               // .orderId("");
        Result<Transaction> resultPartial = gateway.transaction().submitForPartialSettlement(
                "88bhjtav",
                request);
        if (resultPartial.isSuccess()) {
            Transaction settledTransaction = resultPartial.getTarget();
            System.out.println(resultPartial.getMessage());
        } else {
            System.out.println(resultPartial.getErrors());
        }
    }

}
