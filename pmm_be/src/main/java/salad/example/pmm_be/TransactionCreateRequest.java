package salad.example.pmm_be;

public class TransactionCreateRequest {
    public String type;          // "income" | "expense"
    public Double amount;
    public String paymentMethod;
    public String date;          // "YYYY-MM-DDTHH:mm"
    public String location;
    public String note;
}