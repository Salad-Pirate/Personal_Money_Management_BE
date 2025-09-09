package salad.example.pmm_be;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final JdbcTemplate jdbc;

    public TransactionController(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @PostMapping("/post-transaction")
    public Map<String,Object> addTransaction(@RequestBody TransactionCreateRequest req) {
        LocalDateTime ldt = LocalDateTime.parse(req.date);
        Timestamp txTime = Timestamp.from(ldt.atZone(ZoneId.systemDefault()).toInstant());

        int rows = jdbc.update(
                "insert into transactions (user_id, amount, type, payment_method, transaction_time, location, note) " +
                        "values (?, ?, ?, ?, ?, ?, ?)",
                1L, req.amount, req.type.toLowerCase(), req.paymentMethod, txTime, req.location, req.note
        );

        return Map.of("status","ok","rows",rows);
    }

    @GetMapping("/get-transactions")
    public List<Map<String, Object>> getTransactions() {
        return jdbc.queryForList("select * from transactions");
    }

}
