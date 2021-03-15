package nts.uk.cnv.dom.td.event;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * 発注する
 * @author ai_muto
 *
 */
@Stateless
public class OrderService {

	@Inject
	private OrderEventRepository repo;

	public OrderedResult order(Require require, List<String> alterations) {
		// TODO:
		return null;
	}

	public interface Require{

	}
}
