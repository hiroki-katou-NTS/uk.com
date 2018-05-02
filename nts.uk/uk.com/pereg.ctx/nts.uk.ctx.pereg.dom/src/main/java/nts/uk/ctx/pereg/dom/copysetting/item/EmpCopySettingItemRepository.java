package nts.uk.ctx.pereg.dom.copysetting.item;

import java.util.List;

public interface EmpCopySettingItemRepository {

	List<EmpCopySettingItem> getAllItemFromCategoryCd(String categoryCd, String companyId, boolean isSelf);

	void removePerInfoItemInCopySetting(String itemId);

	void updatePerInfoItemInCopySetting(String perInforCtgId, List<String> perInfoItemDefIds);

}
