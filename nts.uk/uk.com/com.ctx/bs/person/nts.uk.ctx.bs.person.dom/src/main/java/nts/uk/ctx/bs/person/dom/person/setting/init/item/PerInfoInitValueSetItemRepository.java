package nts.uk.ctx.bs.person.dom.person.setting.init.item;

import java.util.List;

public interface PerInfoInitValueSetItemRepository {
	
	List<PerInfoInitValueSetItem> getAllItem(String initValueSettingCtgId);
	
	
	PerInfoInitValueSetItem getDetailItem(String initValueSettingCtgId, String perInfoItemDefId);

}
