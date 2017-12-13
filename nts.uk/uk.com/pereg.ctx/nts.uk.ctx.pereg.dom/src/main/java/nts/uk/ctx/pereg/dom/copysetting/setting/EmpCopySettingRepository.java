package nts.uk.ctx.pereg.dom.copysetting.setting;

import java.util.List;

public interface EmpCopySettingRepository {

	List<EmpCopySetting> find(String companyId);

	void updatePerInfoCtgInCopySetting(String perInfoCtgId, String companyId);

	boolean checkPerInfoCtgAlreadyCopy(String perInfoCtgId, String companyId);

	void addCtgCopySetting(EmpCopySetting newCtg);

	void removeCtgCopySetting(String perInfoCtgId);
}
