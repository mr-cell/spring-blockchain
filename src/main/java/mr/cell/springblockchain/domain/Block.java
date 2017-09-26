package mr.cell.springblockchain.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collection;
import java.util.Date;

/**
 * Created by U517779 on 2017-09-26.
 */
@Data
@AllArgsConstructor
public class Block {
	private final int index;
	private final Date timestamp;
	private final Collection<Transaction> transactions;
	private final Long proof;
	private final String previousHash;
}
