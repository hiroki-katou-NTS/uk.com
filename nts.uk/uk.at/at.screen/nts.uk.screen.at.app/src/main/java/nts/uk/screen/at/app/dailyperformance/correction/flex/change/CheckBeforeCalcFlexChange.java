package nts.uk.screen.at.app.dailyperformance.correction.flex.change;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.RequiredArgsConstructor;
import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.actualworkinghours.repository.AttendanceTimeRepository;
import nts.uk.ctx.at.record.dom.adapter.company.AffComHistItemImport;
import nts.uk.ctx.at.record.dom.adapter.company.AffCompanyHistImport;
import nts.uk.ctx.at.record.dom.adapter.company.SyCompanyRecordAdapter;
import nts.uk.ctx.at.record.dom.adapter.workplace.affiliate.AffWorkplaceAdapter;
import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyAdapter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.flex.CalcFlexChangeDto;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.flex.CheckBeforeCalcFlexChangeService;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.flex.ConditionCalcResult;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.flex.MessageFlex;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.UsageUnitSetting;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.UsageUnitSettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.algorithm.monthly.MonthlyFlexStatutoryLaborTime;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.algorithm.monthly.MonthlyStatutoryWorkingHours;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetCom;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetEmp;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetSha;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetWkp;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingCondition;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.pub.workrule.closure.PresentClosingPeriodExport;
import nts.uk.ctx.at.shared.pub.workrule.closure.ShClosurePub;
import nts.uk.screen.at.app.dailyperformance.correction.DailyPerformanceScreenRepo;
import nts.uk.screen.at.app.dailyperformance.correction.dto.AffEmploymentHistoryDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DateRange;
import nts.uk.shr.com.history.DateHistoryItem;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class CheckBeforeCalcFlexChange implements CheckBeforeCalcFlexChangeService {

	@Inject
	private ShClosurePub shClosurePub;

	@Inject
	private WorkingConditionRepository workingConditionRepository;

	@Inject
	private WorkingConditionItemRepository workingConditionItemRepository;

	@Inject
	private ClosureEmploymentRepository closureEmploymentRepository;

	@Inject
	private SyCompanyRecordAdapter syCompanyRecordAdapter;

//	@Inject
//	private AttendanceTimeOfMonthlyRepository attendanceTimeOfMonthlyRepository;

	@Inject
	private DailyPerformanceScreenRepo dailyPerformanceScreenRepo;

//	@Inject
//	private AffWorkplaceHistoryItemRepository affWorkplaceHis;

	@Inject
	private AttendanceTimeRepository attendanceTime;
	
	@Inject
	private CompanyAdapter companyAdapter;
	
	/*require???*/
	@Inject
	private UsageUnitSettingRepository usageUnitSettingRepository;
	@Inject
	private MonthlyWorkTimeSetRepo monthlyWorkTimeSet;

	/** ?????????????????? */
	@Inject
	private AffWorkplaceAdapter affWorkplaceAdapter;
	/*require???*/

	private static final String TIME_DEFAULT = "0:00";

	// ??????????????????????????????????????????????????????
	@Override
	public ConditionCalcResult getConditionCalcFlex(String companyId, CalcFlexChangeDto calc) {
		val cacheCarrier = new CacheCarrier();
		return getConditionCalcFlexRequire(cacheCarrier, companyId, calc);
	}
	
	@Override
	public ConditionCalcResult getConditionCalcFlexRequire(CacheCarrier cacheCarrier, String companyId, CalcFlexChangeDto calc) {
		return getConditionCalcFlex(new RequireImpl(cacheCarrier), cacheCarrier, 
				companyId, calc, Optional.empty(), Optional.empty());
	}

	// ??????????????????????????????????????????????????????
	private ConditionCalcResult getConditionCalcFlex(RequireM2 require, CacheCarrier cacheCarrier,
			String companyId, CalcFlexChangeDto calc, Optional<ClosureEmployment> closureEmployment,
			Optional<PresentClosingPeriodExport> periodExportOpt) {
		
		if(!closureEmployment.isPresent()){
			String employmentCode = require.affEmploymentHistory(companyId, calc.getEmployeeId(), calc.getDate()).getEmploymentCode();
			closureEmployment = require.employmentClosure(companyId, employmentCode);
		}
		if (!closureEmployment.isPresent())
			return new ConditionCalcResult("", MessageFlex.Normal);
		// ?????????????????????????????????????????????????????????
		if(!periodExportOpt.isPresent()){
			periodExportOpt = shClosurePub.findRequire(cacheCarrier, companyId, closureEmployment.get().getClosureId(), calc.getDate());
		}
		
		if (periodExportOpt.isPresent() && (calc.getDate().after(periodExportOpt.get().getClosureEndDate())
				|| calc.getDate().before(periodExportOpt.get().getClosureStartDate()))) {
			// ????????????????????????????????????????????????????????????????????????????????????
			Integer valueLimit = require.getLimitFexMonth(companyId);
			if (valueLimit == null)
				valueLimit = 0;
			//102745  ?????????????????? 
			return new ConditionCalcResult(converMinutesToHours(valueLimit), MessageFlex.Normal);
		}

		// ??????ID???List?????????????????????????????????????????????????????????
		DatePeriod datePeriod = new DatePeriod(periodExportOpt.get().getClosureStartDate(), periodExportOpt.get().getClosureEndDate());
		List<String> sIds = Arrays.asList(calc.getEmployeeId());
		List<AffCompanyHistImport> affCompanyHis = syCompanyRecordAdapter.getAffCompanyHistByEmployeeRequire(cacheCarrier, sIds, datePeriod);
		if (!affCompanyHis.isEmpty()) {
			for (AffComHistItemImport his : affCompanyHis.get(0).getLstAffComHistItem()) {
				// ???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
				if (his.getDatePeriod().end() != null && datePeriod.contains(his.getDatePeriod().end())) {
					//102745 ????????????????????????
					return new ConditionCalcResult(TIME_DEFAULT, MessageFlex.Retirement);
				}
			}
		}
		
		if (calc.getWCItems().isEmpty()) {
			//102745 ????????????????????????
			return new ConditionCalcResult(TIME_DEFAULT, MessageFlex.Retirement);
		}
		
		// ??????????????????????????????????????????????????????
		// Slow Perfomnace
//		GeneralDate dateCheck = GeneralDate.today();
//		Optional<WorkingConditionItem> todayWCI = workingConditionItemRepository.getBySidAndStandardDate(calc.getEmployeeId(), dateCheck);
//		List<DateHistoryItem> WCes = workingConditionRepository.getBySidsAndDatePeriod(sIds, datePeriod)
//																.stream().map(c -> c.getDateHistoryItem())
//																.flatMap(List::stream).distinct().collect(Collectors.toList());
//		for (WorkingConditionItem item : calc.getWCItems()) {
////			Optional<WorkingCondition> workConditionOpt = workingConditionRepository.getByHistoryId(item.getHistoryId());
////			if (workConditionOpt.isPresent() && periodExportOpt.isPresent()) {
//				
//			Optional<DateHistoryItem> dateHist = WCes.stream().filter(x -> x.identifier().equals(item.getHistoryId())).findFirst();
//			if (dateHist.isPresent()) {
//				dateCheck = dateHist.get().end().nextValue(true);
//				
//				if (!todayWCI.isPresent() || !todayWCI.get().getLaborSystem().equals(WorkingSystem.FLEX_TIME_WORK)) {
//					return TIME_DEFAULT;
//				}
//			}
////			}
//		}
		/** TODO: replace below code with above code after confirm design? */
		boolean checkFlex = true;
		for (WorkingConditionItem item : calc.getWCItems()) {
			Optional<WorkingCondition> workConditionOpt = require.workingCondition(item.getHistoryId());
			if (workConditionOpt.isPresent() && periodExportOpt.isPresent()) {
				GeneralDate startDate = periodExportOpt.get().getClosureStartDate();
				GeneralDate endDate = periodExportOpt.get().getClosureEndDate();
				GeneralDate dateCheck = GeneralDate.today();
				Optional<DateHistoryItem> dateHist = workConditionOpt.get().getDateHistoryItem().stream()
						.filter(x -> x.end().afterOrEquals(startDate) && x.end().beforeOrEquals(endDate)).findFirst();
				if (dateHist.isPresent()) {
					dateCheck = dateHist.get().end().nextValue(true);
					Optional<WorkingConditionItem> workingConditionItemOpt = require.workingConditionItem(calc.getEmployeeId(), dateCheck);
					if (!workingConditionItemOpt.isPresent()
							|| !workingConditionItemOpt.get().getLaborSystem().equals(WorkingSystem.FLEX_TIME_WORK)) {
						checkFlex = false;
					}
				}
			}
		}
        
		if(!checkFlex){
			//102745 ??????????????????????????????????????????
			return new ConditionCalcResult(TIME_DEFAULT, MessageFlex.Not_Next_Month);
		}
		// ????????????????????????????????????(l?? ???????????????????????????)
		// ?????????????????????????????????????????????????????????
		Optional<PresentClosingPeriodExport> periodExportOptNext = shClosurePub.findRequire(
				cacheCarrier, companyId,
				closureEmployment.get().getClosureId(), periodExportOpt.get().getClosureEndDate().addDays(1));
		// ????????????????????????????????????????????????????????????????????? 
		// #102745 change EAP
//		Optional<AttendanceTimeOfMonthly> attTiemOpt = attendanceTimeOfMonthlyRepository.find(calc.getEmployeeId(),
//				periodExportOptNext.get().getProcessingYm(),
//				ClosureId.valueOf(closureEmployment.get().getClosureId()),
//				new ClosureDate(periodExportOptNext.get().getClosureDate().getClosureDay(),
//						periodExportOptNext.get().getClosureDate().getLastDayOfMonth()));
//
//		// String timeCarryoverTime = "0:00";
		long timeSum = 0L;
//		if (attTiemOpt.isPresent()) {
//			// ????????????????????????-??????????????????????????????????????????????????????????????????????????????????????????
//			if (attTiemOpt.get().getMonthlyCalculation() == null
//					|| attTiemOpt.get().getMonthlyCalculation().getStatutoryWorkingTime() == null)
//				return TIME_DEFAULT;
//			timeSum = attTiemOpt.get().getMonthlyCalculation().getStatutoryWorkingTime().v()
//					- attTiemOpt.get().getMonthlyCalculation().getAggregateTime().getPrescribedWorkingTime()
//							.getSchedulePrescribedWorkingTime().v();
//			// timeCarryoverTime = String.format("%d:%02d", time);
//		} else {
			// ?????????????????????????????????????????????????????????
			AffEmploymentHistoryDto afEmpDto = require.affEmploymentHistory(companyId, calc.getEmployeeId(),
														periodExportOptNext.get().getClosureStartDate());
			
			// bug 107743
			// ?????????????????????????????????????????????????????????????????????
//			YearMonth yearMonthCalen = companyAdapter.getYearMonthFromCalenderYM(cacheCarrier, companyId, periodExportOptNext.get().getProcessingYm());
//			List<AffWorkplaceHistoryItem> lstAffWorkplace = affWorkplaceHis
//					.getAffWrkplaHistItemByEmpIdAndDate(datePeriod.start(), calc.getEmployeeId());
			// ???????????????????????????????????????(??????????????????)
			MonthlyFlexStatutoryLaborTime monthFlex = MonthlyStatutoryWorkingHours.flexMonAndWeekStatutoryTime(
					require, cacheCarrier,
					companyId, afEmpDto.getEmploymentCode(), calc.getEmployeeId(), datePeriod.start(),
					periodExportOptNext.get().getProcessingYm());

			// ???????????????????????????????????????????????????????????????????????????
			// List<WorkingConditionItem> workingConditionItems =
			// workingConditionItemRepository.getBySidAndPeriodOrderByStrD(calc.getEmployeeId(),
			// new DatePeriod(periodExportOptNext.get().getClosureStartDate(),
			// periodExportOptNext.get().getClosureStartDate()));
			// workingConditionItems = workingConditionItems.stream().filter(x
			// ->
			// x.getLaborSystem().equals(WorkingSystem.FLEX_TIME_WORK)).collect(Collectors.toList());
			List<DateRange> dates = require.getWorkConditionFlexDatePeriod(calc.getEmployeeId(),
					new DatePeriod(periodExportOptNext.get().getClosureStartDate(),
							periodExportOptNext.get().getClosureEndDate()));
			List<Integer> listAttTime = require.findAtt(calc.getEmployeeId(),
					convertToListDate(dates));
			// ????????????????????????-??????????????????????????????????????????????????????????????????????????????????????????

			timeSum = (monthFlex != null && monthFlex.getStatutorySetting() != null)
					? monthFlex.getStatutorySetting().v() - calcSumTime(listAttTime) : 0 - calcSumTime(listAttTime);
//		}

		if (timeSum <= 0) {
			timeSum = 0;
		}
		
		// ????????????????????????????????????????????????????????????????????????????????????
		Integer valueLimit = require.getLimitFexMonth(companyId);
		if(valueLimit == null) valueLimit = 0;
		if (valueLimit >= timeSum) {
			//102745  ??????????????????
			return new ConditionCalcResult(converMinutesToHours((int)timeSum), MessageFlex.Normal);
		} else {
			////102745  ??????????????????
			return new ConditionCalcResult(converMinutesToHours(valueLimit), MessageFlex.Normal);
		}
	}

	private List<GeneralDate> convertToListDate(List<DateRange> dates) {
		List<GeneralDate> listDate = new ArrayList<>();
		dates.forEach(x -> {
			listDate.addAll(x.toListDate());
		});
		return listDate;
	}

	public String converMinutesToHours(int time) {
		int hours = time / 60;
		int minute = Math.abs(time) % 60;
		return (time < 0 && hours == 0) ? "-" + String.format("%d:%02d", hours, minute)
				: String.format("%d:%02d", hours, minute);
	}

	public int convertHourToMinute(String time) throws ParseException {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		Date date = sdf.parse("8:00");
		cal.setTime(date);
		int mins = cal.get(Calendar.HOUR) * 60 + cal.get(Calendar.MINUTE);
		return mins;
	}

	private long calcSumTime(List<Integer> listAttTime) {
		long sum = 0;
		for (Integer data : listAttTime) {
			if (data != null) {
				sum += data;
			}
		}
		;
		return sum;
	}
	
	public static interface RequireM2 extends MonthlyStatutoryWorkingHours.RequireM1 {
		
		Optional<ClosureEmployment> employmentClosure(String companyID, String employmentCD);
		
		AffEmploymentHistoryDto affEmploymentHistory(String comapnyId, String employeeId, GeneralDate date);

		Integer getLimitFexMonth(String companyId);

		Optional<WorkingCondition> workingCondition(String historyId);

		Optional<WorkingConditionItem> workingConditionItem(String employeeId, GeneralDate baseDate);

		List<DateRange> getWorkConditionFlexDatePeriod(String employeeId, DatePeriod date); 

		List<Integer> findAtt(String employeeId, List<GeneralDate> ymd);
		
	}
	
	@RequiredArgsConstructor
	class RequireImpl implements CheckBeforeCalcFlexChange.RequireM2{

		private final CacheCarrier cacheCarrier;

		@Override
		public Integer getLimitFexMonth(String companyId) {
			return dailyPerformanceScreenRepo.getLimitFexMonth(companyId);
		}
		@Override
		public List<DateRange> getWorkConditionFlexDatePeriod(String employeeId, DatePeriod date) {
			return dailyPerformanceScreenRepo.getWorkConditionFlexDatePeriod(employeeId,date);
		}
		@Override
		public List<Integer> findAtt(String employeeId, List<GeneralDate> ymd) {
			return attendanceTime.findAtt(employeeId,ymd);
		}
		@Override
		public Optional<UsageUnitSetting> usageUnitSetting(String companyId) {
			return usageUnitSettingRepository.findByCompany(companyId);
		}
		@Override
		public List<String> getCanUseWorkplaceForEmp(CacheCarrier cacheCarrier, String companyId, String employeeId,
				GeneralDate baseDate) {

			return affWorkplaceAdapter.findAffiliatedWorkPlaceIdsToRootRequire(cacheCarrier, companyId, employeeId, baseDate);
		}
		@Override
		public Optional<ClosureEmployment> employmentClosure(String companyID, String employmentCD) {

			return closureEmploymentRepository.findByEmploymentCD(companyID, employmentCD);
		}
		@Override
		public AffEmploymentHistoryDto affEmploymentHistory(String comapnyId, String employeeId, GeneralDate date) {
			return dailyPerformanceScreenRepo.getAffEmploymentHistory(comapnyId, employeeId, date);
		}
		@Override
		public Optional<WorkingCondition> workingCondition(String historyId) {
			return workingConditionRepository.getByHistoryId(historyId);
		}
		@Override
		public Optional<WorkingConditionItem> workingConditionItem(String employeeId, GeneralDate baseDate) {
			return workingConditionItemRepository.getBySidAndStandardDate(employeeId, baseDate);
		}
		
		@Override
		public Optional<MonthlyWorkTimeSetSha> monthlyWorkTimeSetSha(String cid, String sid,
				LaborWorkTypeAttr laborAttr, YearMonth ym) {
			return monthlyWorkTimeSet.findEmployee(cid, sid, laborAttr, ym);
		}
		@Override
		public Optional<MonthlyWorkTimeSetEmp> monthlyWorkTimeSetEmp(String cid, String empCode,
				LaborWorkTypeAttr laborAttr, YearMonth ym) {
			return monthlyWorkTimeSet.findEmployment(cid, empCode, laborAttr, ym);
		}
		@Override
		public Optional<MonthlyWorkTimeSetCom> monthlyWorkTimeSetCom(String cid, LaborWorkTypeAttr laborAttr,
				YearMonth ym) {
			return monthlyWorkTimeSet.findCompany(cid, laborAttr, ym);
		}
		@Override
		public Optional<MonthlyWorkTimeSetWkp> monthlyWorkTimeSetWkp(String cid, String workplaceId,
				LaborWorkTypeAttr laborAttr, YearMonth ym) {
			return monthlyWorkTimeSet.findWorkplace(cid, workplaceId, laborAttr, ym);
		}
	}
}