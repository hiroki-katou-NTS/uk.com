package nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting;

import java.util.List;

public interface AppEmploymentSettingRepository {
	/**
	 * 勤務種類リスト
	 * @param companyID
	 * @param EmploymentCode
	 * @param appType
	 * @return
	 */
	public List<AppEmployWorkType> getEmploymentWorkType(String companyID,String EmploymentCode, int appType);
	/**
	 * 雇用別申請承認設定
	 * @param companyID
	 * @param EmploymentCode
	 * @param appType
	 * @return
	 */
	public List<AppEmploymentSetting> getEmploymentSetting(String companyID,String EmploymentCode, int appType);
}
