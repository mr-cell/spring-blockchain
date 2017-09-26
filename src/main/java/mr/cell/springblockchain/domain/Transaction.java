package mr.cell.springblockchain.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by U517779 on 2017-09-26.
 */
@Data
@AllArgsConstructor
public class Transaction {
	private String sender;
	private String recipient;
	private BigDecimal amount;
}
