package mr.cell.springblockchain.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Created by U517779 on 2017-09-27.
 */
@Data
@AllArgsConstructor
public class ConsensusResult {
	String message;
	private List<Block> chain;
}
