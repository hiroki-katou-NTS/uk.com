package nts.uk.ctx.bs.person.dom.person.setting.copysetting;

import java.util.List;

import nts.uk.ctx.bs.person.dom.person.info.setting.copysetting.EmpCopySetting;

public interface EmpCopySettingRepository {

	List<EmpCopySetting> find(String companyId);
}
