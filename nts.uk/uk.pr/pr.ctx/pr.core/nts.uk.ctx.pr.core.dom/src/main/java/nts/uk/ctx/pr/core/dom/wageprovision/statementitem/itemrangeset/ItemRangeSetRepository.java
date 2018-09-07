package nts.uk.ctx.pr.core.dom.wageprovision.statementitem.itemrangeset;

import java.util.List;
import java.util.Optional;

/**
 * 項目範囲設定初期値
 */
public interface ItemRangeSetRepository {

	List<ItemRangeSet> getAllItemRangeSetInit();

	Optional<ItemRangeSet> getItemRangeSetInitById(String cid, String salaryItemId);

	void add(ItemRangeSet domain);

	void update(ItemRangeSet domain);

	void remove(String cid, String salaryItemId);

}
