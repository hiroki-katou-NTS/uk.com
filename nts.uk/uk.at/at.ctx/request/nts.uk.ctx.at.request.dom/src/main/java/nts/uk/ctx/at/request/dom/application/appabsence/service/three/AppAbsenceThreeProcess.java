package nts.uk.ctx.at.request.dom.application.appabsence.service.three;

import java.util.List;

import nts.uk.ctx.at.request.dom.application.appabsence.AbsenceWorkType;
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
	public List<AbsenceWorkType> getWorkTypeCodes(List<AppEmploymentSetting> appEmploymentWorkType,String companyID,String employeeID,int holidayType,int alldayHalfDay);
	/**
	 * 2.勤務種類を取得する（詳細）
	 * @param appEmploymentWorkType
	 * @param companyID
	 * @param employeeID
	 * @return list workType
	 */
	public List<WorkType> getWorkTypeDetails(List<AppEmploymentSetting> appEmploymentWorkType,String companyID,String employeeID,int holidayType,int alldayHalfDay);

}
