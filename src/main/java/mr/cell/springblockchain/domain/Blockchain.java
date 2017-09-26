package mr.cell.springblockchain.domain;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.*;

/**
 * Created by U517779 on 2017-09-26.
 */
@Slf4j
public class Blockchain {

	@Getter
	private List<Block> chain;

	@Getter
	private Collection<Transaction> currentTransactions;

	public static String calculateHash(Block block) {
		String encodedBlock = Base64.getEncoder().encodeToString(block.toString().getBytes());
		return DigestUtils.sha256Hex(encodedBlock);
	}

	public Blockchain() {
		chain = new ArrayList<>();
		currentTransactions = new ArrayList<>();
		createNewBlock(100, "1");
	}

	public Block createNewBlock(long proof) {
		log.info("Creating new block");
		String previousHash = Blockchain.calculateHash(getLastBlock());
		return createNewBlock(proof, previousHash);
	}

	public Block createNewBlock(long proof, String previousHash) {
		log.info("Creating new block");
		Block block = new Block(getNextIndex(), new Date(), currentTransactions, proof, previousHash);
		currentTransactions = new ArrayList<>();
		chain.add(block);
		return block;
	}

	public int addTransaction(Transaction transaction) {
		log.info("Adding transaction");
		currentTransactions.add(transaction);
		return getNextIndex();
	}

	private int getNextIndex() {
		log.info("Getting next index");
		return getLastBlock() == null ?
				1 :
				getLastBlock().getIndex() + 1;
	}

	public Block getLastBlock() {
		log.info("Getting last block");
		return chain.isEmpty() ?
				null :
				chain.get(chain.size() - 1);
	}

	public long calculateProofOfWork(long lastProof) {
		log.info("Calculating proof of work");
		long proof = 0;
		while(!isValidProof(lastProof, proof)) {
			proof++;
		}
		return proof;
	}

	public boolean isValidProof(long lastProof, long proof) {
		String guess = Base64.getEncoder().encodeToString(("" + lastProof + proof).getBytes());
		String guessHash = DigestUtils.sha256Hex(guess);
		return guessHash.startsWith("0000");
	}
}
