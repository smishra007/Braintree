import com.braintreegateway.TransactionRequest;
import com.braintreegateway.Transaction;
import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Environment;
import com.braintreegateway.Result;

import java.math.BigDecimal;


public class Vault {
    public static void main(String args[]){
        BraintreeGateway gateway = new BraintreeGateway(
                Environment.SANDBOX,
                "7ccbtyskqh6jfh64",
                "njr2ndt6vxjqmvc6",
                "62af5b4e5d047df8cb59c615d2eb4e81"
        );
        TransactionRequest request = new TransactionRequest()
                .amount(new BigDecimal(100))
                .paymentMethodNonce("d09fbe74-68db-076f-c1d2-de7d6e764ea3")
                .deviceData("d5bdef8d746b6d7a040a5ef2cf2e2ca8")
                .options()
                .submitForSettlement(true)
                .paypal()
                .done()
                .storeInVaultOnSuccess(true)
                .done();

        Result<Transaction> result = gateway.transaction().sale(request);

        if (result.isSuccess()) {
            Transaction transaction = result.getTarget();
            System.out.println("Success ID: " + transaction.getId());
        } else {
            System.out.println("Message: " + result.getMessage());
        }

    }
}
