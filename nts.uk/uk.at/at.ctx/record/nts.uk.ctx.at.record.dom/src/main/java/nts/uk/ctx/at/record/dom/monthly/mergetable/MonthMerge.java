package nts.uk.ctx.at.record.dom.monthly.mergetable;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.affiliation.AffiliationInfoOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.agreement.AgreementTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.AggregateTotalTimeSpentAtWork;
import nts.uk.ctx.at.record.dom.monthly.calc.actualworkingtime.RegularAndIrregularTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.flex.FlexTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.AggregateTotalWorkingTime;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.hdwkandcompleave.HolidayWorkTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.overtime.OverTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.vacationusetime.VacationUseTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.excessoutside.ExcessOutsideWorkOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.VerticalTotalOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.leave.LeaveOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.medicaltime.MedicalTimeOfMonthly;

@Getter
@Setter
public class MonthMerge {
	
	MonthMergeKey monthMergeKey;
	/** KRCDT_MON_AGGR_ABSN_DAYS 30 **/
	AggregateAbsenceDaysMerge absenceDaysMerge;
	
	/** KRCDT_MON_AGGR_BNSPY_TIME 10 **/
	AggregateBonusPayTimeMerge bonusPayTimeMerge;
	
	/** KRCDT_MON_AGGR_DIVG_TIME 10 **/
	AggregateDivergenceTimeMerge divergenceTimeMerge;
	
	/** KRCDT_MON_AGGR_GOOUT 4 **/
	AggregateGoOutMerge goOutMerge;
	
	/** KRCDT_MON_AGGR_HDWK_TIME 10 **/
	AggregateHolidayWorkTimeMerge holidayWorkTimeMerge; 
	
	/** KRCDT_MON_AGGR_OVER_TIME 10 **/
	AggregateOverTimeMerge overTimeMerge;
	
	/** KRCDT_MON_AGGR_PREM_TIME 10 **/
	AggregatePremiumTimeMerge premiumTimeMerge;
	
	/** KRCDT_MON_AGGR_SPEC_DAYS 10 **/
	AggregateSpecificDaysMerge specificDaysMerge; 
	
	/** KRCDT_MON_AGGR_TOTAL_SPT **/
	AggregateTotalTimeSpentAtWork totalTimeSpentAtWork;
	
	/** KRCDT_MON_AGGR_TOTAL_WRK **/
	AggregateTotalWorkingTime totalWorkingTime;
	
	/** KRCDT_MON_ATTENDANCE_TIME **/
	AttendanceTimeOfMonthly attendanceTimeOfMonthly;
	
	/** KRCDT_MON_FLEX_TIME **/
	FlexTimeOfMonthly flexTimeOfMonthly;
	
	/** KRCDT_MON_HDWK_TIME **/
	HolidayWorkTimeOfMonthly holidayWorkTimeOfMonthly;
	
	/** KRCDT_MON_LEAVE - リポジトリ：月別実績の休業 only update **/
	LeaveOfMonthly leaveOfMonthly;
	
	/** KRCDT_MON_MEDICAL_TIME **/
	MedicalTimeOfMonthly medicalTimeOfMonthly;
	
	/** KRCDT_MON_OVER_TIME **/
	OverTimeOfMonthly overTimeOfMonthly;
	
	/** KRCDT_MON_REG_IRREG_TIME **/
	RegularAndIrregularTimeOfMonthly regularAndIrregularTimeOfMonthly;
	
	/** KRCDT_MON_VACT_USE_TIME **/
	VacationUseTimeOfMonthly vacationUseTimeOfMonthly;
	
	/** KRCDT_MON_VERTICAL_TOTAL **/
	VerticalTotalOfMonthly verticalTotalOfMonthly;

	/** KRCDT_MON_EXCESS_OUTSIDE **/
	ExcessOutsideWorkOfMonthly excessOutsideWorkOfMonthly;
	
	/** KRCDT_MON_EXCOUT_TIME 50**/
	ExcessOutsideWorkMerge excessOutsideWorkMerge;
	
	/** KRCDT_MON_AGREEMENT_TIME **/
	AgreementTimeOfMonthly agreementTimeOfMonthly;
	
	/** KRCDT_MON_AFFILIATION **/
	AffiliationInfoOfMonthly affiliationInfoOfMonthly;
}
