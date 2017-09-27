package mr.cell.springblockchain.service;

import lombok.extern.slf4j.Slf4j;
import mr.cell.springblockchain.domain.Block;
import mr.cell.springblockchain.domain.Node;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by U517779 on 2017-09-27.
 */
@Service
@Slf4j
public class NodeService {

	/**
	 *
	 * @param node
	 * @return
	 */
	public List<Block> requestChainFromNode(Node node) {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List<Block>> response = restTemplate.exchange(
				node.getAddress() + "/blockchain/chain",
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<List<Block>>() { });

		if (response.getStatusCode() != HttpStatus.OK) {
			return new ArrayList<>();
		}
		return response.getBody();
	}
}
