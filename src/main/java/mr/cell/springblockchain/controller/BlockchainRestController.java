package mr.cell.springblockchain.controller;

import lombok.extern.slf4j.Slf4j;
import mr.cell.springblockchain.domain.Block;
import mr.cell.springblockchain.service.BlockchainService;
import mr.cell.springblockchain.domain.Transaction;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by U517779 on 2017-09-26.
 */
@RestController
@Slf4j
@RequestMapping("/blockchain")
public class BlockchainRestController {

	private BlockchainService blockchain;

	public BlockchainRestController(BlockchainService blockchain) {
		this.blockchain = blockchain;
	}

	@PostMapping("/transactions/new")
	public void createNewTransaction(@RequestBody Transaction transaction) {
		log.info("Creating new transaction: {}.", transaction);
		blockchain.addTransaction(transaction);
	}

	@GetMapping("/mine")
	public Block mineBlock() {
		log.info("Mining the next block.");
		Block lastBlock = blockchain.getLastBlock();
		Long lastProof = lastBlock.getProof();
		Long proof = blockchain.calculateProofOfWork(lastProof);

		blockchain.addTransaction(new Transaction("0", "1", new BigDecimal(1.0)));

		return blockchain.createNewBlock(proof);
	}

	@GetMapping("/chain")
	public List<Block> getBlockchain() {
		log.info("Returning blockchain.");
		return blockchain.getChain();
	}

}
