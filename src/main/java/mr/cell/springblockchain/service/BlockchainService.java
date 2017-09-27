package mr.cell.springblockchain.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import mr.cell.springblockchain.domain.Block;
import mr.cell.springblockchain.domain.Node;
import mr.cell.springblockchain.domain.Transaction;
import mr.cell.springblockchain.validator.BlockchainValidator;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by U517779 on 2017-09-26.
 */
@Slf4j
@Service
public class BlockchainService {

	private NodeService nodeService;

	@Getter
	private List<Block> chain;

	@Getter
	private Collection<Transaction> currentTransactions;

	@Getter
	private Set<Node> nodes;

	private BlockchainValidator validator;

	public BlockchainService(NodeService nodeService) {
		chain = new ArrayList<>();
		currentTransactions = new ArrayList<>();
		nodes = new HashSet<>();
		this.nodeService = nodeService;
		validator = new BlockchainValidator();
		createNewBlock(100, "1");
	}

	public Block createNewBlock(long proof) {
		String previousHash = getLastBlock().calculateHash();
		return createNewBlock(proof, previousHash);
	}

	public Block createNewBlock(long proof, String previousHash) {
		Block block = new Block(getNextIndex(), new Date(), currentTransactions, proof, previousHash);
		currentTransactions = new ArrayList<>();
		chain.add(block);
		return block;
	}

	public int addTransaction(Transaction transaction) {
		currentTransactions.add(transaction);
		return getNextIndex();
	}

	private int getNextIndex() {
		return getLastBlock() == null ?
				1 :
				getLastBlock().getIndex() + 1;
	}

	public Block getLastBlock() {
		return chain.isEmpty() ?
				null :
				chain.get(chain.size() - 1);
	}

	public long calculateProofOfWork(long lastProof) {
		long proof = 0;
		while( !validator.isValidProof(lastProof, proof)) {
			proof++;
		}
		return proof;
	}

	public boolean resolveConflicts() {
		List<Block> theirsChain = nodes.stream()
				.map(nodeService::requestChainFromNode)
				.filter(validator::isValidChain)
				.max(Comparator.comparingInt(theirChain -> theirChain.size()))
				.orElse(new ArrayList<>());

		if(theirsChain.size() > chain.size()) {
			chain = theirsChain;
			return true;
		}

		return false;
	}
}
