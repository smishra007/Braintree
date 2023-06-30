
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
import com.braintreegateway.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

class customReport {
    public static void main(String[] args) {
        System.out.println("Starting");

        generateCustomReport("./transaction_report.csv");
    }

    private static final Object [] HEADER = {"id", "type", "amount", "status", "created_at", "service_fee_amount", "merchant_account_id","customer_id"};

    private static void generateCustomReport(String fileName) {
        FileWriter writer = null;
        CSVPrinter csv = null;

        CSVFormat csvFormat = CSVFormat.DEFAULT.withRecordSeparator(",");

        try {
            writer = new FileWriter(fileName);
            csv = new CSVPrinter(writer, csvFormat);

            csv.printRecord(HEADER);

            Calendar dates = Calendar.getInstance();
            dates.add(Calendar.DATE, -1); // a day ago
            ResourceCollection<Transaction> collectionOfTransactions = getTransactions(dates);
            System.out.println("getTransactions");

            for (Transaction transaction : collectionOfTransactions) {
                List transactionData = getTransactionData(transaction);
                csv.printRecord(transactionData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }finally {
            try {
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static ResourceCollection<Transaction> getTransactions(Calendar dates)  {
        ResourceCollection<Transaction> collection = null;
        System.out.println("getTransactions");

        try {
            BraintreeGateway gateway = new BraintreeGateway(
                    Environment.SANDBOX, "7ccbtyskqh6jfh64",
                    "njr2ndt6vxjqmvc6",
                    "62af5b4e5d047df8cb59c615d2eb4e81");
            TransactionSearchRequest request = new TransactionSearchRequest()
                   .paymentInstrumentType().in("venmo");
            collection = gateway
                    .transaction()
                    .search(request);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return collection;
    }

    private static List getTransactionData(Transaction transaction) {
        List<String> transactionData = new ArrayList<String>();

        transactionData.add(String.valueOf(transaction.getId()));
        transactionData.add(String.valueOf(transaction.getType()));
        transactionData.add(String.valueOf(transaction.getStatus()));
        transactionData.add(String.valueOf(transaction.getCreatedAt().getTime()));
        transactionData.add(String.valueOf(transaction.getServiceFeeAmount()));
        transactionData.add(String.valueOf(transaction.getMerchantAccountId()));
        transactionData.add(String.valueOf(transaction.getCustomer().getId()));
        return transactionData;
    }
}

