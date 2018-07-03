package nts.uk.ctx.exio.dom.exo.outitemsortorder;

import java.util.List;
import java.util.Optional;

/**
 * 出力項目並び順(定型)
 */
public interface StdOutItemOrderRepository {

	List<StdOutItemOrder> getAllStdOutItemOrder();

	Optional<StdOutItemOrder> getStdOutItemOrderById(String cid, String outItemCd, String condSetCd);

	void add(StdOutItemOrder domain);

	void update(StdOutItemOrder domain);

	void remove(String cid, String outItemCd, String condSetCd);
}