package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeOfManagePeriod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.affiliation.AffiliationInfoOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.anyitem.AnyItemOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.editstate.EditStateOfMonthlyPerformance;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.erroralarm.EmployeeMonthlyPerError;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.weekly.AttendanceTimeOfWeekly;

import java.util.List;

/**
 * 月別実績(Work)
 */
@Getter
@AllArgsConstructor
public class MonthlyPerformance {

	/** 36協定時間 */
	private List<AgreementTimeOfManagePeriod> agreementTimeList;

	/** エラー一覧 */
	private List<EmployeeMonthlyPerError> employeeMonthlyPerErrorList;


	/** 任意項目 */
	private List<AnyItemOfMonthly> anyItemList;

	/** 勤怠時間 */
	private AttendanceTimeOfMonthly attendanceTime;

	/** 所属情報 */
	private AffiliationInfoOfMonthly affiliationInfo;

	/** 週別実績の勤怠時間 */
	private List<EditStateOfMonthlyPerformance> stateOfMonthlyPerformances;

	/** 週別実績 */
	private List<AttendanceTimeOfWeekly> attendanceTimeOfWeekList;

}
