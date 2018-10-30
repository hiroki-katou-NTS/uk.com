package nts.uk.ctx.pr.core.dom.wageprovision.statementitem.breakdownitemset;

import java.util.List;
import java.util.Optional;

/**
 * 
 * @author thanh.tq 内訳項目設定
 *
 */
public interface BreakdownItemSetRepository {

	List<BreakdownItemSet> getAllBreakdownItemSt();
	
	List<BreakdownItemSet> getBreakdownItemStByStatementItemId(String cid, int categoryAtr, String itemNameCd);

	Optional<BreakdownItemSet> getBreakdownItemStById(String cid, int categoryAtr, String itemNameCd, String breakdownItemCode);

	void add(BreakdownItemSet domain);

	void update(BreakdownItemSet domain);

	void remove(String cid, int categoryAtr, String itemNameCd, String breakdownItemCode);
	
	void removeAll(String cid, int categoryAtr, String itemNameCd);

}
