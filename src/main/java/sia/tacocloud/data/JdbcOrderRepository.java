package sia.tacocloud.data;

import org.springframework.jdbc.core.JdbcOperations;
import sia.tacocloud.TacoOrder;

public class JdbcOrderRepository implements OrderRepository {
    private JdbcOperations jdbcOperations;

    public JdbcOrderRepository(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public TacoOrder save(TacoOrder order) {
        return null;
    }
}
