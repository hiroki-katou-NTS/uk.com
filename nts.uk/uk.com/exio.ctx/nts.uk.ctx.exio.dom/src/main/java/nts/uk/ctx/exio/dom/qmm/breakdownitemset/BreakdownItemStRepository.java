package nts.uk.ctx.exio.dom.qmm.breakdownitemset;

import java.util.Optional;
import java.util.List;

/**
 * 
 * @author thanh.tq 内訳項目設定
 *
 */
public interface BreakdownItemStRepository {

	List<BreakdownItemSt> getAllBreakdownItemSt();

	Optional<BreakdownItemSt> getBreakdownItemStById(String salaryItemId, int breakdownItemCode);

	void add(BreakdownItemSt domain);

	void update(BreakdownItemSt domain);

	void remove(String salaryItemId, int breakdownItemCode);

}
