//package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work;
//
//import java.nio.file.Paths;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import lombok.val;
//import mockit.Tested;
//import mockit.integration.junit4.JMockit;
//import nts.arc.layer.dom.AggregateRoot;
//import nts.arc.time.GeneralDate;
//import nts.arc.time.YearMonth;
//import nts.arc.time.calendar.period.DatePeriod;
//import nts.gul.serialize.binary.ObjectBinaryFile;
//import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
//import nts.uk.ctx.at.record.dom.affiliationinformation.AffiliationInforOfDailyPerfor;
//import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.PCLogOnInfoOfDaily;
//import nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemValueOfDaily;
//import nts.uk.ctx.at.record.dom.raisesalarytime.SpecificDateAttrOfDailyPerfor;
//import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
//import nts.uk.ctx.at.record.dom.worktime.TemporaryTimeOfDailyPerformance;
//import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
//import nts.uk.ctx.at.shared.dom.affiliationinformation.WorkTypeOfDailyPerformance;
//import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsRecRemainMngOfInPeriod;
//import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakDayOffRemainMngOfInPeriod;
//import nts.uk.ctx.at.shared.dom.remainingnumber.export.param.AggrResultOfAnnAndRsvLeave;
//import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service.InPeriodOfSpecialLeaveResultInfor;
//import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
//import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.EmployeeDailyPerError;
//import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.AggregateMonthlyRecordService;
//import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.com.ComFlexMonthActCalSet;
//import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.com.ComDeforLaborMonthActCalSet;
//import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.com.ComRegulaMonthActCalSet;
//import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonAggrCompanySettings;
//import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonAggrEmployeeSettings;
//import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.AttendanceTimeOfMonthly;
//import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.IntegrationOfMonthly;
//import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.anyitem.AnyItemOfMonthly;
//import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.OutsideOTSetting;
//import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.UseClassification;
//import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
//import nts.uk.shr.com.time.calendar.date.ClosureDate;
//
//@RunWith(JMockit.class)
//public class AggregateMonthlyRecordServiceTest {
//
//	@Tested
//	private AggregateMonthlyRecordService aggregateMonthlyService;
//	
//	@Test
//	public void a(){
//		
//		// restore
//		HashMap<String, Object> restoreCid =
//				ObjectBinaryFile.read(Paths.get("c:\\MonBinWithCid.csv"));
//		HashMap<String, Object> restoreEmpIdPeriod =
//				ObjectBinaryFile.read(Paths.get("c:\\MonBinWithEmpIdPeriod.csv"));
//		HashMap<String, Object> restoreEmpIdYearMonth =
//				ObjectBinaryFile.read(Paths.get("c:\\MonBinWithEmpIdYearMonth.csv"));
//		
//		String companyId = "comID";
//		String employeeId = "empID";
//		YearMonth yearMonth = YearMonth.of(2020, 5);
//		ClosureId closureId = ClosureId.RegularEmployee;
//		ClosureDate closureDate = new ClosureDate(0, true);
//		DatePeriod period = new DatePeriod(GeneralDate.ymd(2020, 5, 1), GeneralDate.ymd(2020, 5, 31));
//		
//		// 年休積立年休の集計結果（前月分）
//		AggrResultOfAnnAndRsvLeave prevAggrResult = new AggrResultOfAnnAndRsvLeave();
//		// 期間内の振出振休残数の取得結果（前月分）
//		AbsRecRemainMngOfInPeriod prevAbsRecResult = null;
//		// 休出代休残数管理（前月分）
//		BreakDayOffRemainMngOfInPeriod prevBreakDayOffResult = null;
//		// 特別休暇の集計結果情報(前月分)	
//		Map<Integer, InPeriodOfSpecialLeaveResultInfor> prevSpecialLeaveResultMap = new HashMap<>();
//		
//		// 月別集計で必要な会社別設定
//		MonAggrCompanySettings companySets = new MonAggrCompanySettings();
//		companySets.setCompanyId(companyId);
//		Map<String, AggregateRoot> holidayAdditions = (Map<String, AggregateRoot>)restoreCid.get("HolidayAddition");
//		companySets.getHolidayAdditionMap().putAll(holidayAdditions);
//		ComRegulaMonthActCalSet comRegSet = (ComRegulaMonthActCalSet)restoreCid.get("ComRegSet");
//		companySets.setComRegSetOpt(Optional.ofNullable(comRegSet));
//		ComDeforLaborMonthActCalSet comIrgSet = (ComDeforLaborMonthActCalSet)restoreCid.get("ComIrgSet");
//		companySets.setComIrgSetOpt(Optional.ofNullable(comIrgSet));
//		ComFlexMonthActCalSet comFlexSet = (ComFlexMonthActCalSet)restoreCid.get("ComFlexSet");
//		companySets.setComFlexSetOpt(Optional.ofNullable(comFlexSet));
//		OutsideOTSetting outsideOTSet = (OutsideOTSetting)restoreCid.get("OutsideOTSet");
//		companySets.setOutsideOverTimeSet(outsideOTSet);
//		// ＞　時間外超過設定：内訳項目一覧（積上番号順）
//		companySets.getOutsideOTBDItems().addAll(companySets.getOutsideOverTimeSet().getBreakdownItems());
//		companySets.getOutsideOTBDItems().removeIf(a -> { return a.getUseClassification() != UseClassification.UseClass_Use; });
//		companySets.getOutsideOTBDItems().sort((a, b) -> a.getProductNumber().value - b.getProductNumber().value);
//		// ＞　時間外超過設定：超過時間一覧（超過時間順）
//		companySets.getOutsideOTOverTimes().addAll(companySets.getOutsideOverTimeSet().getOvertimes());
//		companySets.getOutsideOTOverTimes().removeIf(a -> { return a.getUseClassification() != UseClassification.UseClass_Use; });
//		companySets.getOutsideOTOverTimes().sort((a, b) -> a.getOvertime().v() - b.getOvertime().v());
//		
//		// 月別集計で必要な社員別設定
//		MonAggrEmployeeSettings employeeSets = new MonAggrEmployeeSettings();
//		
//		// 日別実績(WORK)
//		List<IntegrationOfDaily> dailyWorks = new ArrayList<>();
//		List<WorkTypeOfDailyPerformance> workTypeOfDays =
//				(List<WorkTypeOfDailyPerformance>)restoreEmpIdPeriod.get("WorkTypeOfDay");
//		List<AffiliationInforOfDailyPerfor> affiliationOfDays =
//				(List<AffiliationInforOfDailyPerfor>)restoreEmpIdPeriod.get("AffiliationOfDay");
//		List<AttendanceTimeOfDailyPerformance> attendanceTimeOfDays =
//				(List<AttendanceTimeOfDailyPerformance>)restoreEmpIdPeriod.get("AttendanceTimeOfDay");
//		List<WorkInfoOfDailyPerformance> workInfoOfDays =
//				(List<WorkInfoOfDailyPerformance>)restoreEmpIdPeriod.get("WorkInfoOfDay");
//		List<TimeLeavingOfDailyPerformance> timeLeavingOfDays =
//				(List<TimeLeavingOfDailyPerformance>)restoreEmpIdPeriod.get("TimeLeavingOfDay");
//		List<TemporaryTimeOfDailyPerformance> temporaryTimeOfDays =
//				(List<TemporaryTimeOfDailyPerformance>)restoreEmpIdPeriod.get("TemporaryTimeOfDay");
//		List<SpecificDateAttrOfDailyPerfor> specificDateAttrOfDays =
//				(List<SpecificDateAttrOfDailyPerfor>)restoreEmpIdPeriod.get("SpecificDateAttrOfDay");
//		List<PCLogOnInfoOfDaily> pcLogOnInfoOfDays =
//				(List<PCLogOnInfoOfDaily>)restoreEmpIdPeriod.get("PCLogOnInfoOfDay");
//		List<EmployeeDailyPerError> perErrorOfDays =
//				(List<EmployeeDailyPerError>)restoreEmpIdPeriod.get("PerErrorOfDay");
//		List<AnyItemValueOfDaily> anyItemOfDays =
//				(List<AnyItemValueOfDaily>)restoreEmpIdPeriod.get("AnyItemOfDay");
//		
//		Map<GeneralDate, WorkTypeOfDailyPerformance> workTypeOfDayMap = new HashMap<>();
//		if (workTypeOfDays != null){
//			for (val v : workTypeOfDays) workTypeOfDayMap.put(v.getDate(), v);
//		}
//		Map<GeneralDate, AffiliationInforOfDailyPerfor> affiliationOfDayMap = new HashMap<>();
//		if (affiliationOfDays != null){
//			for (val v : affiliationOfDays) affiliationOfDayMap.put(v.getYmd(), v);
//		}
//		Map<GeneralDate, AttendanceTimeOfDailyPerformance> attendanceTimeOfDayMap = new HashMap<>();
//		if (attendanceTimeOfDays != null){
//			for (val v : attendanceTimeOfDays) attendanceTimeOfDayMap.put(v.getYmd(), v);
//		}
//		Map<GeneralDate, TimeLeavingOfDailyPerformance> timeLeavingOfDayMap = new HashMap<>();
//		if (timeLeavingOfDays != null){
//			for (val v : timeLeavingOfDays) timeLeavingOfDayMap.put(v.getYmd(), v);
//		}
//		Map<GeneralDate, TemporaryTimeOfDailyPerformance> temporaryTimeOfDayMap = new HashMap<>();
//		if (temporaryTimeOfDays != null){
//			for (val v : temporaryTimeOfDays) temporaryTimeOfDayMap.put(v.getYmd(), v);
//		}
//		Map<GeneralDate, SpecificDateAttrOfDailyPerfor> specificDateAttrOfDayMap = new HashMap<>();
//		if (specificDateAttrOfDays != null){
//			for (val v : specificDateAttrOfDays) specificDateAttrOfDayMap.put(v.getYmd(), v);
//		}
//		Map<GeneralDate, PCLogOnInfoOfDaily> pcLogOnInfoOfDayMap = new HashMap<>();
//		if (pcLogOnInfoOfDays != null){
//			for (val v : pcLogOnInfoOfDays) pcLogOnInfoOfDayMap.put(v.getYmd(), v);
//		}
//		Map<GeneralDate, List<EmployeeDailyPerError>> perErrorOfDayMap = new HashMap<>();
//		if (perErrorOfDays != null){
//			for (val v : perErrorOfDays){
//				perErrorOfDayMap.putIfAbsent(v.getDate(), new ArrayList<>());
//				perErrorOfDayMap.get(v.getDate()).add(v);
//			}
//		}
//		Map<GeneralDate, AnyItemValueOfDaily> anyItemOfDayMap = new HashMap<>();
//		if (anyItemOfDays != null){
//			for (val v : anyItemOfDays) anyItemOfDayMap.put(v.getYmd(), v);
//		}
//		
//		if (workTypeOfDays != null){
//			for (val v : workInfoOfDays){
//				GeneralDate ymd = v.getYmd();
//				
//				IntegrationOfDaily integOfDay = new IntegrationOfDaily(
//						employeeId,
//						ymd,
//						v.getWorkInformation(),
//						null,
//						affiliationOfDayMap.containsKey(ymd) ? affiliationOfDayMap.get(ymd).getAffiliationInfor() : null,
//						//Optional.ofNullable(workTypeOfDayMap.get(ymd)),
//						Optional.ofNullable(pcLogOnInfoOfDayMap.get(ymd).getTimeZone()),
//						perErrorOfDayMap.containsKey(ymd) ? perErrorOfDayMap.get(ymd) : new ArrayList<>(),
//						Optional.empty(),
//						new ArrayList<>(),
//						Optional.ofNullable(attendanceTimeOfDayMap.get(ymd).getTime()),
//						//Optional.empty(),
//						Optional.ofNullable(timeLeavingOfDayMap.get(ymd).getAttendance()),
//						Optional.empty(),
//						Optional.ofNullable(specificDateAttrOfDayMap.get(ymd).getSpecificDay()),
//						Optional.empty(),
//						Optional.ofNullable(anyItemOfDayMap.get(ymd).getAnyItem()),
//						new ArrayList<>(),
//						Optional.ofNullable(temporaryTimeOfDayMap.get(ymd).getAttendance()),
//						new ArrayList<>());
//				
//				dailyWorks.add(integOfDay);
//			}
//		}
//		
//		// 月別実績(WORK)
//		IntegrationOfMonthly monthlyWork = new IntegrationOfMonthly();
//		List<AttendanceTimeOfMonthly> attendanceTimeOfMons =
//				(List<AttendanceTimeOfMonthly>)restoreEmpIdYearMonth.get("AttendanceTimeOfMon");
//		List<AnyItemOfMonthly> anyItemOfMons =
//				(List<AnyItemOfMonthly>)restoreEmpIdYearMonth.get("AnyItemOfMon");
//		if (attendanceTimeOfMons != null){
//			if (attendanceTimeOfMons.size() > 0){
//				monthlyWork.setAttendanceTime(Optional.of(
//						attendanceTimeOfMons.get(attendanceTimeOfMons.size() - 1)));
//			}
//		}
//		if (anyItemOfMons != null){
//			monthlyWork.getAnyItemList().addAll(anyItemOfMons);
//		}
//		
//		val result = this.aggregateMonthlyService.aggregate(
//				companyId,
//				employeeId,
//				yearMonth,
//				closureId,
//				closureDate,
//				period,
//				prevAggrResult,
//				Optional.ofNullable(prevAbsRecResult),
//				Optional.ofNullable(prevBreakDayOffResult),
//				prevSpecialLeaveResultMap,
//				companySets,
//				employeeSets,
//				Optional.ofNullable(dailyWorks),
//				Optional.ofNullable(monthlyWork));
//		
//		result.toString();
//	}
//}
