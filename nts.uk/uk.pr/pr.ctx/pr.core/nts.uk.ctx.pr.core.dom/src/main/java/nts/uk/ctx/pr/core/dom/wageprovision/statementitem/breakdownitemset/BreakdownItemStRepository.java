package nts.uk.ctx.pr.core.dom.wageprovision.statementitem.breakdownitemset;

import java.util.Optional;
import java.util.List;

/**
 * 
 * @author thanh.tq 内訳項目設定
 *
 */
public interface BreakdownItemStRepository {

	List<BreakdownItemSet> getAllBreakdownItemSt();

	Optional<BreakdownItemSet> getBreakdownItemStById(String salaryItemId, int breakdownItemCode);

	void add(BreakdownItemSet domain);

	void update(BreakdownItemSet domain);

	void remove(String salaryItemId, int breakdownItemCode);

}
