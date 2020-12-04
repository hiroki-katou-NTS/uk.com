package nts.uk.ctx.at.request.dom.application.appabsence.service.three;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.request.dom.application.appabsence.AbsenceWorkType;
import nts.uk.ctx.at.request.dom.application.appabsence.HolidayAppType;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting.HolidayApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

/**
 * @author loivt
 * 3.勤務種類を取得する
 */
public interface AppAbsenceThreeProcess {
	/**
	 * 1.勤務種類を取得する（新規）
	 * @param appEmploymentWorkType
	 * @param companyID
	 * @param employeeID
	 * @param holidayType
	 * @param alldayHalfDay
	 * @return
	 */
	public List<AbsenceWorkType> getWorkTypeCodes(List<AppEmploymentSetting> appEmploymentWorkType,
													String companyID,
													String employeeID,
													HolidayAppType holidayType,
													int alldayHalfDay,
													boolean displayHalfDayValue,
													Optional<HolidayApplicationSetting> hdAppSet);

	/**
	 * 2.勤務種類を取得する（詳細）
	 * @param appEmploymentWorkType
	 * @param companyID 会社ID
	 * @param holidayType 選択する休暇種類
	 * @param alldayHalfDay 終日休暇半日休暇区分
	 * @param displayHalfDayValue 勤務種類組み合わせ全表示チェック
	 * @return
	 */
	public List<WorkType> getWorkTypeDetails(AppEmploymentSetting appEmploymentWorkType,String companyID,HolidayAppType holidayType,int alldayHalfDay, boolean displayHalfDayValue);

}
