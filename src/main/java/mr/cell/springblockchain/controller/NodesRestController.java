package mr.cell.springblockchain.controller;

import lombok.extern.slf4j.Slf4j;
import mr.cell.springblockchain.service.BlockchainService;
import mr.cell.springblockchain.domain.ConsensusResult;
import mr.cell.springblockchain.domain.Node;
import org.springframework.web.bind.annotation.*;

/**
 * Created by U517779 on 2017-09-27.
 */
@RestController
@Slf4j
@RequestMapping("/nodes")
public class NodesRestController {

	private BlockchainService blockchain;

	public NodesRestController(BlockchainService blockchain) {
		this.blockchain = blockchain;
	}

	@PostMapping("/register")
	public void registerNewNode(@RequestBody Node node) {
		log.info("Registering new node: {}", node);
		blockchain.getNodes().add(node);
	}

	@GetMapping("/resolve")
	public ConsensusResult resolveChain() {
		log.info("Resolving chain conflicts.");
		if(blockchain.resolveConflicts()) {
			return new ConsensusResult("Our chain was replaced", blockchain.getChain());
		} else {
			return new ConsensusResult("Our chain is authoritative", blockchain.getChain());
		}
	}
}
