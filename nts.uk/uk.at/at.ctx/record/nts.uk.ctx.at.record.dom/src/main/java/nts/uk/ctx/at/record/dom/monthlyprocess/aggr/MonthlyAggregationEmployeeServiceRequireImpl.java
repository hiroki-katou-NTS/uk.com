package nts.uk.ctx.at.record.dom.monthlyprocess.aggr;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeImport;
import nts.uk.ctx.at.shared.dom.common.days.AttendanceDaysMonth;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.DailyInterimRemainMngData;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.InterimRemainMngMode;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.MonthlyDayoffRemainData;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.ReserveLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.AddSetManageWorkHour;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HolidayAddtionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.WorkDeformedLaborAdditionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.WorkFlexAdditionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.WorkRegularAdditionSet;
import nts.uk.ctx.at.shared.dom.scherec.closurestatus.ClosureStatusManagement;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.AffiliationInforOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TemporaryTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.DailyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.entranceandexit.PCLogOnInfoOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.optionalitemvalue.AnyItemValueOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.paytime.SpecificDateAttrOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.snapshot.SnapShot;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.OuenWorkTimeOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.OuenWorkTimeSheetOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.AggregateMonthlyRecordService;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.AggregateMonthlyRecordValue;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.emp.EmpFlexMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.wkp.WkpFlexMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.emp.EmpDeforLaborMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.emp.EmpRegulaMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.wkp.WkpDeforLaborMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.wkp.WkpRegulaMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.converter.MonthlyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roundingset.RoundingSetOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.AggregateMethodOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.WorkDaysNumberOnLeaveCount;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonAggrCompanySettings;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonAggrEmployeeSettings;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonthlyCalculatingDailys;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting.BasicAgreementSettingForCalc;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.AttendanceTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.IntegrationOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.anyitem.AnyItemOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.editstate.EditStateOfMonthlyPerformance;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.flex.CalcFlexChangeDto;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.flex.ConditionCalcResult;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.ouen.aggframe.OuenAggregateFrameSetOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.absenceleave.AbsenceLeaveRemainData;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave.AnnLeaRemNumEachMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.reserveleave.RsvLeaRemNumEachMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialHolidayRemainData;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.reservation.ReservationOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.OutsideOTSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.holiday.SuperHD60HConMed;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.UsageUnitSetting;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetCom;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetEmp;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetSha;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetWkp;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeCom;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeEmp;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeSha;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeWkp;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeCom;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeEmp;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeSha;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeWkp;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveComSetting;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingCondition;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemWithPeriod;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.ctx.at.shared.dom.workrule.weekmanage.WeekRuleManagement;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.license.option.OptionLicense;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

@AllArgsConstructor
public class MonthlyAggregationEmployeeServiceRequireImpl implements AggregateMonthlyRecordService.RequireM2 {

	private List<IntegrationOfMonthly> aggrMonthly;
	private MonthlyAggregationEmployeeService.RequireM1 require;
	
	@Override
	public List<WorkingConditionItemWithPeriod> workingCondition(String employeeId, DatePeriod datePeriod) {
		return require.workingCondition(employeeId, datePeriod);
	}

	@Override
	public List<EditStateOfMonthlyPerformance> monthEditStates(String employeeId, YearMonth yearMonth,
			ClosureId closureId, ClosureDate closureDate) {
		return aggrMonthly.stream().filter(m -> m.getEmployeeId().equals(employeeId) &&
				m.getYearMonth().equals(yearMonth) && m.getClosureId().equals(closureId) &&
				m.getClosureDate().equals(closureDate))
		.map(m -> m.getEditState()).findFirst()
		.orElseGet(() -> require.monthEditStates(employeeId, yearMonth, closureId, closureDate));
	}

	@Override
	public Map<GeneralDate, SnapShot> snapshot(String employeeId, DatePeriod datePeriod) {
		return require.snapshot(employeeId, datePeriod);
	}

	@Override
	public Map<GeneralDate, TimeLeavingOfDailyAttd> dailyTimeLeavings(String employeeId, DatePeriod datePeriod) {
		return require.dailyTimeLeavings(employeeId, datePeriod);
	}

	@Override
	public Map<GeneralDate, TemporaryTimeOfDailyAttd> dailyTemporaryTimes(String employeeId, DatePeriod datePeriod) {
		return require.dailyTemporaryTimes(employeeId, datePeriod);
	}

	@Override
	public Map<GeneralDate, SpecificDateAttrOfDailyAttd> dailySpecificDates(String employeeId, DatePeriod datePeriod) {
		return require.dailySpecificDates(employeeId, datePeriod);
	}

	@Override
	public List<EmployeeDailyPerError> dailyEmpErrors(String employeeId, DatePeriod datePeriod) {
		return require.dailyEmpErrors(employeeId, datePeriod);
	}

	@Override
	public Map<GeneralDate, AnyItemValueOfDailyAttd> dailyAnyItems(List<String> employeeId, DatePeriod baseDate) {
		return require.dailyAnyItems(employeeId, baseDate);
	}

	@Override
	public Map<GeneralDate, PCLogOnInfoOfDailyAttd> dailyPcLogons(List<String> employeeId, DatePeriod baseDate) {
		return require.dailyPcLogons(employeeId, baseDate);
	}

	@Override
	public List<AnnualLeaveGrantRemainingData> annualLeaveGrantRemainingData(String employeeId) {
		return require.annualLeaveGrantRemainingData(employeeId);
	}

	@Override
	public List<ReserveLeaveGrantRemainingData> reserveLeaveGrantRemainingData(String employeeId) {
		return require.reserveLeaveGrantRemainingData(employeeId);
	}

	@Override
	public Map<GeneralDate, AffiliationInforOfDailyAttd> dailyAffiliationInfors(List<String> employeeId,
			DatePeriod baseDate) {
		return require.dailyAffiliationInfors(employeeId, baseDate);
	}

	@Override
	public Map<GeneralDate, WorkInfoOfDailyAttendance> dailyWorkInfos(String employeeId, DatePeriod datePeriod) {
		return require.dailyWorkInfos(employeeId, datePeriod);
	}

	@Override
	public Map<GeneralDate, AttendanceTimeOfDailyAttendance> dailyAttendanceTimes(String employeeId,
			DatePeriod datePeriod) {
		return require.dailyAttendanceTimes(employeeId, datePeriod);
	}

	@Override
	public Map<String, Map<GeneralDate, AttendanceTimeOfDailyAttendance>> dailyAttendanceTimesclones(
			List<String> employeeId, DatePeriod datePeriod) {
		return require.dailyAttendanceTimesclones(employeeId, datePeriod);
	}

	@Override
	public ConditionCalcResult flexConditionCalcResult(CacheCarrier cacheCarrier, String companyId,
			CalcFlexChangeDto calc) {
		return require.flexConditionCalcResult(cacheCarrier, companyId, calc);
	}

	@Override
	public List<String> getCanUseWorkplaceForEmp(CacheCarrier cacheCarrier, String companyId, String employeeId,
			GeneralDate baseDate) {
		return require.getCanUseWorkplaceForEmp(cacheCarrier, companyId, employeeId, baseDate);
	}

	@Override
	public Optional<WkpRegulaMonthActCalSet> monthRegularCalcSetByWorkplace(String cid, String wkpId) {
		return require.monthRegularCalcSetByWorkplace(cid, wkpId);
	}

	@Override
	public Optional<EmpRegulaMonthActCalSet> monthRegularCalcSetByEmployment(String cid, String empCode) {
		return require.monthRegularCalcSetByEmployment(cid, empCode);
	}

	@Override
	public List<WorkingConditionItem> workingConditionItem(String employeeId, DatePeriod datePeriod) {
		return require.workingConditionItem(employeeId, datePeriod);
	}

	@Override
	public Optional<WkpDeforLaborMonthActCalSet> monthDeforCalcSetByWorkplace(String cid, String wkpId) {
		return require.monthDeforCalcSetByWorkplace(cid, wkpId);
	}

	@Override
	public Optional<EmpDeforLaborMonthActCalSet> monthDeforCalcSetByEmployment(String cid, String empCode) {
		return require.monthDeforCalcSetByEmployment(cid, empCode);
	}

	@Override
	public Optional<WkpFlexMonthActCalSet> wkpFlexMonthActCalSet(String companyId, String workplaceId) {
		return require.wkpFlexMonthActCalSet(companyId, workplaceId);
	}

	@Override
	public Optional<EmpFlexMonthActCalSet> empFlexMonthActCalSet(String companyId, String employmentCode) {
		return require.empFlexMonthActCalSet(companyId, employmentCode);
	}

	@Override
	public Optional<MonthlyWorkTimeSetWkp> monthlyWorkTimeSetWkp(String cid, String workplaceId,
			LaborWorkTypeAttr laborAttr, YearMonth ym) {
		return require.monthlyWorkTimeSetWkp(cid, workplaceId, laborAttr, ym);
	}

	@Override
	public Optional<MonthlyWorkTimeSetSha> monthlyWorkTimeSetSha(String cid, String sid, LaborWorkTypeAttr laborAttr,
			YearMonth ym) {
		return require.monthlyWorkTimeSetSha(cid, sid, laborAttr, ym);
	}

	@Override
	public Optional<MonthlyWorkTimeSetEmp> monthlyWorkTimeSetEmp(String cid, String empCode,
			LaborWorkTypeAttr laborAttr, YearMonth ym) {
		return require.monthlyWorkTimeSetEmp(cid, empCode, laborAttr, ym);
	}

	@Override
	public Optional<MonthlyWorkTimeSetCom> monthlyWorkTimeSetCom(String cid, LaborWorkTypeAttr laborAttr,
			YearMonth ym) {
		return require.monthlyWorkTimeSetCom(cid, laborAttr, ym);
	}

	@Override
	public Optional<UsageUnitSetting> usageUnitSetting(String companyId) {
		return require.usageUnitSetting(companyId);
	}

	@Override
	public Optional<RegularLaborTimeCom> regularLaborTimeByCompany(String companyId) {
		return require.regularLaborTimeByCompany(companyId);
	}

	@Override
	public Optional<DeforLaborTimeCom> deforLaborTimeByCompany(String companyId) {
		return require.deforLaborTimeByCompany(companyId);
	}

	@Override
	public Optional<RegularLaborTimeWkp> regularLaborTimeByWorkplace(String cid, String wkpId) {
		return require.regularLaborTimeByWorkplace(cid, wkpId);
	}

	@Override
	public Optional<DeforLaborTimeWkp> deforLaborTimeByWorkplace(String cid, String wkpId) {
		return require.deforLaborTimeByWorkplace(cid, wkpId);
	}

	@Override
	public Optional<RegularLaborTimeEmp> regularLaborTimeByEmployment(String cid, String employmentCode) {
		return require.regularLaborTimeByEmployment(cid, employmentCode);
	}

	@Override
	public Optional<DeforLaborTimeEmp> deforLaborTimeByEmployment(String cid, String employmentCode) {
		return require.deforLaborTimeByEmployment(cid, employmentCode);
	}

	@Override
	public Optional<RegularLaborTimeSha> regularLaborTimeByEmployee(String Cid, String EmpId) {
		return require.regularLaborTimeByEmployee(Cid, EmpId);
	}

	@Override
	public Optional<DeforLaborTimeSha> deforLaborTimeByEmployee(String cid, String empId) {
		return require.deforLaborTimeByEmployee(cid, empId);
	}

	@Override
	public EmployeeImport employeeInfo(CacheCarrier cacheCarrier, String empId) {
		return require.employee(cacheCarrier, empId);
	}

	@Override
	public Optional<ClosureEmployment> employmentClosure(String companyID, String employmentCD) {
		return require.employmentClosure(companyID, employmentCD);
	}

	@Override
	public List<ClosureEmployment> employmentClosureClones(String companyID, List<String> employmentCD) {
		return require.employmentClosureClones(companyID, employmentCD);
	}

	@Override
	public Optional<Closure> closure(String companyId, int closureId) {
		return require.closure(companyId, closureId);
	}

	@Override
	public List<Closure> closureClones(String companyId, List<Integer> closureId) {
		return require.closure(companyId);
	}

	@Override
	public DailyRecordToAttendanceItemConverter createDailyConverter() {
		return require.createDailyConverter();
	}

	@Override
	public Optional<WorkType> workType(String companyId, String workTypeCd) {
		return require.workType(companyId, workTypeCd);
	}

	@Override
	public Optional<WeekRuleManagement> weekRuleManagement(String cid) {
		return require.weekRuleManagement(cid);
	}

	@Override
	public Optional<WorkingConditionItem> workingConditionItem(String employeeId, GeneralDate baseDate) {
		return require.workingConditionItem(employeeId, baseDate);
	}

	@Override
	public Optional<WorkType> workType(String companyId, WorkTypeCode workTypeCode) {
		return require.workType(companyId, workTypeCode);
	}

	@Override
	public Optional<WorkTimeSetting> workTimeSetting(String companyId, String workTimeCode) {
		return require.workTimeSetting(companyId, workTimeCode);
	}

	@Override
	public Optional<FixedWorkSetting> fixedWorkSetting(String companyId, String workTimeCode) {
		return require.fixedWorkSetting(companyId, workTimeCode);
	}

	@Override
	public Optional<FlowWorkSetting> flowWorkSetting(String companyId, String workTimeCode) {
		return require.flowWorkSetting(companyId, workTimeCode);
	}

	@Override
	public Optional<DiffTimeWorkSetting> diffTimeWorkSetting(String companyId, String workTimeCode) {
		return require.diffTimeWorkSetting(companyId, workTimeCode);
	}

	@Override
	public Optional<FlexWorkSetting> flexWorkSetting(String companyId, String workTimeCode) {
		return require.flexWorkSetting(companyId, workTimeCode);
	}

	@Override
	public Optional<WorkTimeSetting> workTimeSetting(String companyId, WorkTimeCode workTimeCode) {
		return require.workTimeSetting(companyId, workTimeCode);
	}

	@Override
	public Optional<FixedWorkSetting> fixedWorkSetting(String companyId, WorkTimeCode workTimeCode) {
		return require.fixedWorkSetting(companyId, workTimeCode);
	}

	@Override
	public Optional<FlowWorkSetting> flowWorkSetting(String companyId, WorkTimeCode workTimeCode) {
		return require.flowWorkSetting(companyId, workTimeCode);
	}

	@Override
	public Optional<FlexWorkSetting> flexWorkSetting(String companyId, WorkTimeCode workTimeCode) {
		return require.flexWorkSetting(companyId, workTimeCode);
	}

	@Override
	public Optional<HolidayAddtionSet> holidayAddtionSet(String cid) {
		return require.holidayAddtionSet(cid);
	}

	@Override
	public Optional<AddSetManageWorkHour> addSetManageWorkHour(String cid) {
		return require.addSetManageWorkHour(cid);
	}

	@Override
	public Optional<WorkFlexAdditionSet> workFlexAdditionSet(String cid) {
		return require.workFlexAdditionSet(cid);
	}

	@Override
	public Optional<WorkRegularAdditionSet> workRegularAdditionSet(String cid) {
		return require.workRegularAdditionSet(cid);
	}

	@Override
	public Optional<WorkDeformedLaborAdditionSet> workDeformedLaborAdditionSet(String cid) {
		return require.workDeformedLaborAdditionSet(cid);
	}

	@Override
	public Optional<PredetemineTimeSetting> predetemineTimeSetting(String companyId, WorkTimeCode workTimeCode) {
		return require.predetemineTimeSetting(companyId, workTimeCode);
	}

	@Override
	public List<AttendanceTimeOfMonthly> attendanceTimeOfMonthly(String employeeId, YearMonth yearMonth) {
		val monthlys = aggrMonthly.stream().filter(m -> m.getEmployeeId().equals(employeeId) &&
					m.getYearMonth().equals(yearMonth) && m.getAttendanceTime().isPresent())
			.map(m -> m.getAttendanceTime().get()).collect(Collectors.toList());
		val dbMonthly = require.attendanceTimeOfMonthly(employeeId, yearMonth);
		
		dbMonthly.removeIf(c -> monthlys.stream().anyMatch(m -> m.getClosureId().equals(c.getClosureId()) &&
															m.getClosureDate().equals(c.getClosureDate())));
		dbMonthly.addAll(monthlys);
		return dbMonthly; 
	}

	@Override
	public Optional<AttendanceTimeOfMonthly> attendanceTimeOfMonthly(String employeeId, YearMonth yearMonth,
			ClosureId closureId, ClosureDate closureDate) {
		
		return aggrMonthly.stream().filter(m -> m.getEmployeeId().equals(employeeId) &&
					m.getYearMonth().equals(yearMonth) && m.getClosureId().equals(closureId) &&
					m.getClosureDate().equals(closureDate))
			.map(m -> m.getAttendanceTime()).findFirst()
			.orElseGet(() -> require.attendanceTimeOfMonthly(employeeId, yearMonth, closureId, closureDate));
	}

	@Override
	public Optional<AggregateMethodOfMonthly> aggregateMethodOfMonthly(String cid) {
		return require.aggregateMethodOfMonthly(cid);
	}

	@Override
	public WorkDaysNumberOnLeaveCount workDaysNumberOnLeaveCount(String cid) {
		return require.workDaysNumberOnLeaveCount(cid);
	}

	@Override
	public ReservationOfMonthly reservation(String sid, GeneralDate date, String companyID) {
		return require.reservation(sid, date, companyID);
	}

	@Override
	public Optional<SuperHD60HConMed> superHD60HConMed(String cid) {
		return require.superHD60HConMed(cid);
	}

	@Override
	public Optional<OutsideOTSetting> outsideOTSetting(String cid) {
		return require.outsideOTSetting(cid);
	}

	@Override
	public MonthlyRecordToAttendanceItemConverter createMonthlyConverter() {
		return require.createMonthlyConverter();
	}

	@Override
	public Optional<WorkingCondition> workingCondition(String historyId) {
		return require.workingCondition(historyId);
	}

	@Override
	public AggregateMonthlyRecordValue aggregation(CacheCarrier cacheCarrier, DatePeriod period, String companyId,
			String employeeId, YearMonth yearMonth, ClosureId closureId, ClosureDate closureDate,
			MonAggrCompanySettings companySets, MonAggrEmployeeSettings employeeSets,
			MonthlyCalculatingDailys monthlyCalculatingDailys, InterimRemainMngMode interimRemainMngMode,
			boolean isCalcAttendanceRate) {
		return require.aggregation(cacheCarrier, period, companyId, employeeId, yearMonth, closureId, closureDate, 
				companySets, employeeSets, monthlyCalculatingDailys, interimRemainMngMode, isCalcAttendanceRate);
	}

	@Override
	public Optional<ClosureStatusManagement> latestClosureStatusManagement(String employeeId) {
		return require.latestClosureStatusManagement(employeeId);
	}

	@Override
	public List<DailyInterimRemainMngData> createDailyInterimRemainMngs(CacheCarrier cacheCarrier, String companyId,
			String employeeId, DatePeriod period, MonAggrCompanySettings comSetting, MonthlyCalculatingDailys dailys) {
		return require.createDailyInterimRemainMngs(cacheCarrier, companyId, employeeId, period, comSetting, dailys);
	}

	@Override
	public Optional<OuenAggregateFrameSetOfMonthly> ouenAggregateFrameSetOfMonthly(String companyId) {
		return require.ouenAggregateFrameSetOfMonthly(companyId);
	}

	@Override
	public List<OuenWorkTimeOfDailyAttendance> ouenWorkTimeOfDailyAttendance(String empId, GeneralDate ymd) {
		return require.ouenWorkTimeOfDailyAttendance(empId, ymd);
	}

	@Override
	public List<OuenWorkTimeSheetOfDailyAttendance> ouenWorkTimeSheetOfDailyAttendance(String empId, GeneralDate ymd) {
		return require.ouenWorkTimeSheetOfDailyAttendance(empId, ymd);
	}

	@Override
	public boolean isUseWorkLayer(String companyId) {
		return require.isUseWorkLayer(companyId);
	}

	@Override
	public BasicAgreementSettingForCalc basicAgreementSetting(String cid, String sid, YearMonth ym,
			GeneralDate baseDate) {
		return require.basicAgreementSetting(cid, sid, ym, baseDate);
	}

	@Override
	public Map<String, BasicAgreementSettingForCalc> basicAgreementSettingClones(String cid, List<String> sid,
			YearMonth ym, GeneralDate baseDate) {
		return require.basicAgreementSettingClones(cid, sid, ym, baseDate);
	}

	@Override
	public Optional<RoundingSetOfMonthly> monthRoundingSet(String cid) {
		return require.monthRoundingSet(cid);
	}

	@Override
	public List<AnyItemOfMonthly> anyItemOfMonthly(String employeeId, YearMonth yearMonth, ClosureId closureId,
			ClosureDate closureDate) {
		
		return aggrMonthly.stream().filter(m -> m.getEmployeeId().equals(employeeId) &&
				m.getYearMonth().equals(yearMonth) && m.getClosureId().equals(closureId) &&
				m.getClosureDate().equals(closureDate))
		.map(m -> m.getAnyItemList()).findFirst()
		.orElseGet(() -> require.anyItemOfMonthly(employeeId, yearMonth, closureId, closureDate));
	}

	@Override
	public Optional<AnnLeaRemNumEachMonth> annLeaRemNumEachMonth(String employeeId, YearMonth yearMonth,
			ClosureId closureId, ClosureDate closureDate) {
		
		return aggrMonthly.stream().filter(m -> m.getEmployeeId().equals(employeeId) &&
				m.getYearMonth().equals(yearMonth) && m.getClosureId().equals(closureId) &&
				m.getClosureDate().equals(closureDate))
		.map(m -> m.getAnnualLeaveRemain()).findFirst()
		.orElseGet(() -> require.annLeaRemNumEachMonth(employeeId, yearMonth, closureId, closureDate));
	}

	@Override
	public Optional<RsvLeaRemNumEachMonth> rsvLeaRemNumEachMonth(String employeeId, YearMonth yearMonth,
			ClosureId closureId, ClosureDate closureDate) {

		return aggrMonthly.stream().filter(m -> m.getEmployeeId().equals(employeeId) &&
				m.getYearMonth().equals(yearMonth) && m.getClosureId().equals(closureId) &&
				m.getClosureDate().equals(closureDate))
		.map(m -> m.getReserveLeaveRemain()).findFirst()
		.orElseGet(() -> require.rsvLeaRemNumEachMonth(employeeId, yearMonth, closureId, closureDate));
	}

	@Override
	public Optional<AbsenceLeaveRemainData> absenceLeaveRemainData(String employeeId, YearMonth yearMonth,
			ClosureId closureId, ClosureDate closureDate) {

		return aggrMonthly.stream().filter(m -> m.getEmployeeId().equals(employeeId) &&
				m.getYearMonth().equals(yearMonth) && m.getClosureId().equals(closureId) &&
				m.getClosureDate().equals(closureDate))
		.map(m -> m.getAbsenceLeaveRemain()).findFirst()
		.orElseGet(() -> require.absenceLeaveRemainData(employeeId, yearMonth, closureId, closureDate));
	}

	@Override
	public Optional<MonthlyDayoffRemainData> monthlyDayoffRemainData(String employeeId, YearMonth yearMonth,
			ClosureId closureId, ClosureDate closureDate) {

		return aggrMonthly.stream().filter(m -> m.getEmployeeId().equals(employeeId) &&
				m.getYearMonth().equals(yearMonth) && m.getClosureId().equals(closureId) &&
				m.getClosureDate().equals(closureDate))
		.map(m -> m.getMonthlyDayoffRemain()).findFirst()
		.orElseGet(() -> require.monthlyDayoffRemainData(employeeId, yearMonth, closureId, closureDate));
	}

	@Override
	public List<SpecialHolidayRemainData> specialHolidayRemainData(String employeeId, YearMonth yearMonth,
			ClosureId closureId, ClosureDate closureDate) {

		return aggrMonthly.stream().filter(m -> m.getEmployeeId().equals(employeeId) &&
				m.getYearMonth().equals(yearMonth) && m.getClosureId().equals(closureId) &&
				m.getClosureDate().equals(closureDate))
		.map(m -> m.getSpecialLeaveRemain()).findFirst()
		.orElseGet(() -> require.specialHolidayRemainData(employeeId, yearMonth, closureId, closureDate));
	}

	@Override
	public AttendanceDaysMonth monthAttendanceDays(CacheCarrier cacheCarrier, DatePeriod period,
			Map<String, WorkType> workTypeMap) {
		return require.monthAttendanceDays(cacheCarrier, period, workTypeMap);
	}

	@Override
	public Optional<WorkTimeSetting> getWorkTime(String cid, String workTimeCode) {
		return this.workTimeSetting(cid, new WorkTimeCode(workTimeCode));
	}

	@Override
	public CompensatoryLeaveComSetting findCompensatoryLeaveComSet(String companyId) {
		return require.compensatoryLeaveComSetting(companyId).get();
	}

	@Override
	public OptionLicense getOptionLicense() {
		return AppContexts.optionLicense();
	}

}
