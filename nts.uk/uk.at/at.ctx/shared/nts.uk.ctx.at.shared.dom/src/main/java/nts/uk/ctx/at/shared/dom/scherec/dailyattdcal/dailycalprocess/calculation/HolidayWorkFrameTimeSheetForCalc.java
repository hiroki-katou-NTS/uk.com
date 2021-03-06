package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.AddSettingOfWorkingTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.ActualWorkTimeSheetAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalRestTimeSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.setting.BonusPaySetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeDivergenceWithCalculation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.holidayworktime.HolidayWorkFrameTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.holidayworktime.HolidayWorkFrameTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.paytime.SpecificDateAttrOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.MidNightTimeSheetForCalcList;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.StaggerDiductionTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.TimeSheetOfDeductionItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.someitems.BonusPayTimeSheetForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.midnighttimezone.MidNightTimeSheet;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.HolidayWorkFrameNo;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.StaturoryAtrOfHolidayWork;
import nts.uk.ctx.at.shared.dom.worktime.IntegrationOfWorkTime;
import nts.uk.ctx.at.shared.dom.worktime.common.BreakFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimezoneNo;
import nts.uk.ctx.at.shared.dom.worktime.common.HDWorkTimeSheetSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneGoOutSet;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkHolidayTimeZone;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSetting;
import nts.uk.ctx.at.shared.dom.worktype.HolidayAtr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * ???????????????????????????
 * @author keisuke_hoshina
 */
@Getter
public class HolidayWorkFrameTimeSheetForCalc extends ActualWorkingTimeSheet{
	
	//??????????????????No
	private HolidayWorkFrameTime frameTime;
	
	//???????????????????????????
	private boolean TreatAsTimeSpentAtWork;
	
	//???????????????
	private EmTimezoneNo HolidayWorkTimeSheetNo; 
	
	//????????????
	private Finally<StaturoryAtrOfHolidayWork> statutoryAtr;
	
	/**
	 * constructor
	 * @param timeSheet ?????????(????????????)
	 * @param calculationTimeSheet ????????????
	 * @param deductionTimeSheets ????????????????????????
	 * @param bonusPayTimeSheet ???????????????
	 * @param midNighttimeSheet 
	 * @param frameTime
	 * @param treatAsTimeSpentAtWork
	 * @param holidayWorkTimeSheetNo
	 */
	public HolidayWorkFrameTimeSheetForCalc(TimeSpanForDailyCalc timeSheet, TimeRoundingSetting rounding,
			List<TimeSheetOfDeductionItem> recorddeductionTimeSheets,
			List<TimeSheetOfDeductionItem> deductionTimeSheets, List<BonusPayTimeSheetForCalc> bonusPayTimeSheet,
			List<SpecBonusPayTimeSheetForCalc> specifiedBonusPayTimeSheet, MidNightTimeSheetForCalcList midNighttimeSheet,
			HolidayWorkFrameTime frameTime, boolean treatAsTimeSpentAtWork, EmTimezoneNo holidayWorkTimeSheetNo,
			Finally<StaturoryAtrOfHolidayWork> statutoryAtr) {
		super(timeSheet, rounding, recorddeductionTimeSheets, deductionTimeSheets, bonusPayTimeSheet, specifiedBonusPayTimeSheet,
				midNighttimeSheet);
		this.frameTime = frameTime;
		TreatAsTimeSpentAtWork = treatAsTimeSpentAtWork;
		HolidayWorkTimeSheetNo = holidayWorkTimeSheetNo;
		this.statutoryAtr = statutoryAtr;
	}

	/**
	 * ????????????(???????????????
	 * @param companyCommonSetting ?????????????????????
	 * @param personDailySetting ??????????????????
	 * @param integrationOfWorkTime ?????????????????????
	 * @param integrationOfDaily ????????????(Work)
	 * @param attendanceLeave ?????????
	 * @param todayWorkType ????????????
	 * @param deductionTimeSheet ???????????????
	 * @param oneDayOfRange 1????????????
	 * @return ???????????????????????????List
	 */
	public static List<HolidayWorkFrameTimeSheetForCalc> createHolidayTimeWorkFrame(
			ManagePerCompanySet companyCommonSetting,
			ManagePerPersonDailySet personDailySetting,
			IntegrationOfWorkTime integrationOfWorkTime,
			IntegrationOfDaily integrationOfDaily,
			TimeLeavingWork attendanceLeave,
			WorkType todayWorkType,
			DeductionTimeSheet deductionTimeSheet,
			TimeSpanForDailyCalc oneDayOfRange){

		// ??????????????????????????????
		List<HDWorkTimeSheetSetting> holidayWorkTimeList = integrationOfWorkTime.getHDWorkTimeSheetSettingList();
		
		List<HolidayWorkFrameTimeSheetForCalc> returnList = new ArrayList<>();
		for (HDWorkTimeSheetSetting holidayWorkTime: holidayWorkTimeList) {
			Optional<HolidayWorkFrameTimeSheetForCalc> holidayWorkFrameTimeSheet = createHolidayTimeWorkFrameTimeSheet(
					attendanceLeave,
					holidayWorkTime,
					todayWorkType,
					personDailySetting.getBonusPaySetting(),
					companyCommonSetting.getMidNightTimeSheet(),
					deductionTimeSheet,
					Optional.of(integrationOfWorkTime.getCommonSetting()),
					integrationOfDaily.getSpecDateAttr());
			if (holidayWorkFrameTimeSheet.isPresent()){
				returnList.add(holidayWorkFrameTimeSheet.get());
			}
		}
		// ???????????????????????????????????????
		returnList.addAll(HolidayWorkTimeSheet.getHolidayWorkTimeSheetFromTemporary(
				companyCommonSetting,
				personDailySetting,
				integrationOfWorkTime,
				integrationOfDaily,
				todayWorkType,
				oneDayOfRange));
		// ???????????????????????????
		return returnList;
	}
	
	/**
	 * ??????????????????????????????????????????????????????????????????
	 * @return?????????????????????
	 */
	public HolidayWorkFrameTimeSheet changeNotWorkFrameTimeSheet() {
		return new HolidayWorkFrameTimeSheet(this.getFrameTime().getHolidayFrameNo(),this.timeSheet.getTimeSpan());
	}

	/**
	 * ???????????????????????????
	 * @param attendanceLeave ?????????
	 * @param holidayWorkSet ??????????????????????????????
	 * @param today ????????????
	 * @param bonuspaySetting ????????????
	 * @param midNightTimeSheet ???????????????
	 * @param deductTimeSheet ???????????????
	 * @param commonSetting ??????????????????????????????
	 * @param specificDateAttrSheets ???????????????
	 * @return ??????????????????
	 */
	public static Optional<HolidayWorkFrameTimeSheetForCalc> createHolidayTimeWorkFrameTimeSheet(
			TimeLeavingWork attendanceLeave,
			HDWorkTimeSheetSetting holidayWorkSet,
			WorkType today,
			Optional<BonusPaySetting> bonuspaySetting,
			MidNightTimeSheet midNightTimeSheet,
			DeductionTimeSheet deductTimeSheet,
			Optional<WorkTimezoneCommonSet> commonSetting,
			Optional<SpecificDateAttrOfDailyAttd> specificDateAttrSheets) {

		// ?????????????????????
		Optional<TimeSpanForCalc> duplicateTimeSpan = holidayWorkSet.getTimezone().timeSpan().getDuplicatedWith(
				attendanceLeave.getTimespan());
		if (!duplicateTimeSpan.isPresent()) return Optional.empty();
		// ?????????????????????
		HolidayAtr holidayAtr = today.getWorkTypeSetList().get(0).getHolidayAtr();
		// ?????????No?????????
		BreakFrameNo breakFrameNo = holidayWorkSet.decisionBreakFrameNoByHolidayAtr(holidayAtr);
		// ????????????????????????
		HolidayWorkFrameTime holidayTimeFrame = new HolidayWorkFrameTime(
				new HolidayWorkFrameNo(breakFrameNo.v().intValue()),
				Finally.of(TimeDivergenceWithCalculation.sameTime(new AttendanceTime(0))),
				Finally.of(TimeDivergenceWithCalculation.sameTime(new AttendanceTime(0))),
				Finally.of(new AttendanceTime(0)));
		// ???????????????????????????
		HolidayWorkFrameTimeSheetForCalc holidayWorkFrameTimeSheet = new HolidayWorkFrameTimeSheetForCalc(
				new TimeSpanForDailyCalc(duplicateTimeSpan.get()),
				holidayWorkSet.getTimezone().getRounding(),
				new ArrayList<>(),
				new ArrayList<>(),
				new ArrayList<>(),
				new ArrayList<>(),
				MidNightTimeSheetForCalcList.createEmpty(),
				holidayTimeFrame,
				false,
				new EmTimezoneNo(holidayWorkSet.getWorkTimeNo()),
				Finally.of(StaturoryAtrOfHolidayWork.deicisionAtrByHolidayAtr(holidayAtr)));
		// ????????????????????????
		holidayWorkFrameTimeSheet.registDeductionList(ActualWorkTimeSheetAtr.HolidayWork, deductTimeSheet, commonSetting);
		// ????????????????????????
		holidayWorkFrameTimeSheet.createBonusPayTimeSheet(bonuspaySetting, specificDateAttrSheets, deductTimeSheet);;
		// ????????????????????????
		holidayWorkFrameTimeSheet.createMidNightTimeSheet(midNightTimeSheet, commonSetting, deductTimeSheet);
		
		return Optional.of(holidayWorkFrameTimeSheet);
	}
	
	/**
	 * ???????????????????????????????????????????????????
	 * @param autoCalcSet ??????????????????????????????
	 * @return ?????????????????????????????????
	 */
	public HolidayWorkFrameTime calcOverTimeWorkTime(AutoCalRestTimeSetting autoCalcSet) {
		AttendanceTime holidayWorkTime;
		if(autoCalcSet.getLateNightTime().getCalAtr().isCalculateEmbossing()) {
			holidayWorkTime = new AttendanceTime(0);
		}
		else {
			holidayWorkTime = this.calcTotalTime();
		}
		return  new HolidayWorkFrameTime(this.frameTime.getHolidayFrameNo()
				,this.frameTime.getTransferTime()
				,Finally.of(TimeDivergenceWithCalculation.sameTime(holidayWorkTime))
				,this.frameTime.getBeforeApplicationTime());
	}
	
	/**
	 * ????????????
	 * ?????????????????????
	 * @param autoCalcSet ??????????????????
	 * @param goOutSet ??????????????????????????????
	 * @return ????????????
	 */
	public TimeDivergenceWithCalculation correctCalculationTime(AutoCalSetting autoCalcSet, Optional<WorkTimezoneGoOutSet> goOutSet) {
		AttendanceTime time = autoCalcSet.getCalAtr().isCalculateEmbossing() ? this.calcTime(ActualWorkTimeSheetAtr.HolidayWork, goOutSet) : AttendanceTime.ZERO;
		AttendanceTime calcTime = this.calcTime(ActualWorkTimeSheetAtr.HolidayWork, goOutSet);
		return TimeDivergenceWithCalculation.createTimeWithCalculation(time, calcTime);
	}
	
//	/**
//	 *???????????????????????????????????????????????????
//	 * @param forcsList
//	 * @param atr
//	 * @return
//	 */
//	public AttendanceTime forcs(List<TimeSheetOfDeductionItem> forcsList,ConditionAtr atr,DeductionAtr dedAtr){
//		AttendanceTime dedTotalTime = new AttendanceTime(0);
//		val loopList = this.getDedTimeSheetByAtr(dedAtr, atr);
//		for(TimeSheetOfDeductionItem deduTimeSheet: loopList) {
//			if(deduTimeSheet.checkIncludeCalculation(atr)) {
//				dedTotalTime = dedTotalTime.addMinutes(deduTimeSheet.calcTotalTime().valueAsMinutes());
//			}
//		}
//		return dedTotalTime;
//	}
	
	//?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
	
	/**
	 * ???????????????????????????????????????
	 * @param todayWorkType ?????????????????????
	 * @param flowWorkSetting ??????????????????
	 * @param deductTimeSheet ???????????????
	 * @param itemsWithinCalc ?????????????????????
	 * @param holidayStartEnd ????????????????????????
	 * @param bonusPaySetting ????????????
	 * @param specDateAttr ??????????????????????????????
	 * @param midNightTimeSheet ???????????????
	 * @param processingTimezone ?????????????????????
	 * @return ???????????????????????????
	 */
	public static HolidayWorkFrameTimeSheetForCalc createAsFlow(
			ManagePerPersonDailySet personDailySetting,
			WorkType todayWorkType,
			FlowWorkSetting flowWorkSetting,
			DeductionTimeSheet deductTimeSheet,
			List<TimeSheetOfDeductionItem> itemsWithinCalc,
			TimeSpanForDailyCalc holidayStartEnd,
			Optional<SpecificDateAttrOfDailyAttd> specDateAttr,
			MidNightTimeSheet midNightTimeSheet,
			FlowWorkHolidayTimeZone processingTimezone) {
		
		// ?????????????????????????????????????????????????????????????????????
		TimeWithDayAttr endTime = HolidayWorkFrameTimeSheetForCalc.calcEndTimeForFlow(
				processingTimezone,
				flowWorkSetting,
				itemsWithinCalc,
				holidayStartEnd,
				personDailySetting.getAddSetting().getAddSetOfWorkingTime());
		// ?????????????????????
		HolidayAtr holidayAtr = todayWorkType.getWorkTypeSetList().get(0).getHolidayAtr();
		// ?????????No????????????
		BreakFrameNo breakFrameNo = processingTimezone.getBreakFrameNoToHolidayAtr(holidayAtr);
		// ????????????????????????
		HolidayWorkFrameTime frameTime = new HolidayWorkFrameTime(
				new HolidayWorkFrameNo(breakFrameNo.v().intValue()),
				Finally.of(TimeDivergenceWithCalculation.sameTime(new AttendanceTime(0))),
				Finally.of(TimeDivergenceWithCalculation.sameTime(new AttendanceTime(0))),
				Finally.of(new AttendanceTime(0)));
		// ??????????????????
		HolidayWorkFrameTimeSheetForCalc holidayWorkFrameTimeSheet = new HolidayWorkFrameTimeSheetForCalc(
				new TimeSpanForDailyCalc(holidayStartEnd.getStart(), endTime),
				processingTimezone.getFlowTimeSetting().getRounding(),
				new ArrayList<>(),
				new ArrayList<>(),
				new ArrayList<>(),
				new ArrayList<>(),
				MidNightTimeSheetForCalcList.createEmpty(),
				frameTime,
				false,
				new EmTimezoneNo(processingTimezone.getWorktimeNo()),
				Finally.of(StaturoryAtrOfHolidayWork.deicisionAtrByHolidayAtr(todayWorkType.getHolidayAtr().get())));
		// ????????????????????????
		holidayWorkFrameTimeSheet.registDeductionList(ActualWorkTimeSheetAtr.HolidayWork, deductTimeSheet,
				Optional.of(flowWorkSetting.getCommonSetting()));
		// ????????????????????????
		holidayWorkFrameTimeSheet.createBonusPayTimeSheet(personDailySetting.getBonusPaySetting(), specDateAttr, deductTimeSheet);
		// ????????????????????????
		holidayWorkFrameTimeSheet.createMidNightTimeSheet(
				midNightTimeSheet, Optional.of(flowWorkSetting.getCommonSetting()), deductTimeSheet);
		
		return holidayWorkFrameTimeSheet;
	}
	
	/**
	 * ?????????????????????????????????
	 * @param processingHolidayTimeZone ?????????????????????????????????
	 * @param holidayTimezones ?????????????????????(List)
	 * @param timeSheetOfDeductionItems ????????????????????????(List)
	 * @param holidayStartEnd ????????????????????????
	 * @return ????????????
	 */
	private static TimeWithDayAttr calcEndTimeForFlow(
			FlowWorkHolidayTimeZone processingHolidayTimeZone,
			FlowWorkSetting flowWorkSetting,
			List<TimeSheetOfDeductionItem> timeSheetOfDeductionItems,
			TimeSpanForDailyCalc holidayStartEnd,
			AddSettingOfWorkingTime holidaySet) {
		
		Optional<FlowWorkHolidayTimeZone> plusOneHolidayTimezone = flowWorkSetting.getOffdayWorkTimezone().getLstWorkTimezone().stream()
				.filter(timezone -> timezone.getWorktimeNo().equals(processingHolidayTimeZone.getWorktimeNo()+1))
				.findFirst();
		
		TimeWithDayAttr endTime = TimeWithDayAttr.THE_PRESENT_DAY_0000;
		
		if(plusOneHolidayTimezone.isPresent()) {
			//?????????????????????????????????
			AttendanceTime holidayFrameTime = plusOneHolidayTimezone.get().getFlowTimeSetting().getElapsedTime().minusMinutes(
					processingHolidayTimeZone.getFlowTimeSetting().getElapsedTime().valueAsMinutes());
			
			//????????????????????????????????????????????????
			endTime = holidayStartEnd.getStart().forwardByMinutes(holidayFrameTime.valueAsMinutes());
			
			TimeSpanForDailyCalc timeSpan = new TimeSpanForDailyCalc(holidayStartEnd.getStart(), endTime);
			
			//??????????????????????????????????????????
			StaggerDiductionTimeSheet forward = new StaggerDiductionTimeSheet(timeSpan, processingHolidayTimeZone.getFlowTimeSetting().getRounding(), timeSheetOfDeductionItems);
			endTime = forward.getForwardEnd(ActualWorkTimeSheetAtr.HolidayWork, flowWorkSetting.getCommonSetting(), holidaySet);
			
			//????????????????????????????????????
			TimeSpanForDailyCalc afterShift = new TimeSpanForDailyCalc(timeSpan.getStart(), endTime);
			
			//??????input.?????????????????????????????????input.???????????????????????????
			if(afterShift.contains(holidayStartEnd.getEnd())) endTime = holidayStartEnd.getEnd();
		}
		else {
			endTime = holidayStartEnd.getEnd();
		}
		return endTime;
	}
	
	/**
	 * ????????????????????????????????????
	 * @param timeSpan ?????????
	 * @param commonSet ??????????????????????????????
	 * @return ??????????????????
	 */
	public Optional<HolidayWorkFrameTimeSheetForCalc> recreateWithDuplicate(TimeSpanForDailyCalc timeSpan, Optional<WorkTimezoneCommonSet> commonSet) {
		Optional<TimeSpanForDailyCalc> duplicate = this.timeSheet.getDuplicatedWith(timeSpan);
		if(!duplicate.isPresent()) {
			return Optional.empty();
		}
		HolidayWorkFrameTimeSheetForCalc recreated = new HolidayWorkFrameTimeSheetForCalc(
				duplicate.get(),
				this.rounding.clone(),
				this.recordedTimeSheet.stream().map(r -> r.getAfterDeleteOffsetTime()).collect(Collectors.toList()),
				this.deductionTimeSheet.stream().map(d -> d.getAfterDeleteOffsetTime()).collect(Collectors.toList()),
				this.getDuplicatedBonusPayNotStatic(this.bonusPayTimeSheet, duplicate.get()),
				this.getDuplicatedSpecBonusPayzNotStatic(this.specBonusPayTimesheet, duplicate.get()),
				this.midNightTimeSheet.getDuplicateRangeTimeSheet(duplicate.get()),
				this.frameTime.clone(),
				this.TreatAsTimeSpentAtWork,
				new EmTimezoneNo(this.HolidayWorkTimeSheetNo.v().intValue()),
				this.statutoryAtr.isPresent() ? Finally.of(StaturoryAtrOfHolidayWork.valueOf(this.statutoryAtr.get().toString())) : Finally.empty());
		
		//????????????????????????
		recreated.registDeductionList(ActualWorkTimeSheetAtr.HolidayWork, this.getCloneDeductionTimeSheet(), commonSet);
		
		return Optional.of(recreated);
	}
	
	/**
	 * ??????????????????????????????
	 * @return ??????????????????
	 */
	public HolidayWorkFrameTimeSheetForCalc getReverseRounding() {
		return new HolidayWorkFrameTimeSheetForCalc(
				this.timeSheet.clone(),
				this.rounding.getReverseRounding(),
				this.recordedTimeSheet.stream().map(r -> r.clone()).collect(Collectors.toList()),
				this.deductionTimeSheet.stream().map(d -> d.clone()).collect(Collectors.toList()),
				this.bonusPayTimeSheet.stream().map(b -> b.clone()).collect(Collectors.toList()),
				this.specBonusPayTimesheet.stream().map(s -> s.clone()).collect(Collectors.toList()),
				this.midNightTimeSheet.clone(),
				this.frameTime.clone(),
				this.TreatAsTimeSpentAtWork,
				new EmTimezoneNo(this.HolidayWorkTimeSheetNo.v().intValue()),
				this.statutoryAtr.isPresent() ? Finally.of(StaturoryAtrOfHolidayWork.valueOf(this.statutoryAtr.get().toString())) : Finally.empty());
	}
}
