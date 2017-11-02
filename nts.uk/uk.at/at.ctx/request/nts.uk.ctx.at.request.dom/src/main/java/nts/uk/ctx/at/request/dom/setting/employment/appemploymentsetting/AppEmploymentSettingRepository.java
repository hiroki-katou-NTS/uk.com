package nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting;

import java.util.List;

public interface AppEmploymentSettingRepository {
	public List<AppEmployWorkType> getEmploymentWorkType(String companyID,String EmploymentCode, int appType);
}
