package nts.uk.ctx.pereg.dom.copysetting.setting;

import java.util.List;
import java.util.Optional;

public interface EmpCopySettingRepository {

	List<EmpCopySetting> find(String companyId);
	
	Optional<EmployeeCopySetting> findSetting(String companyId);
	
	Optional<EmployeeCopyCategory> findCopyCategory(String companyId, String categoryId);
	
	void addCopyCategory(EmployeeCopyCategory copyCategory);
	
	void removeCopyCategory(String categoryId);

	void updatePerInfoCtgInCopySetting(String perInfoCtgId, String companyId);

	boolean checkPerInfoCtgAlreadyCopy(String perInfoCtgId, String companyId);

	void addCtgCopySetting(EmpCopySetting newCtg);

	void removeCtgCopySetting(String perInfoCtgId);
}
