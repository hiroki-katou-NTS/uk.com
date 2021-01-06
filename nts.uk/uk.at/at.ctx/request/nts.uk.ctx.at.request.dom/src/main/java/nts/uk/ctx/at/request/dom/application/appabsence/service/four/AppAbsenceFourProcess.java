package nts.uk.ctx.at.request.dom.application.appabsence.service.four;

import java.util.Optional;

import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting.HolidayApplicationSetting;

/**
 * @author loivt
 * 4.就業時間帯を取得する
 */
public interface AppAbsenceFourProcess {
	/**
	 * 1.就業時間帯の表示制御(xu li hien thị A6_1)
	 * @param workTypeCd
	 * @param hdAppSet
	 * @param companyID
	 * @return
	 */
	public boolean getDisplayControlWorkingHours(String workTypeCd, Optional<HolidayApplicationSetting> hdAppSet, String companyID);
}
