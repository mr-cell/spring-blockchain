package mr.cell.springblockchain.validator;

import mr.cell.springblockchain.domain.Block;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Base64;
import java.util.List;

/**
 * Created by U517779 on 2017-09-27.
 */
public class BlockchainValidator {

	public boolean isValidProof(long lastProof, long proof) {
		String guess = Base64.getEncoder().encodeToString(("" + lastProof + proof).getBytes());
		String guessHash = DigestUtils.sha256Hex(guess);
		return guessHash.startsWith("0000");
	}

	public boolean isValidChain(List<Block> chain) {
		if(chain.isEmpty()) {
			return false;
		}

		Block previousBlock = chain.get(0);
		int currentIndex = 1;

		while(currentIndex < chain.size()) {
			Block block = chain.get(currentIndex);

			if( !block.getPreviousHash().equals(previousBlock.calculateHash())) {
				return false;
			}

			if( !isValidProof(previousBlock.getProof(), block.getProof())) {
				return false;
			}

			previousBlock = block;
			currentIndex++;
		}
		return true;
	}
}
