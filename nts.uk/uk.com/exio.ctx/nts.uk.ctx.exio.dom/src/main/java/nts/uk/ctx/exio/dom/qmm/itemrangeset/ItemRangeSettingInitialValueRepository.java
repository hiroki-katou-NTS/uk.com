package nts.uk.ctx.exio.dom.qmm.itemrangeset;

import java.util.List;
import java.util.Optional;

/**
 * 項目範囲設定初期値
 */
public interface ItemRangeSettingInitialValueRepository {

	List<ItemRangeSettingInitialValue> getAllItemRangeSetInit();

	Optional<ItemRangeSettingInitialValue> getItemRangeSetInitById(String cid, String salaryItemId);

	void add(ItemRangeSettingInitialValue domain);

	void update(ItemRangeSettingInitialValue domain);

	void remove(String cid, String salaryItemId);

}
