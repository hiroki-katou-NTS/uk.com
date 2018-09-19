package nts.uk.ctx.exio.dom.exo.outputitemorder;

import java.util.List;
import java.util.Optional;

/**
 * 出力項目並び順(定型)
 */
public interface StandardOutputItemOrderRepository {

	List<StandardOutputItemOrder> getAllStandardOutputItemOrder();

	Optional<StandardOutputItemOrder> getStandardOutputItemOrderById(String cid, String outputItemCode,
			String conditionSettingCode);

	List<StandardOutputItemOrder> getStandardOutputItemOrderByCidAndSetCd(String cid, String conditionSettingCode);

	void add(StandardOutputItemOrder domain);
	
	void add(List<StandardOutputItemOrder> domain);

	void update(StandardOutputItemOrder domain);

	void remove(String cid, String outputItemCode, String conditionSettingCode);
	
	void remove(String cid, String condSetCd);
}