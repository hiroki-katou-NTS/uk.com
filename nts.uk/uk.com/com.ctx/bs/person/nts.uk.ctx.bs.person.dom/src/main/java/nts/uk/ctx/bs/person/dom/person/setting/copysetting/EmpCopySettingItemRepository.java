package nts.uk.ctx.bs.person.dom.person.setting.copysetting;

import java.util.List;

import nts.uk.ctx.bs.person.dom.person.info.setting.copysetting.EmpCopySettingItem;

public interface EmpCopySettingItemRepository {

	List<EmpCopySettingItem> getAllItemFromCategoryCd(String categoryCd, String companyId, boolean isSelf);
}
