package nts.uk.ctx.pereg.dom.copysetting.setting;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.pereg.dom.copysetting.setting.valueobject.CopySettingItemObject;

public interface EmpCopySettingRepository {

	Optional<EmployeeCopySetting> findSetting(String companyId);
	
	Optional<EmployeeCopyCategory> findCopyCategory(String companyId, String categoryId);
	
	List<CopySettingItemObject> getPerInfoItemByCtgId(String companyId, String perInfoCategoryId);
	
	void addCopyCategory(EmployeeCopyCategory copyCategory);
	
	void removeCopyCategory(String categoryId);

	void updatePerInfoCtgInCopySetting(String perInfoCtgId, String companyId);

	boolean checkPerInfoCtgAlreadyCopy(String perInfoCtgId, String companyId);

}
