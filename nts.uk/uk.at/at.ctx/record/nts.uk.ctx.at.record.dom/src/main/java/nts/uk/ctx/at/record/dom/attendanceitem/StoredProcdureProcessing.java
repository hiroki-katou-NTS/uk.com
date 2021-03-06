package nts.uk.ctx.at.record.dom.attendanceitem;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.YearMonth;
import nts.gul.util.value.MutableValue;
import nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemValueOfDailyRepo;
import nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemValueOfDailyTempo;
import nts.uk.ctx.at.shared.dom.common.anyitem.AnyTimesMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeOfExistMinus;
import nts.uk.ctx.at.shared.dom.scherec.anyitem.AnyItemNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeDivergenceWithCalculation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeDivergenceWithCalculationMinusExist;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.entranceandexit.PCLogOnNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.optionalitemvalue.AnyItemTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.optionalitemvalue.AnyItemTimes;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.optionalitemvalue.AnyItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.overtimehours.clearovertime.FlexTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.overtimehours.clearovertime.OverTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.ActualWorkingTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.outsideworktime.OverTimeFrameTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.AttendanceTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.AttendanceTimeOfMonthlyRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.anyitem.AnyItemOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.anyitem.AnyItemOfMonthlyRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneUse;
import nts.uk.ctx.at.shared.dom.worktime.predset.WorkNo;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.DailyWork;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeUnit;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.TimeWithDayAttr;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

@Stateless
public class StoredProcdureProcessing implements StoredProcdureProcess {
	
	/**I????????????*/
	private static final WorkTypeCode DEFAULT_WORK_TYPE = new WorkTypeCode("100");
	
	/**I?????????*/
	private static final List<WorkTimeCode> DEFAULT_WORK_TIME = Arrays.asList(new WorkTimeCode("100"), new WorkTimeCode("101"));
	
	private static final List<Integer> MONTHKY_ANYITEM_TO_PROCESS = Arrays.asList(51, 52, 53, 45, 46, 1, 2);
	
	@Inject
	private WorkTypeRepository workTypeRepo;
	
	@Inject
	private PredetemineTimeSettingRepository predetermineRepo;
	
	@Inject
	private AttendanceTimeOfMonthlyRepository attendanceTimeOfMonthly;
	
	@Inject
	private WorkTimeSettingRepository workTimeRepository;
	
	@Inject
	private AnyItemValueOfDailyRepo dailyOptionalItem;
	
	@Inject
	private AnyItemOfMonthlyRepository monthlyOptionalItem;
	
	private static final BigDecimal COUNT_ON = BigDecimal.ONE;
	private static final BigDecimal COUNT_OFF = BigDecimal.ZERO;
	private static final BigDecimal V100 = BigDecimal.valueOf(100);
	private static final WorkTypeCode PREMIUM_CODE = new WorkTypeCode("105");
	private static final WorkTypeCode WORKTYPE_NO2 = new WorkTypeCode("002");
	
	@Override
	public Optional<DailyStoredProcessResult> dailyProcessing(IntegrationOfDaily daily, Optional<WorkType> workTypeOpt, 
			Optional<WorkTimeSetting>  workTimeOpt, Optional<PredetemineTimeSetting> predSetOpt) {

		String companyId = AppContexts.user().companyId();
		WorkType workType = getWorkType(companyId, daily.getWorkInformation().getRecordInfo().getWorkTypeCode().v(), workTypeOpt);

		if(workType == null){
			return Optional.empty();
		}
		
		DailyTimeForCalc dailyTime = new DailyTimeForCalc(companyId, daily, workType, workTimeOpt, predSetOpt);
		
		List<AnyItemValue> optionalItems = calcOptionalItem(workType, dailyTime);
		List<OverTimeFrameTime> overTimes = calcOverTime(daily, workType, dailyTime.have(t -> t.startTime));
		
		return Optional.of(new DailyStoredProcessResult(optionalItems, overTimes));
	}
	
	private List<AnyItemValue> calcOptionalItem(WorkType workType, DailyTimeForCalc dailyTime) {
		List<AnyItemValue> result = new ArrayList<>();  
		
		boolean isWorkingType = checkWorkType(workType.getDailyWork().getWorkTypeUnit(), workType.getDailyWork().getOneDay(),
											workType.getDailyWork().getMorning(), workType.getDailyWork().getAfternoon());
		boolean hasAttendanceTime = dailyTime.have(t -> t.startTime);
		boolean pcLogoned = dailyTime.have(t -> t.logonTime);
		boolean pcLogoffed = dailyTime.have(t -> t.logoffTime);
		boolean hasAttendanceLeaveGateStartTime = dailyTime.have(t -> t.leaveGateStartTime);
		boolean hasAttendanceLeaveGateEndTime = dailyTime.have(t -> t.leaveGateEndTime);
		boolean isActualWork = dailyTime.have(t -> t.actualWorkTime) && dailyTime.value(t -> t.actualWorkTime) > 0;
		boolean isNotWorkWithinStatutory = !dailyTime.have(t -> t.withinStatutoryTime) || dailyTime.value(t -> t.withinStatutoryTime) == 0;
		boolean isNotSpecialWorkType = !dailyTime.value(t -> t.isPremiumWorkType) && !workType.getWorkTypeCode().equals(WORKTYPE_NO2);
		
		/** ????????????1: ?????????????????????null???????????????????????? */
		processCountOptionalItem(() -> pcLogoned && isWorkingType && isActualWork, result, COUNT_ON, COUNT_OFF, 1);

		/** ????????????2: ?????????????????? > 0 ????????????????????? */
		processCountOptionalItem(() -> pcLogoffed && isWorkingType && isActualWork, result, COUNT_ON, COUNT_OFF, 2);
		
		/** ????????????3: ?????????????????????????????????PC????????????????????????????????????????????? */
		processCountOptionalItem(() -> hasAttendanceTime && !pcLogoned && !pcLogoffed, result, COUNT_ON, COUNT_OFF, 3);
		
		/** ????????????4: ????????????PC???????????? = null and ???????????? <> null ????????? */
		processCountOptionalItem(() -> hasAttendanceTime && !pcLogoned && pcLogoffed, result, COUNT_ON, COUNT_OFF, 4);
		
		/** ????????????5: ????????????PC???????????? = null and ???????????? <> null ????????? */
		processCountOptionalItem(() -> hasAttendanceTime && pcLogoned && !pcLogoffed, result, COUNT_ON, COUNT_OFF, 5);
		
		/** ????????????6: ??????????????????????????????????????????????????????????????? */
		processCountOptionalItem(() -> pcLogoned && pcLogoffed, result, COUNT_ON, COUNT_OFF, 6);
		
		/** ????????????7: ??????????????? */
		processTimeOptionalItem(() -> isWorkingType && isActualWork, result, dailyTime.value(t -> t.timeOn), dailyTime.value(t -> t.timeOff), 7);
		
		dailyTime.value(t -> t.timeOn, dailyTime.value(t -> t.leaveGateEndTime));
		
		/** ????????????9: ??????????????? */
		processTimeOptionalItem(() -> isWorkingType && isActualWork && isNotSpecialWorkType, result, dailyTime.value(t -> t.timeOn), dailyTime.value(t -> t.timeOff), 9);

		/** ????????????11: ?????????????????? = 0 ?????? ???????????????(????????????????????????) ????????????????????? */
		processCountOptionalItem(() -> isWorkingType && isNotWorkWithinStatutory, result, COUNT_ON, COUNT_OFF, 11);
		
		/** ????????????12 */
		processCountOptionalItem(() -> isWorkingType && isActualWork, result, COUNT_ON, COUNT_OFF, 12);
		
		/** ????????????13: ????????????????????????????????????????????????????????????????????? */
		processCountOptionalItem(() -> hasAttendanceTime && !hasAttendanceLeaveGateStartTime && !hasAttendanceLeaveGateEndTime, result, COUNT_ON, COUNT_OFF, 13);
		
		/** ????????????14: ????????????PC???????????? = null ????????? */
		processCountOptionalItem(() -> hasAttendanceTime && !pcLogoned, result, COUNT_ON, COUNT_OFF, 14);
		
		/** ????????????15: ????????????PC???????????? = null ????????? */
		processCountOptionalItem(() -> hasAttendanceTime && !pcLogoffed, result, COUNT_ON, COUNT_OFF, 15);
		
		/** ????????????16: ?????????????????????????????????PC???????????????????????????????????????????????????????????? */
		processCountOptionalItem(() -> hasAttendanceTime && (!pcLogoned || !pcLogoffed), result, COUNT_ON, COUNT_OFF, 16);
		
		dailyTime.value(t -> t.timeOff, 0);
		
		/** ????????????8: ????????????????????????????????????????????????????????????????????? */
		dailyTime.calcStartDivergenceTime();
		processTimeOptionalItem(() -> isWorkingType && isActualWork, result, dailyTime.value(t -> t.timeOn), dailyTime.value(t -> t.timeOff), 8);
		
		/** ????????????10 */
		dailyTime.calcEndDivergenceTime();
		processTimeOptionalItem(() -> isWorkingType && isActualWork && isNotSpecialWorkType, result, dailyTime.value(t -> t.timeOn), dailyTime.value(t -> t.timeOff), 10);
		
		dailyTime.value(t -> t.timeOn, 1);
		
		/** ????????????17: ?????????*/
		processTimeOptionalItem(() -> isWorkingType, result, dailyTime.value(t -> t.divergenceTime), dailyTime.value(t -> t.timeOff), 17);
		
		/** ????????????19: ????????????1~10 > 0 ????????????????????????????????????????????????????????? */
//		processCountOptionalItem(() -> dailyTime.value(t -> t.timePre) > 0 && dailyTime.overTime.stream().allMatch(t -> t <= 0) && dailyTime.value(t -> t.timeFlex) <= 0, 
//				result, COUNT_ON, COUNT_OFF, 19);
		
		/** ????????????21: ????????????1~10 > 0 ?????????????????????????????????????????????????????? */
//		processCountOptionalItem(() -> dailyTime.value(t -> t.timePre) > 0 && (dailyTime.value(t -> t.timeFlex) > 0 || dailyTime.overTime.stream().anyMatch(t -> t > 0)),
//				result, COUNT_ON, COUNT_OFF, 21);	
		
		/** ????????????27: ???????????? ?????? ?????????????????????????????? */
		processCountOptionalItem(() -> dailyTime.value(t -> t.totalOverTime) > 0, result, COUNT_ON, COUNT_OFF, 27);
		
		/** ????????????28: ?????????????????? > 0 ????????????????????? */
		processCountOptionalItem(() -> dailyTime.value(t -> t.timePre) > 0, result, COUNT_ON, COUNT_OFF, 28);
		
		/** ????????????29???  ??? */
		processTimeOptionalItem(() -> dailyTime.value(t -> t.preSetDivergence) > 0, result, dailyTime.value(t -> t.preSetDivergence), 0, 29);
		
		boolean isItem12on = result.parallelStream().anyMatch(c -> c.getItemNo().v() == 12 && c.getTimes().get().v().equals(COUNT_ON));
		
		dailyTime.calcSomeItemByPredetemineTime(isItem12on);
		
		/** ????????????35 */
		processCountOptionalItem(() -> dailyTime.value(t -> t.flag35), result, COUNT_ON, COUNT_OFF, 35);
		
		/** ????????????36 */
		processCountOptionalItem(() -> dailyTime.value(t -> t.flag36), result, COUNT_ON, COUNT_OFF, 36);
		
		/** ????????????19 */
		processCountOptionalItem(() -> dailyTime.value(t -> t.flag19) > 0, result, BigDecimal.valueOf((long) dailyTime.value(t -> t.flag19).longValue()), COUNT_OFF, 19);
		
		/** ????????????21 */
		processCountOptionalItem(() -> dailyTime.value(t -> t.flag21) > 0, result, BigDecimal.valueOf((long) dailyTime.value(t -> t.flag21).longValue()), COUNT_OFF, 21);
		
		/** ????????????37 processOptionalItem(() -> dailyTime.value(t -> t.flag37) > 0, result, BigDecimal.valueOf(dailyTime.value(t -> t.flag37)), COUNT_OFF, 37); */
		
		/** ????????????40: ????????????????????????????????????????????????????????? */
		processTimeOptionalItem(() -> dailyTime.value(t -> t.flexTime) < 0, result, dailyTime.value(t -> t.flexTime) * -1, dailyTime.value(t -> t.timeOff), 40);
		
		/** ????????????41: ?????????????????????????????????????????????????????? */
		processTimeOptionalItem(() -> dailyTime.value(t -> t.flexTime) >= 0, result, dailyTime.value(t -> t.flexTime), dailyTime.value(t -> t.timeOff), 41);
		
		/** ????????????59: */
		processTimeOptionalItem(() -> hasAttendanceLeaveGateEndTime && dailyTime.value(t -> t.leaveGateEndTime) > 0 && isItem12on, result, dailyTime.value(t -> t.leaveGateEndTime), 0, 59);
		
		/** ????????????72: */
		processCountOptionalItem(() -> pcLogoffed && dailyTime.value(t -> t.logoffTime) > 0 && isNotSpecialWorkType && isItem12on, result, COUNT_ON, COUNT_OFF, 72);

		dailyTime.calcBreakTime();
		
		/** ????????????82: */
		processTimeOptionalItem(() -> isItem12on, result, dailyTime.value(t -> t.timeOn), 0, 82);
		
		boolean flagFor89And90 = hasAttendanceTime && !dailyTime.value(t -> t.isPremiumWorkType) && isItem12on;
		/** ????????????89 */
		processTimeOptionalItem(() -> flagFor89And90, result, dailyTime.value(t -> t.startTime), dailyTime.value(t -> t.timeOff), 89);
		
		/** ????????????90 */
		processCountOptionalItem(() -> flagFor89And90, result, COUNT_ON, COUNT_OFF, 90);
		
		return result;
	}

	private List<OverTimeFrameTime> calcOverTime(IntegrationOfDaily daily, WorkType workType, boolean isGoToWork) {
		if(DEFAULT_WORK_TYPE.equals(daily.getWorkInformation().getRecordInfo().getWorkTypeCode())) {
			return setOverTime(30, 60);
		}
		
		if(DEFAULT_WORK_TIME.contains(daily.getWorkInformation().getRecordInfo().getWorkTimeCode()) && workType != null) {
			if(workType.getDailyWork().getOneDay() == WorkTypeClassification.HolidayWork 
					|| workType.getDailyWork().getAfternoon() == WorkTypeClassification.HolidayWork 
					|| workType.getDailyWork().getMorning() == WorkTypeClassification.HolidayWork){
				return setOverTime(0, 0);
			} else {
				return setOverTime(!isGoToWork ? 0 : 30, !isGoToWork ? 0 : 60);
			}
		}
		
		return new ArrayList<>();
	}
	
	private List<OverTimeFrameTime> setOverTime(int timeFor4, int timeFor5) {
		List<OverTimeFrameTime> overTimes = new ArrayList<>();
		
		overTimes.add(createOverTimeFrame(4, timeFor4));
		overTimes.add(createOverTimeFrame(5, timeFor5));
		
		return overTimes;
	}

	private OverTimeFrameTime createOverTimeFrame(int no, int val){
		return new OverTimeFrameTime(new OverTimeFrameNo(no), 
				TimeDivergenceWithCalculation.sameTime(new AttendanceTime(val)), 
				TimeDivergenceWithCalculation.sameTime(AttendanceTime.ZERO), 
				AttendanceTime.ZERO, AttendanceTime.ZERO);
	}
	
	@Override
	public List<AnyItemOfMonthly> monthlyProcessing(String companyId, String employeeId, YearMonth yearMonth, ClosureId closureId,
			ClosureDate closureDate) {
		return monthlyProcessing(companyId, employeeId, yearMonth, closureId, closureDate, Optional.empty(), new ArrayList<>());
	}
	
	@Override
	public List<AnyItemOfMonthly> monthlyProcessing(String companyId, String employeeId, YearMonth yearMonth, ClosureId closureId,
			ClosureDate closureDate, Optional<AttendanceTimeOfMonthly> attendanceTime) {
		return monthlyProcessing(companyId, employeeId, yearMonth, closureId, closureDate, attendanceTime, new ArrayList<>());
	}
	
	@Override
	public List<AnyItemOfMonthly> monthlyProcessing(String companyId, String employeeId, YearMonth yearMonth, ClosureId closureId,
			ClosureDate closureDate, Optional<AttendanceTimeOfMonthly> attendanceTime, List<AnyItemOfMonthly> monthlyOptionalItems) {
		
		/** ????????????????????????????????? */
		if (!AppContexts.optionLicense().customize().ootsuka())
			return monthlyOptionalItems;
		
		/** ?????????????????????????????? */
		if(!attendanceTime.isPresent()){
			attendanceTime = attendanceTimeOfMonthly.find(employeeId, yearMonth, closureId, closureDate);
		}
		
		if(!attendanceTime.isPresent())
			return new ArrayList<>(monthlyOptionalItems);
		
		
		/** ?????????????????????????????? */
		List<AnyItemValueOfDailyTempo> optionalItemsInMonth = dailyOptionalItem.finds(Arrays.asList(employeeId), attendanceTime.get().getDatePeriod())
				.stream().map(x-> new AnyItemValueOfDailyTempo(x.getEmployeeId(), x.getYmd(), x.getAnyItem().getItems())).collect(Collectors.toList());
		List<AnyItemValue> items = optionalItemsInMonth.stream().map(AnyItemValueOfDailyTempo::getItems).flatMap(List::stream).collect(Collectors.toList());
		
		
		List<AnyItemOfMonthly> dataToProcess = new ArrayList<>(monthlyOptionalItems);

		if(dataToProcess.isEmpty()){
			dataToProcess = monthlyOptionalItem.find(employeeId, yearMonth, closureId, closureDate, MONTHKY_ANYITEM_TO_PROCESS);
		} else {
			List<Integer> notExist = new ArrayList<>(MONTHKY_ANYITEM_TO_PROCESS);
			
			notExist.removeAll(dataToProcess.stream().map(AnyItemOfMonthly::getAnyItemId).distinct().collect(Collectors.toList()));
			
			if(!notExist.isEmpty()){
				dataToProcess.addAll(monthlyOptionalItem.find(employeeId, yearMonth, closureId, closureDate, notExist));
			}
		}
		
		double count19 = sumCountItem(items, 19); 
		double count21 = sumCountItem(items, 21); 
		double count37 = sumCountItem(items, 37);
		double count45 = dataToProcess.stream().filter(c -> c.getAnyItemId() == 45)
											.mapToDouble(c -> c.getTimes().orElseGet(() -> new AnyTimesMonth(0d)).v().doubleValue()).sum();
		
		/** ????????????51: ??????????????? */
		accessMonthlyItem(employeeId, yearMonth, closureId, closureDate, 51, getOnDefault(count19, count45), dataToProcess);
		
		/** ????????????52: ??????????????? */
		accessMonthlyItem(employeeId, yearMonth, closureId, closureDate, 52, getOnDefault(count21, count45), dataToProcess);
		
		/** ????????????53: ??????????????? */
		accessMonthlyItem(employeeId, yearMonth, closureId, closureDate, 53, getOnDefault(count37, count45), dataToProcess);
		
		/** ????????????46: ??????????????? */
		accessMonthlyItem(employeeId, yearMonth, closureId, closureDate, 46, sumFor46(dataToProcess), dataToProcess);
		
		monthlyOptionalItem.persistAndUpdate(dataToProcess);
		
		return dataToProcess;
	}
	
	private void accessMonthlyItem(String employeeId, YearMonth yearMonth, ClosureId closureId,
			ClosureDate closureDate, int itemNo, double val, List<AnyItemOfMonthly> monthlyOptionalItems){
		AnyTimesMonth value = new AnyTimesMonth(val);
		if(monthlyOptionalItems.stream().anyMatch(m -> m.getAnyItemId() == itemNo)){
			monthlyOptionalItems.stream().filter(mi -> mi.getAnyItemId() == itemNo).findFirst().ifPresent(mi -> mi.updateTimes(value));
		} else {
			monthlyOptionalItems.add(AnyItemOfMonthly.of(employeeId, yearMonth, closureId, 
					closureDate, itemNo, Optional.empty(), Optional.of(value), Optional.empty()));
		}
	}
	
	private double sumFor46(List<AnyItemOfMonthly> monthlyOptionalItems){
		return monthlyOptionalItems.stream().filter(mo -> mo.getAnyItemId() == 1 || mo.getAnyItemId() == 2)
				.mapToInt(mo -> mo.getTimes().orElseGet(() -> new AnyTimesMonth(0d)).v().intValue()).sum();
	}

	private double sumCountItem(List<AnyItemValue> items, int itemNo) {
		return items.stream().filter(o -> o.getItemNo().v() == itemNo)
					.mapToDouble(ai -> ai.getTimes().orElseGet(() -> new AnyItemTimes(COUNT_OFF)).v().doubleValue())
					.sum();
	}

	private WorkType getWorkType(String companyId, String workTypeCode, Optional<WorkType> workType) {
		return workType.orElseGet(() -> workTypeRepo.findByPK(companyId, workTypeCode).orElse(null));
	}	
	
	private double getOnDefault(double upper, double downer) {
		if(downer == 0 || upper == 0){
			return 0;
		}
		return BigDecimal.valueOf(upper).divide(BigDecimal.valueOf(downer), new MathContext(5, RoundingMode.HALF_UP)).multiply(V100).setScale(1, RoundingMode.HALF_UP).doubleValue();
	}

	private boolean checkWorkType(WorkTypeUnit atr, WorkTypeClassification oneDay, WorkTypeClassification morning,
			WorkTypeClassification afternoon) {
		return (atr == WorkTypeUnit.OneDay && checkWorkType(oneDay)) || 
				(atr == WorkTypeUnit.MonringAndAfternoon && (checkWorkType(morning) || checkWorkType(afternoon)));
	}
	
	private boolean checkWorkType(WorkTypeClassification atr) {
		return atr == WorkTypeClassification.Attendance || atr == WorkTypeClassification.Shooting
				|| atr == WorkTypeClassification.SpecialHoliday || atr == WorkTypeClassification.AnnualHoliday;
	}

	private void processCountOptionalItem(BooleanSupplier condition, List<AnyItemValue> items,
			BigDecimal valueOn, BigDecimal valueOff, int... itemNo){
		if(condition.getAsBoolean()){
			addOptionalItemWithNo(items, valueOn, itemNo);
		} else {
			addOptionalItemWithNo(items, valueOff, itemNo);
		}
	}
	
	private void processTimeOptionalItem(BooleanSupplier condition, List<AnyItemValue> items,
			Integer valueOn, Integer valueOff, int... itemNo){
		if(condition.getAsBoolean()){
			addOptionalItemWithNo(items, valueOn, itemNo);
		} else {
			addOptionalItemWithNo(items, valueOff, itemNo);
		}
	}
	
	private void addOptionalItemWithNo(List<AnyItemValue> items, BigDecimal count, int... itemNo) {
		AnyItemTimes val = count == null ? null : new AnyItemTimes(count);
		for (int no : itemNo) {
			items.add(new AnyItemValue(new AnyItemNo(no), Optional.ofNullable(val), Optional.empty(), Optional.empty()));
		}
	}
	
	private void addOptionalItemWithNo(List<AnyItemValue> items, Integer time, int... itemNo) {
		AnyItemTime val = time == null ? null : new AnyItemTime(time);
		for (int no : itemNo) {
			items.add(new AnyItemValue(new AnyItemNo(no), Optional.empty(), Optional.empty(), Optional.ofNullable(val)));
		}
	}
	
	@AllArgsConstructor
	public class DailyStoredProcessResult {
		List<AnyItemValue> optionalItems;
		@Getter
		List<OverTimeFrameTime> overTimes;
		
		public Optional<AnyItemValue>  getOptionalItemBySearchNo(String searchNo){
			return optionalItems.stream().filter(itm -> itm.getItemNo().toString().equals(searchNo)).findFirst();
		}
		
		public boolean isInclude(String searchNo) {
			return this.getOptionalItemBySearchNo(searchNo).isPresent();
		}
	}
	
	private class DailyTimeForCalc {
		private MutableValue<Integer> actualWorkTime = new MutableValue<>(); 
		private MutableValue<Integer> withinStatutoryTime = new MutableValue<>(); 
		private MutableValue<Integer> logonTime = new MutableValue<>(); 
		private MutableValue<Integer> logoffTime = new MutableValue<>(); 
		private MutableValue<Integer> leaveGateStartTime = new MutableValue<>(); 
		private MutableValue<Integer> leaveGateEndTime = new MutableValue<>();
		private MutableValue<Integer> startTime = new MutableValue<>();
		private MutableValue<Integer> endTime = new MutableValue<>();
		private MutableValue<Integer> timeOff = new MutableValue<>();
		private MutableValue<Integer> timeOn = new MutableValue<>();
		private MutableValue<Integer> divergenceTime = new MutableValue<>(0);  
		private MutableValue<Integer> timePreFlex = new MutableValue<>(0);
		private MutableValue<Integer> flexTime = new MutableValue<>(0); 
		private MutableValue<Integer> timeFlex = new MutableValue<>(0);
		private MutableValue<Integer> timePre = new MutableValue<>(0);
		private MutableValue<Integer> totalOverTime = new MutableValue<>(0);
		private MutableValue<Integer> preSetDivergence = new MutableValue<>(0); 
		private MutableValue<Integer> flag19 = new MutableValue<>(0);
		private MutableValue<Integer> flag21 = new MutableValue<>(0);
		/** MutableValue<Integer> flag37 = new MutableValue<>(0); */
		private MutableValue<Boolean> flag35 = new MutableValue<>(false);
		private MutableValue<Boolean> flag36 = new MutableValue<>(false);
		private MutableValue<Boolean> isPremiumWorkType = new MutableValue<>(false);

		private List<Integer> overTime = new ArrayList<>();
		private List<Integer> preOver = new ArrayList<>();
		
		private String companyId; 
		private IntegrationOfDaily daily; 
		private WorkType workType; 
		private Optional<WorkTimeSetting>  workTimeOpt; 
		private Optional<PredetemineTimeSetting> predSetOpt;
		
		DailyTimeForCalc(String companyId, IntegrationOfDaily daily, WorkType workType, Optional<WorkTimeSetting>  workTimeOpt, Optional<PredetemineTimeSetting> predSetOpt) {
			this.isPremiumWorkType.set(daily.getWorkInformation().getRecordInfo().getWorkTypeCode().equals(PREMIUM_CODE));
			this.daily = daily;
			this.workType = workType;
			this.workTimeOpt = workTimeOpt;
			this.predSetOpt = predSetOpt;
			
			calcAttendanceLeave(daily);
			
			calcPCLogOnInfo(daily);
			
			calcAttendanceLeaveGate(daily);
			
			calcAttendanceTime(daily);
			/** calcSomeItemByPredetemineTime(daily, companyId, workTimeOpt, predSetOpt, workType.getDailyWork()); */
		}
	
		<T> T value(Function<DailyTimeForCalc, MutableValue<T>> time) {
			MutableValue<T> value = time.apply(this);
			return value.optional().isPresent() ? value.get() : null;
		}
		
		<T> boolean have(Function<DailyTimeForCalc, MutableValue<T>> time) {
			MutableValue<T> value = time.apply(this);
			return value.optional().isPresent();
		}
		
		<T> void value(Function<DailyTimeForCalc, MutableValue<T>> time, T val) {
			MutableValue<T> value = time.apply(this);
			value.set(val);
		}

		public void calcStartDivergenceTime() {
			if (!have(t -> t.startTime) || !have(t -> leaveGateStartTime)) {
				this.divergenceTime.set(0);
				this.timeOn.set(0);
				return;	
			}
			
			int divergence = startTime.get() < leaveGateStartTime.get() ? 0 : startTime.get() - leaveGateStartTime.get();
			
			this.divergenceTime.set(divergence);
			this.timeOn.set(divergence);
		}
		
		public void calcEndDivergenceTime() {
			if (!have(t -> t.endTime) || !have(t -> leaveGateEndTime)) {
				this.timeOn.set(0);
				return;
			}
			
			this.timeOn.set(leaveGateEndTime.get() < endTime.get() ? 0 : leaveGateEndTime.get() - endTime.get());
		}
		
		public void calcBreakTime(){
			if (have(t -> t.actualWorkTime)) {
				this.daily.getAttendanceTimeOfDailyPerformance().ifPresent(atd -> {
					AttendanceTimeOfExistMinus breakTime = new AttendanceTimeOfExistMinus(atd.getActualWorkingTimeOfDaily().getTotalWorkingTime()
							.getBreakTimeOfDaily().getToRecordTotalTime().getWithinStatutoryTotalTime().getTime().valueAsMinutes());
					if(this.actualWorkTime.get() > AttendanceTime.ZERO.addHours(8).valueAsMinutes()) {
						this.timeOn.set(breakTime.addHours(-1).addMinutes(-30).valueAsMinutes());
					} else {
						this.timeOn.set(breakTime.addHours(-1).valueAsMinutes());
					}
				});
				return;
			}
			
			this.timeOn.set(0);
		}
		
		private void calcAttendanceTime(IntegrationOfDaily daily) {
			daily.getAttendanceTimeOfDailyPerformance().ifPresent(at -> {
				actualWorkTime.set(at.getActualWorkingTimeOfDaily().getTotalWorkingTime().getActualTime().valueAsMinutes());
				withinStatutoryTime.set(at.getActualWorkingTimeOfDaily().getTotalWorkingTime().getWithinStatutoryTimeOfDaily().getActualWorkTime().valueAsMinutes());
				getOverTime(at).ifPresent(ot -> {
					ot.getOverTimeWorkFrameTime().stream().forEach(o -> {
						overTime.add(sumActualOvertime(o) - getAttendanceTime(o.getBeforeApplicationTime()));
						preOver.add(getAttendanceTime(o.getBeforeApplicationTime()));
					});
					
					FlexTime flex = getFlexTime(ot.getFlexTime());
					
					timePreFlex.set(getAttendanceTime(flex.getBeforeApplicationTime()));
					flexTime.set(getAttendanceTime(flex.getFlexTime().getTime())); 
					timeFlex.set(flexTime.get() > 0 ? flexTime.get() - timePreFlex.get() : 0);
					timePre.set(preOver.stream().mapToInt(t -> t).sum() + timePreFlex.get());
					totalOverTime.set(overTime.stream().mapToInt(t -> t).sum() + preOver.stream().mapToInt(t -> t).sum() + flexTime.get());
				});
			});
		}

		private void calcAttendanceLeaveGate(IntegrationOfDaily daily) {
			daily.getAttendanceLeavingGate().ifPresent(alg -> 
				alg.getAttendanceLeavingGate(new WorkNo(1)).ifPresent(alw -> {
					alw.getAttendance().ifPresent(as -> { 
						int time = as.getTimeDay().getTimeWithDay() == null ||  !as.getTimeDay().getTimeWithDay().isPresent() ? 0 : as.getTimeDay().getTimeWithDay().get().valueAsMinutes();
						leaveGateStartTime.set(time); 
						timeOn.set(time);
					});
					alw.getLeaving().ifPresent(as -> leaveGateEndTime.set(as.getTimeDay().getTimeWithDay() == null ||  !as.getTimeDay().getTimeWithDay().isPresent() ? 0 : as.getTimeDay().getTimeWithDay().get().valueAsMinutes()));
				})
			);
		}

		private void calcPCLogOnInfo(IntegrationOfDaily daily) {
			daily.getPcLogOnInfo().ifPresent(al -> 
				al.getLogOnInfo(new PCLogOnNo(1)).ifPresent(loi -> {
					loi.getLogOn().ifPresent(s -> logonTime.set(s.valueAsMinutes()));
					loi.getLogOff().ifPresent(s -> logoffTime.set(s.valueAsMinutes()));
				})
			);
		}

		private void calcAttendanceLeave(IntegrationOfDaily daily) {
			daily.getAttendanceLeave().ifPresent(al -> 
				al.getAttendanceLeavingWork(1).ifPresent(alw -> {
					alw.getAttendanceStamp().ifPresent(as -> as.getStamp().ifPresent(s -> startTime.set(!s.getTimeDay().getTimeWithDay().isPresent() || s.getTimeDay().getTimeWithDay() == null ? 0 : s.getTimeDay().getTimeWithDay().get().valueAsMinutes())));
					alw.getLeaveStamp().ifPresent(as -> as.getStamp().ifPresent(s -> endTime.set(!s.getTimeDay().getTimeWithDay().isPresent() || s.getTimeDay().getTimeWithDay() == null ? 0 : s.getTimeDay().getTimeWithDay().get().valueAsMinutes())));
				})
			);
		}
		
		public void calcSomeItemByPredetemineTime(boolean isItem12On) {
			if(daily.getWorkInformation().getRecordInfo().getWorkTimeCode() != null) {
				WorkTimeSetting wtime = workTimeOpt.orElseGet(() -> workTimeRepository.findByCode(companyId, daily.getWorkInformation().getRecordInfo().getWorkTimeCode().v()).orElse(null));
				PredetemineTimeSetting predSet = predSetOpt.orElseGet(() -> predetermineRepo.findByWorkTimeCode(companyId, daily.getWorkInformation().getRecordInfo().getWorkTimeCode().v()).orElse(null));
				if(wtime != null && predSet != null) {
					calcValueForSomeItem(daily, predSet, isItem12On);
					
					calcPreSetDiverence(wtime, workType.getDailyWork(), predSet);
				}		
			}
		}
		
		private void calcPreSetDiverence(WorkTimeSetting wtime, DailyWork dailyWork, PredetemineTimeSetting predSet) {
					
			if(dailyWork.isWeekDayAttendance() && !(wtime.getWorkTimeDivision().getWorkTimeDailyAtr().isFlex())) {
				//?????????????????????
				TimeWithDayAttr startOclock = new TimeWithDayAttr(0);
				Optional<TimezoneUse> firstTimeZone =  predSet.getPrescribedTimezoneSetting().getLstTimezone().stream().filter(tc -> tc.getWorkNo() == 1).findFirst();
				if(firstTimeZone.isPresent()) {
					startOclock = firstTimeZone.get().getStart();
				}
				//????????????
				if(have(t -> t.startTime)) {
					preSetDivergence.set(startOclock.backByMinutes(startTime.get()).valueAsMinutes());
				}
			}
		}

		private void calcValueForSomeItem(IntegrationOfDaily daily, PredetemineTimeSetting predSet, boolean isItem12On) {
			predSet.getTimeSheetOf(1).ifPresent(timeSheet -> {
				int preSetAtten01 = timeSheet.getStart().valueAsMinutes();
				int preSetLeave01 = timeSheet.getEnd().valueAsMinutes();
				
				if(have(t -> t.startTime) && startTime.get() < preSetAtten01 && isItem12On) {
					flag35.set(true);
				}
				
				if(have(t -> t.endTime) && endTime.get() > preSetLeave01 && isItem12On) {
					flag36.set(true);
				}
				
				if(!flag35.get() && !flag36.get()) {
					return;
				}
				
				calc19And21Item(daily, preSetAtten01, preSetLeave01);
			});
		}

		private void calc19And21Item(IntegrationOfDaily daily, int preSetAtten01, int preSetLeave01) {
			daily.getWorkInformation().getScheduleTimeSheet(new WorkNo(1)).ifPresent(scheTimeSheet -> {
				int scheWorkAtten = scheTimeSheet.getAttendance().valueAsMinutes();
				int scheWorkLeave = scheTimeSheet.getLeaveWork().valueAsMinutes();
				
				if(flag35.get() && have(t -> t.startTime) && preSetAtten01 > scheWorkAtten) {
					if(startTime.get() >= scheWorkAtten) {
						flag19.set(flag19.get() + 1);
					}
					if(startTime.get() < scheWorkAtten) {
						flag21.set(flag21.get() + 1);
					}
				}
				
				if(flag36.get() && have(t -> t.endTime) && preSetLeave01 < scheWorkLeave) {
					if(endTime.get() <= scheWorkLeave) {
						flag19.set(flag19.get() + 1);
					}
					if(endTime.get() > scheWorkLeave) {
						flag21.set(flag21.get() + 1);
					}
				}
				/**
				if (flag35.get() && preSetAtten01 <= scheWorkAtten) {
					flag37.set(flag37.get() + 1);
				}
				if(flag36.get() && preSetLeave01 >= scheWorkLeave) {
					flag37.set(flag37.get() + 1);
				}
				*/
			});
		}

		private int sumActualOvertime(OverTimeFrameTime otf) {
			return getTimefrom(otf.getOverTimeWork()) + getTimefrom(otf.getTransferTime());
		}
		
		private int getAttendanceTime(AttendanceTime time) {
			return time == null ? 0 : time.valueAsMinutes();
		}
		
		private int getAttendanceTime(AttendanceTimeOfExistMinus time) {
			return time == null ? 0 : time.valueAsMinutes();
		}
		
		private int getTimefrom(TimeDivergenceWithCalculation time){
			return time == null ? 0 : getAttendanceTime(time.getTime());
		}
		
		private FlexTime getFlexTime(FlexTime flexTime){ 
			if(flexTime != null ){
				return flexTime;
			}
			return new FlexTime(TimeDivergenceWithCalculationMinusExist.sameTime(AttendanceTimeOfExistMinus.ZERO), AttendanceTime.ZERO);
		}
		
		private Optional<OverTimeOfDaily> getOverTime(AttendanceTimeOfDailyAttendance attendanceTime){
			ActualWorkingTimeOfDaily actual = attendanceTime.getActualWorkingTimeOfDaily();
			if(actual == null || actual.getTotalWorkingTime() == null
				|| actual.getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily() == null){
						return Optional.empty();
			}
			return actual.getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getOverTimeWork();
		}
	}

}

