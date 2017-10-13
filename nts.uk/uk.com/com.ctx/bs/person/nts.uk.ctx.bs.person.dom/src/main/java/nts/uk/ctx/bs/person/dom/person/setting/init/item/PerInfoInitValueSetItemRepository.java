package nts.uk.ctx.bs.person.dom.person.setting.init.item;

import java.util.List;

public interface PerInfoInitValueSetItemRepository {
	/**
	 * get all init value item
	 * @param initValueSettingCtgId
	 * @return
	 */
	List<PerInfoInitValueSetItem> getAllItem(String initValueSettingCtgId);
	
	boolean isExist(String perInfoCtgId);
	PerInfoInitValueSetItem getDetailItem(String initValueSettingCtgId, String perInfoItemDefId);

}
