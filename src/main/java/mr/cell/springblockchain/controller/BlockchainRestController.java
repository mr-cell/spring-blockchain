package mr.cell.springblockchain.controller;

import lombok.extern.slf4j.Slf4j;
import mr.cell.springblockchain.domain.Block;
import mr.cell.springblockchain.domain.Blockchain;
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

	private Blockchain blockchain;

	public BlockchainRestController() {
		blockchain = new Blockchain();
	}

	@PostMapping("/transactions/new")
	public void createNewTransaction(@RequestBody Transaction transaction) {
		blockchain.addTransaction(transaction);
	}

	@GetMapping("/mine")
	public Block mineBlock() {
		Block lastBlock = blockchain.getLastBlock();
		Long lastProof = lastBlock.getProof();
		Long proof = blockchain.calculateProofOfWork(lastProof);

		blockchain.addTransaction(new Transaction("0", "1", new BigDecimal(1.0)));

		return blockchain.createNewBlock(proof);
	}

	@GetMapping("/chain")
	public List<Block> getBlockchain() {
		return blockchain.getChain();
	}

}
