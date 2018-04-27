package nts.uk.ctx.pereg.dom.copysetting.item;

import java.util.List;

import nts.uk.ctx.pereg.dom.copysetting.setting.valueobject.CopySettingItemObject;

public interface EmpCopySettingItemRepository {

	List<EmpCopySettingItem> getAllItemFromCategoryCd(String categoryCd, String companyId, boolean isSelf);

	void removePerInfoItemInCopySetting(String itemId);

	void updatePerInfoItemInCopySetting(String perInforCtgId, List<String> perInfoItemDefIds);

	List<CopySettingItemObject> getPerInfoItemByCtgId(String perInfoCategoryId, String companyId, String contractCd);
	
}
