package nts.uk.ctx.at.shared.dom.worktime.flexset;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.val;
import mockit.Injectable;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.common.timerounding.Rounding;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.common.timerounding.Unit;
import nts.uk.ctx.at.shared.dom.common.usecls.ApplyAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.primitives.BonusPaySettingCode;
import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.BreakFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.CommonRestSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.CompensatoryOccurrenceDivision;
import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTime;
import nts.uk.ctx.at.shared.dom.worktime.common.DesignatedTime;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeZoneSet;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimezoneNo;
import nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkTimezoneSet;
import nts.uk.ctx.at.shared.dom.worktime.common.GoOutTimeRoundingMethod;
import nts.uk.ctx.at.shared.dom.worktime.common.GoOutTimezoneRoundingSet;
import nts.uk.ctx.at.shared.dom.worktime.common.HDWorkTimeSheetSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.HDWorkTimeSheetSettingGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.common.HolidayCalculation;
import nts.uk.ctx.at.shared.dom.worktime.common.IntervalTime;
import nts.uk.ctx.at.shared.dom.worktime.common.IntervalTimeGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.common.IntervalTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.IntervalTimeSettingGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.common.OTFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.OneDayTime;
import nts.uk.ctx.at.shared.dom.worktime.common.OverTimeOfTimeZoneSet;
import nts.uk.ctx.at.shared.dom.worktime.common.OverTimeOfTimeZoneSetGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.common.PrioritySetting;
import nts.uk.ctx.at.shared.dom.worktime.common.RestClockManageAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.RestTimeOfficeWorkCalcMethod;
import nts.uk.ctx.at.shared.dom.worktime.common.RoundingTime;
import nts.uk.ctx.at.shared.dom.worktime.common.SettlementOrder;
import nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezone;
import nts.uk.ctx.at.shared.dom.worktime.common.SubHolTransferSet;
import nts.uk.ctx.at.shared.dom.worktime.common.SubHolTransferSetAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.SubHolTransferSetGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZoneRounding;
import nts.uk.ctx.at.shared.dom.worktime.common.TimezoneOfFixedRestTimeSet;
import nts.uk.ctx.at.shared.dom.worktime.common.TotalRoundingSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkSystemAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSetGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneExtraordTimeSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneGoOutSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneGoOutSetGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneLateEarlySet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneLateNightTimeSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneMedicalSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneMedicalSetGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneOtherSubHolTimeSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneOtherSubHolTimeSetGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneShortTimeWorkSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneStampSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneStampSetGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowFixedRestCalcMethod;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowFixedRestSet;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowRestCalcMethod;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowRestClockCalcMethod;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowRestSet;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowRestSetGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowRestSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowRestTimezone;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkRestSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkRestSettingDetail;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkRestSettingDetailGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkRestSettingGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkRestTimezone;
import nts.uk.ctx.at.shared.dom.worktime.predset.BreakDownTimeDay;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetermineTime;
import nts.uk.ctx.at.shared.dom.worktime.predset.PrescribedTimezoneSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneUse;
import nts.uk.ctx.at.shared.dom.worktime.predset.UseSetting;
import nts.uk.shr.com.time.TimeWithDayAttr;

public class FlexWorkSettingHelper {
	 
	public static FlexWorkSetting createFlexOffRestTimeZone(FlowWorkRestTimezone offRestTimeZone) {
		val offdayWorkTimeImpl = new FlexOffdayWorkTimeGetMementoImpl(Arrays.asList(new HDWorkTimeSheetSetting()), offRestTimeZone);
		return new FlexWorkSetting(new FlexWorkSettingImpl(ApplyAtr.USE, Collections.emptyList()
				, new FlexOffdayWorkTime(offdayWorkTimeImpl), true));
	}
	
	public static FlexWorkSetting createFlexHaftRestTimeZone(FlowWorkRestTimezone flexHalfRestTimezone) {
		
		val flexHalfDayWorkTimes = Arrays.asList(
				  FlexHalfDayWorkTimeHelper.createWorkTime(AmPmAtr.ONE_DAY, flexHalfRestTimezone, Collections.emptyList()
						  , Arrays.asList(
								  EmTimeZoneSetHelper.createWorkingTimezoneList(new EmTimeFrameNo(1) , TimeWithDayAttr.hourMinute( 5,  0 ), TimeWithDayAttr.hourMinute( 29, 0 ))
							))
				, FlexHalfDayWorkTimeHelper.createWorkTime(AmPmAtr.AM, flexHalfRestTimezone, Collections.emptyList()
						, Arrays.asList(
								 EmTimeZoneSetHelper.createWorkingTimezoneList(new EmTimeFrameNo(2) , TimeWithDayAttr.hourMinute( 5,  0 ), TimeWithDayAttr.hourMinute( 12, 0 ))
						))
				, FlexHalfDayWorkTimeHelper.createWorkTime(AmPmAtr.PM, flexHalfRestTimezone, Collections.emptyList()
						, Arrays.asList(
								 EmTimeZoneSetHelper.createWorkingTimezoneList(new EmTimeFrameNo(3) , TimeWithDayAttr.hourMinute( 13,  0 ), TimeWithDayAttr.hourMinute( 29, 0 ))
						))
				);
		
		val fixedRestTimeSet = new TimezoneOfFixedRestTimeSet(Arrays.asList(
				  new DeductionTime(TimeWithDayAttr.hourMinute(12, 00), TimeWithDayAttr.hourMinute(13, 00))
				, new DeductionTime(TimeWithDayAttr.hourMinute(17, 30), TimeWithDayAttr.hourMinute(18, 00))));
		
		val offdayWorkTimeImpl = new FlexOffdayWorkTimeGetMementoImpl(Arrays.asList(new HDWorkTimeSheetSetting())
				, new FlowWorkRestTimezone(true, fixedRestTimeSet, new FlowRestTimezone()) //休憩時間帯を固定にする = TRUE
				);
		
		return  new FlexWorkSetting(new FlexWorkSettingImpl(ApplyAtr.USE, flexHalfDayWorkTimes
				, new FlexOffdayWorkTime(offdayWorkTimeImpl), true));
	}
	
	
	public static FlexWorkSetting createNotUse(List<OverTimeOfTimeZoneSetImpl> overTimes, AmPmAtr amPM) {
		val flexHalfRestTimezone = new FlowWorkRestTimezone();
		val flexHalfDayWorkTimes = Arrays.asList(
				  FlexHalfDayWorkTimeHelper.createWorkTime(amPM, flexHalfRestTimezone
						  , OverTimeOfTimeZoneSetHelper.createOverTimeList(overTimes)
						  , Arrays.asList(
								  EmTimeZoneSetHelper.createWorkingTimezoneList(new EmTimeFrameNo(1) , TimeWithDayAttr.hourMinute( 5,  0 ), TimeWithDayAttr.hourMinute( 29, 0 ))
							))
				, FlexHalfDayWorkTimeHelper.createWorkTime(AmPmAtr.AM, flexHalfRestTimezone
						,  OverTimeOfTimeZoneSetHelper.createOverTimeList(overTimes)
						, Arrays.asList(
								 EmTimeZoneSetHelper.createWorkingTimezoneList(new EmTimeFrameNo(2) , TimeWithDayAttr.hourMinute( 5,  0 ), TimeWithDayAttr.hourMinute( 12, 0 ))
						))
				);
		return new FlexWorkSetting(new FlexWorkSettingImpl(ApplyAtr.NOT_USE, flexHalfDayWorkTimes, new FlexOffdayWorkTime(), true));
	}
	
	
	public static FlexWorkSetting createUse_One_Day(List<OverTimeOfTimeZoneSetImpl> overTimes) {
		val flexHalfRestTimezone = new FlowWorkRestTimezone();
		//就業時間帯
		val flexHalfDayWorkTimes = Arrays.asList(
				  FlexHalfDayWorkTimeHelper.createWorkTime(AmPmAtr.ONE_DAY, flexHalfRestTimezone
						  , OverTimeOfTimeZoneSetHelper.createOverTimeList(overTimes)
						  , Arrays.asList(
								  EmTimeZoneSetHelper.createWorkingTimezoneList(new EmTimeFrameNo(1) , TimeWithDayAttr.hourMinute( 5,  0 ), TimeWithDayAttr.hourMinute( 29, 0 ))
							))
				, FlexHalfDayWorkTimeHelper.createWorkTime(AmPmAtr.AM, flexHalfRestTimezone
						, OverTimeOfTimeZoneSetHelper.createOverTimeList(overTimes)
						, Arrays.asList(
								 EmTimeZoneSetHelper.createWorkingTimezoneList(new EmTimeFrameNo(2) , TimeWithDayAttr.hourMinute( 5,  0 ), TimeWithDayAttr.hourMinute( 12, 0 ))
						))
				, FlexHalfDayWorkTimeHelper.createWorkTime(AmPmAtr.PM, flexHalfRestTimezone
						, OverTimeOfTimeZoneSetHelper.createOverTimeList(overTimes)
						, Arrays.asList(
								 EmTimeZoneSetHelper.createWorkingTimezoneList(new EmTimeFrameNo(3) , TimeWithDayAttr.hourMinute( 13,  0 ), TimeWithDayAttr.hourMinute( 29, 0 ))
						))
				);
		return new FlexWorkSetting(new FlexWorkSettingImpl(ApplyAtr.USE, flexHalfDayWorkTimes, new FlexOffdayWorkTime(), true));
	}
	
	public static FlexWorkSetting createUse_AM(List<OverTimeOfTimeZoneSetImpl> overTimes) {
		val flexHalfRestTimezone = new FlowWorkRestTimezone();
		//就業時間帯
		val flexHalfDayWorkTimes = Arrays.asList(
				  FlexHalfDayWorkTimeHelper.createWorkTime(AmPmAtr.ONE_DAY, flexHalfRestTimezone
						  , OverTimeOfTimeZoneSetHelper.createOverTimeList(overTimes)
						  , Arrays.asList(
								  EmTimeZoneSetHelper.createWorkingTimezoneList(new EmTimeFrameNo(1) , TimeWithDayAttr.hourMinute( 5,  0 ), TimeWithDayAttr.hourMinute( 29, 0 ))
							))
				, FlexHalfDayWorkTimeHelper.createWorkTime(AmPmAtr.AM, flexHalfRestTimezone
						, OverTimeOfTimeZoneSetHelper.createOverTimeList(overTimes)
						, Arrays.asList(
								 EmTimeZoneSetHelper.createWorkingTimezoneList(new EmTimeFrameNo(2) , TimeWithDayAttr.hourMinute( 5,  0 ), TimeWithDayAttr.hourMinute( 12, 0 ))
						))
				, FlexHalfDayWorkTimeHelper.createWorkTime(AmPmAtr.PM, flexHalfRestTimezone
						, OverTimeOfTimeZoneSetHelper.createOverTimeList(overTimes)
						, Arrays.asList(
								 EmTimeZoneSetHelper.createWorkingTimezoneList(new EmTimeFrameNo(3) , TimeWithDayAttr.hourMinute( 13,  0 ), TimeWithDayAttr.hourMinute( 29, 0 ))
						))
				);
		return new FlexWorkSetting(new FlexWorkSettingImpl(ApplyAtr.USE, flexHalfDayWorkTimes, new FlexOffdayWorkTime(), true));
	}
	
	public static FlexWorkSetting createUse_PM(List<OverTimeOfTimeZoneSetImpl> overTimes) {
		val flexHalfRestTimezone = new FlowWorkRestTimezone();
		//就業時間帯
		val flexHalfDayWorkTimes = Arrays.asList(
				  FlexHalfDayWorkTimeHelper.createWorkTime(AmPmAtr.ONE_DAY, flexHalfRestTimezone
						  , OverTimeOfTimeZoneSetHelper.createOverTimeList(overTimes)
						  , Arrays.asList(
								  EmTimeZoneSetHelper.createWorkingTimezoneList(new EmTimeFrameNo(1) , TimeWithDayAttr.hourMinute( 5,  0 ), TimeWithDayAttr.hourMinute( 29, 0 ))
							))
				, FlexHalfDayWorkTimeHelper.createWorkTime(AmPmAtr.AM, flexHalfRestTimezone
						, OverTimeOfTimeZoneSetHelper.createOverTimeList(overTimes)
						, Arrays.asList(
								 EmTimeZoneSetHelper.createWorkingTimezoneList(new EmTimeFrameNo(2) , TimeWithDayAttr.hourMinute( 5,  0 ), TimeWithDayAttr.hourMinute( 12, 0 ))
						))
				, FlexHalfDayWorkTimeHelper.createWorkTime(AmPmAtr.PM, flexHalfRestTimezone
						, OverTimeOfTimeZoneSetHelper.createOverTimeList(overTimes)
						, Arrays.asList(
								 EmTimeZoneSetHelper.createWorkingTimezoneList(new EmTimeFrameNo(3) , TimeWithDayAttr.hourMinute( 13,  0 ), TimeWithDayAttr.hourMinute( 29, 0 ))
						))
				);
		return new FlexWorkSetting(new FlexWorkSettingImpl(ApplyAtr.USE, flexHalfDayWorkTimes, new FlexOffdayWorkTime(), true));
	}
	
	public static HDWorkTimeSheetSetting createHdWorkTimeSheet(TimeWithDayAttr start, TimeWithDayAttr end){
		
		return new HDWorkTimeSheetSetting(new HDWorkTimeSheetSettingImpl(start, end));
	}
	
	
	
	public static FlowWorkRestTimezone createOffRestTimeZone(boolean isFixed, TimezoneOfFixedRestTimeSet fixedRestTimeSet) {
		
		return new FlowWorkRestTimezone(isFixed, fixedRestTimeSet, new FlowRestTimezone());
		
	}
	
	public static FlexWorkSetting createFlexWorkSetting(List<HDWorkTimeSheetSetting> lstWorkTimezone) {
		
		val offRestTimeZone = new FlowWorkRestTimezone(true, new TimezoneOfFixedRestTimeSet(), new FlowRestTimezone());
		
		val offdayWorkTimeImpl = new FlexOffdayWorkTimeGetMementoImpl(lstWorkTimezone, offRestTimeZone);
		
		val flexWorkSetting = new FlexWorkSetting(new FlexWorkSettingImpl(ApplyAtr.USE, Collections.emptyList(), new FlexOffdayWorkTime(offdayWorkTimeImpl), false));

		return flexWorkSetting;
	}
	
	public static class Time {
		/**
		 * 勤怠時刻を作成する
		 * @param hour 時
		 * @param minute 分
		 * @return 勤怠時刻
		 */
		public static AttendanceTime toAtdTime(int hour, int minute) {
			return new AttendanceTime( hour * 60 + minute );
		}
	}

	public static class Timezone {

		public static TimezoneUse from(int workNo, boolean isUse, TimeSpanForCalc timeSpan) {
			return new TimezoneUse(
								timeSpan.getStart(), timeSpan.getEnd()
							,	isUse ? UseSetting.USE : UseSetting.NOT_USE
							,	workNo
						);
		}

		public static TimezoneUse createUsed(int workNo, TimeSpanForCalc timeSpan) {
			return Timezone.from(workNo, true, timeSpan);
		}

		public static TimezoneUse createUnused(int workNo) {
			return Timezone.from(workNo, false, new TimeSpanForCalc(TimeWithDayAttr.hourMinute(0, 0), TimeWithDayAttr.hourMinute(0, 0)));
		}
		
	}
	
	public static class PredTimeStg {
		
		@Injectable
		static BreakDownTimeDay predTime;
		
		@Injectable
		static List<TimezoneUse> lstTimezone;
		
		@Injectable
		static TimeWithDayAttr morningEndTime;

		@Injectable
		static TimeWithDayAttr afternoonStartTime;
		
		/**
		 * 所定時間(DUMMY)を作成する
		 * @return 所定時間(DUMMY)
		 */
		public static PredetermineTime createDummyPredTime() {
			return new PredetermineTime( predTime, predTime);
		}

		/**
		 * 所定時間帯設定(DUMMY)を作成する
		 * @return 所定時間帯設定(DUMMY)
		 */
		public static PrescribedTimezoneSetting createPrscTzStg(List<TimezoneUse> lstTimezone) {
			return new PrescribedTimezoneSetting( morningEndTime, afternoonStartTime, lstTimezone);
		}
		
		/**
		 * 所定時間帯設定(DUMMY)を作成する
		 * @return 所定時間帯設定(DUMMY)
		 */
		public static PrescribedTimezoneSetting createPrscTzStg(TimeWithDayAttr morningEndTime, TimeWithDayAttr afternoonStartTime) {
			return new PrescribedTimezoneSetting( morningEndTime, afternoonStartTime, lstTimezone);
		}
		
		/**
		 * 所定時間帯設定(DUMMY)を作成する
		 * @return 所定時間帯設定(DUMMY)
		 */
		public static PrescribedTimezoneSetting createPrscTzStgByMoringEnd(List<TimezoneUse> lstTimezone) {
			return new PrescribedTimezoneSetting( morningEndTime,	afternoonStartTime,	lstTimezone);
		}

		/**
		 * 所定時間設定を作成する
		 * @param code 就業時間帯コード
		 * @param startClock 1日の開始時刻
		 * @param rangeOfDay 1日の範囲(時間)
		 * @param prscTzStg 所定時間帯設定
		 * @return 所定時間設定
		 */
		public static PredetemineTimeSetting create(String code, TimeWithDayAttr startClock, AttendanceTime rangeOfDay
				, PrescribedTimezoneSetting prscTzStg) {
			return new PredetemineTimeSetting("CID", rangeOfDay, new WorkTimeCode(code)
						, PredTimeStg.createDummyPredTime(), false, prscTzStg, startClock, false
					);
		}
		
		public static PredetemineTimeSetting create(List<TimezoneUse> lstTimezone) {
			return new PredetemineTimeSetting("CID", new AttendanceTime(2300), new WorkTimeCode("01")
					, PredTimeStg.createDummyPredTime(), false
					, createPrscTzStg(lstTimezone),  TimeWithDayAttr.hourMinute( 5, 0 ), false
				);
		}
		
		public static PredetemineTimeSetting create(TimeWithDayAttr morningEndTime, TimeWithDayAttr afternoonStartTime) {
			return new PredetemineTimeSetting("CID", new AttendanceTime(2300), new WorkTimeCode("01")
					, PredTimeStg.createDummyPredTime(), false
					, createPrscTzStg(morningEndTime, afternoonStartTime),  TimeWithDayAttr.hourMinute( 5, 0 ), false
				);
		}
    }
	
	
	public static class EmTimeZoneSetHelper{
		
		@Injectable
		static TimeRoundingSetting timeRouding;
		
		public static EmTimeZoneSet  createWorkingTimezoneList(EmTimeFrameNo emTimeFrNo, TimeWithDayAttr start, TimeWithDayAttr end) {
			return new EmTimeZoneSet(emTimeFrNo , new TimeZoneRounding(start, end, timeRouding));
		}
	}
	
	public static class FlowWkRestTimezoneHelper{
		
		@Injectable
		static TimezoneOfFixedRestTimeSet fixedRestTimeZone;
		
		public static FlowWorkRestTimezone createRestTimeZone(FlowRestSetting flowRestSetting, FlowRestSetting hereAfterRestSet, boolean fixRestTime) {
			val flowRestTimezone = new FlowRestTimezone(Arrays.asList(flowRestSetting), false,  hereAfterRestSet);
			return new FlowWorkRestTimezone(false, fixedRestTimeZone , flowRestTimezone);
		}
		
	}
	
	public static class FlexHalfDayWorkTimeHelper{
		
		public static FlexHalfDayWorkTime createWorkTime(AmPmAtr amPmAtr, FlowWorkRestTimezone restTimezone
				, List<OverTimeOfTimeZoneSet> lstOTTimezone, List<EmTimeZoneSet> lstWorkingTimezone) {
			//勤務時間帯
			val workTimezone = new FixedWorkTimezoneSet();
			workTimezone.setLstOTTimezone(lstOTTimezone);
			workTimezone.setLstWorkingTimezone(lstWorkingTimezone);
			
			return new FlexHalfDayWorkTime(new FlexHalfDayWorkTimeImpl(restTimezone, workTimezone, amPmAtr));
		}
		
	}
	
	public static class OverTimeOfTimeZoneSetHelper{
		public static OverTimeOfTimeZoneSetImpl createOverTime(int emTimeNo, boolean earlyOTUse, TimeWithDayAttr start, TimeWithDayAttr end) {
			return new OverTimeOfTimeZoneSetImpl(new EmTimezoneNo(emTimeNo), earlyOTUse, new TimeZoneRounding(start, end, null), new OTFrameNo(emTimeNo), new OTFrameNo(emTimeNo), new SettlementOrder(emTimeNo));
		}
		
		public static List<OverTimeOfTimeZoneSet> createOverTimeList(List<OverTimeOfTimeZoneSetImpl> overTimeList) {
			
			return overTimeList.stream().map(c -> {return new OverTimeOfTimeZoneSet(c);}).collect(Collectors.toList());
		}
		
	}
	
	@AllArgsConstructor
	public static class FlowRestSetImpl implements FlowRestSetGetMemento{

		@Override
		public boolean getUseStamp() {
			return false;
		}

		@Override
		public FlowRestClockCalcMethod getUseStampCalcMethod() {
			return FlowRestClockCalcMethod.USE_GOOUT_TIME_TO_CALCULATE;
		}

		@Override
		public RestClockManageAtr getTimeManagerSetAtr() {
			return RestClockManageAtr.IS_CLOCK_MANAGE;
		}

		@Override
		public FlowRestCalcMethod getCalculateMethod() {
			return FlowRestCalcMethod.REFER_MASTER;
		}
		
		
	}
	
	@AllArgsConstructor
	public static class FlowWorkRestSettingDetailImpl implements FlowWorkRestSettingDetailGetMemento{
		@Override
		public FlowRestSet getFlowRestSetting() {
		    val flowRestMemento = new FlowRestSetImpl();
			return new FlowRestSet(flowRestMemento);
		}

		@Override
		public FlowFixedRestSet getFlowFixedRestSetting() {
			return new FlowFixedRestSet(true, true, true, FlowFixedRestCalcMethod.REFER_MASTER);
		}

		@Override
		public boolean getUsePluralWorkRestTime() {
			return false;
		}

		@Override
		public TimeRoundingSetting getRoundingBreakMultipleWork() {
			return new TimeRoundingSetting(Unit.ROUNDING_TIME_10MIN, Rounding.ROUNDING_DOWN);
		}
		
		
	}
	
	@AllArgsConstructor
	public static class FlowWorkRestSettingGetMementoImpl implements FlowWorkRestSettingGetMemento{
		@Override
		public CommonRestSetting getCommonRestSetting() {
			return new CommonRestSetting(RestTimeOfficeWorkCalcMethod.APPROP_ALL);
		}

		@Override
		public FlowWorkRestSettingDetail getFlowRestSetting() {
			val restSettingDetailMemento = new FlowWorkRestSettingDetailImpl();
			return new FlowWorkRestSettingDetail(restSettingDetailMemento);
		}
	
	}
	
	@AllArgsConstructor
	public static class HDWorkTimeSheetSettingImpl implements HDWorkTimeSheetSettingGetMemento{

		private TimeWithDayAttr start;
		
		private TimeWithDayAttr end;
		
		@Override
		public Integer getWorkTimeNo() {
			return 1;
		}

		@Override
		public TimeZoneRounding getTimezone() {
			val timeRoundingSetting =  new TimeRoundingSetting(Unit.ROUNDING_TIME_10MIN, Rounding.ROUNDING_DOWN);
			return new TimeZoneRounding(this.start, this.end, timeRoundingSetting);
		}

		@Override
		public boolean getIsLegalHolidayConstraintTime() {
			return false;
		}

		@Override
		public BreakFrameNo getInLegalBreakFrameNo() {
			return new BreakFrameNo(new BigDecimal(1));
		}

		@Override
		public boolean getIsNonStatutoryDayoffConstraintTime() {
			return false;
		}

		@Override
		public BreakFrameNo getOutLegalBreakFrameNo() {
			return new BreakFrameNo(new BigDecimal(2));
		}

		@Override
		public boolean getIsNonStatutoryHolidayConstraintTime() {
			return false;
		}

		@Override
		public BreakFrameNo getOutLegalPubHDFrameNo() {
			return new BreakFrameNo(new BigDecimal(3));
		}
		
	}
	
	@AllArgsConstructor
	public static class FlexOffdayWorkTimeGetMementoImpl implements FlexOffdayWorkTimeGetMemento{
		
		private List<HDWorkTimeSheetSetting> lstWorkTimezone;
		
		private FlowWorkRestTimezone restTimezone;
		
		@Override
		public List<HDWorkTimeSheetSetting> getLstWorkTimezone() {
			return this.lstWorkTimezone;
		}

		@Override
		public FlowWorkRestTimezone getRestTimezone() {
			return this.restTimezone;
		}
		
	}
	
	public static class IntervalTimeImpl implements IntervalTimeGetMemento{

		@Override
		public AttendanceTime getIntervalTime() {
			return new AttendanceTime(100);
		}

		@Override
		public TimeRoundingSetting getRounding() {
			return new TimeRoundingSetting(Unit.ROUNDING_TIME_15MIN, Rounding.ROUNDING_DOWN);
		}
		
	}
	
	
	@AllArgsConstructor
	public static class IntervalTimeSettingOImpl implements IntervalTimeSettingGetMemento{

		@Override
		public boolean getuseIntervalExemptionTime() {
			return false;
		}

		@Override
		public TimeRoundingSetting getIntervalExemptionTimeRound() {
			
			return new TimeRoundingSetting(Unit.ROUNDING_TIME_15MIN, Rounding.ROUNDING_DOWN);
		}

		@Override
		public IntervalTime getIntervalTime() {
			val intervalTimeMemento = new IntervalTimeImpl();
			return new IntervalTime(intervalTimeMemento);
		}

		@Override
		public boolean getuseIntervalTime() {
			return false;
		}
	}
	
	@AllArgsConstructor
	public static class SubHolTransferSetImpl implements SubHolTransferSetGetMemento{
		//1日：８ｈ
		@Override
		public OneDayTime getCertainTime() {
			return new OneDayTime(800);
		}

		@Override
		public boolean getUseDivision() {
			return false;
		}

		@Override
		public DesignatedTime getDesignatedTime() {
			return new DesignatedTime(new OneDayTime(800), new OneDayTime(400));
		}

		@Override
		public SubHolTransferSetAtr getSubHolTransferSetAtr() {
			return SubHolTransferSetAtr.CERTAIN_TIME_EXC_SUB_HOL;
		}
		
	}

	@AllArgsConstructor
	public static class WorkTimezoneOtherSubHolTimeSetImpl implements WorkTimezoneOtherSubHolTimeSetGetMemento{

		@Override
		public SubHolTransferSet getSubHolTimeSet() {
			val subHolTransferSetImpl = new SubHolTransferSetImpl();
			return new SubHolTransferSet(subHolTransferSetImpl);
		}

		@Override
		public WorkTimeCode getWorkTimeCode() {
			return new WorkTimeCode("001");
		}

		@Override
		public CompensatoryOccurrenceDivision getOriginAtr() {
			return CompensatoryOccurrenceDivision.WorkDayOffTime;
		}
		
	}
	
	@AllArgsConstructor
	public static class WorkTimezoneMedicalSetImpl implements WorkTimezoneMedicalSetGetMemento{

		@Override
		public TimeRoundingSetting getRoundingSet() {
			return new TimeRoundingSetting(Unit.ROUNDING_TIME_10MIN, Rounding.ROUNDING_DOWN);
		}

		@Override
		public WorkSystemAtr getWorkSystemAtr() {
			return WorkSystemAtr.DAY_SHIFT;
		}

		@Override
		public OneDayTime getApplicationTime() {
			return new OneDayTime(800);
		}
		
	}
	
	@AllArgsConstructor
	public static class WorkTimezoneGoOutSetImpl implements WorkTimezoneGoOutSetGetMemento{
		@Override
		public TotalRoundingSet getTotalRoundingSet() {
			return new  TotalRoundingSet(GoOutTimeRoundingMethod.ROUNDING_AND_TOTAL, GoOutTimeRoundingMethod.ROUNDING_AND_TOTAL);
		}

		@Override
		public GoOutTimezoneRoundingSet getDiffTimezoneSetting() {
			return new GoOutTimezoneRoundingSet();
		}
	}
	
	@AllArgsConstructor
	public static class WorkTimezoneStampSetImpl implements WorkTimezoneStampSetGetMemento{

		@Override
		public List<PrioritySetting> getPrioritySet() {
			return Arrays.asList(new PrioritySetting());
		}

		@Override
		public RoundingTime getRoundingTime() {
			// TODO Auto-generated method stub
			return null;
		}
	}
	
	@AllArgsConstructor
	public static class WorkTimezoneCommonSetImpl  implements WorkTimezoneCommonSetGetMemento{

		@Override
		public boolean getZeroHStraddCalculateSet() {
			return false;
		}

		@Override
		public IntervalTimeSetting getIntervalSet() {
		    val intervalTimeSetting = new IntervalTimeSettingOImpl();
			return new IntervalTimeSetting(intervalTimeSetting);
		}

		@Override
		public List<WorkTimezoneOtherSubHolTimeSet> getSubHolTimeSet() {
			val workTimeZoneOtherSet = new WorkTimezoneOtherSubHolTimeSetImpl();
			return Arrays.asList(new WorkTimezoneOtherSubHolTimeSet(workTimeZoneOtherSet));
		}

		@Override
		public List<WorkTimezoneMedicalSet> getMedicalSet() {
			return Collections.emptyList();
		}

		@Override
		public WorkTimezoneGoOutSet getGoOutSet() {
			
			return new WorkTimezoneGoOutSet(new WorkTimezoneGoOutSetImpl());
		}

		@Override
		public WorkTimezoneStampSet getStampSet() {
			return new WorkTimezoneStampSet();
		}

		@Override
		public WorkTimezoneLateNightTimeSet getLateNightTimeSet() {
			return new WorkTimezoneLateNightTimeSet();
		}

		@Override
		public WorkTimezoneShortTimeWorkSet getShortTimeWorkSet() {
			return new WorkTimezoneShortTimeWorkSet();
		}

		@Override
		public WorkTimezoneExtraordTimeSet getExtraordTimeSet() {
			return new WorkTimezoneExtraordTimeSet();
		}

		@Override
		public WorkTimezoneLateEarlySet getLateEarlySet() {
			return new WorkTimezoneLateEarlySet();
		}

		@Override
		public HolidayCalculation getHolidayCalculation() {
			return new HolidayCalculation();
		}

		@Override
		public Optional<BonusPaySettingCode> getRaisingSalarySet() {
			return Optional.empty();
		}
	}
	
	@AllArgsConstructor
	static class OverTimeOfTimeZoneSetImpl implements OverTimeOfTimeZoneSetGetMemento{
		// 就業時間帯NO
		private EmTimezoneNo workTimezoneNo;

		// 早出残業として扱う
		private boolean earlyOTUse;

		// 時間帯
		private TimeZoneRounding timezone;

		// 残業枠NO
		private OTFrameNo otFrameNo;

		// 法定内残業枠NO
		private OTFrameNo legalOTframeNo;

		// 精算順序
		private SettlementOrder settlementOrder;
		
		@Override
		public EmTimezoneNo getWorkTimezoneNo() {
			return workTimezoneNo;
		}

		@Override
		public boolean getRestraintTimeUse() {
			return false;
		}

		@Override
		public boolean getEarlyOTUse() {
			return earlyOTUse;
		}

		@Override
		public TimeZoneRounding getTimezone() {
			return timezone;
		}

		@Override
		public OTFrameNo getOTFrameNo() {
			return otFrameNo;
		}

		@Override
		public OTFrameNo getLegalOTframeNo() {
			return legalOTframeNo;
		}

		@Override
		public SettlementOrder getSettlementOrder() {
			return settlementOrder;
		}
		
		
	}
	
	
	@AllArgsConstructor
    public static class FlexHalfDayWorkTimeImpl implements FlexHalfDayWorkTimeGetMemento{
    	private FlowWorkRestTimezone restTimezone;
    	
    	private FixedWorkTimezoneSet workTimezone;
    	
    	private AmPmAtr ampmAtr;
    	
		@Override
		public FlowWorkRestTimezone getRestTimezone() {
			return this.restTimezone;
		}

		@Override
		public FixedWorkTimezoneSet getWorkTimezone() {
			return this.workTimezone;
		}

		@Override
		public AmPmAtr getAmpmAtr() {
			return this.ampmAtr;
		}
		
    }
	
	
	
	
	
	@AllArgsConstructor
	@NoArgsConstructor
	@Setter
    public static class FlexWorkSettingImpl implements FlexWorkSettingGetMemento{
		
		private ApplyAtr coreSettingApplyAtr;
		
		private List<FlexHalfDayWorkTime> flexHalfDayWorkTimes;
		
		private FlexOffdayWorkTime flexOffDayWorkTime;
		
		private boolean useHalfDayShift;

		@Override
		public String getCompanyId() {
			return "dummy";
		}

		@Override
		public WorkTimeCode getWorkTimeCode() {
			return new WorkTimeCode("wkTimeCode");
		}

		@Override
		public CoreTimeSetting getCoreTimeSetting() {
			return new CoreTimeSetting(new TimeSheet(new TimeWithDayAttr(480), new TimeWithDayAttr(960))
										 , coreSettingApplyAtr, new AttendanceTime(3000));
		}

		@Override
		public FlowWorkRestSetting getRestSetting() {
			val flowWorkRestSetting = new FlowWorkRestSettingGetMementoImpl();
			return new FlowWorkRestSetting(flowWorkRestSetting);
		}

		@Override
		public FlexOffdayWorkTime getOffdayWorkTime() {
			return flexOffDayWorkTime;
		}

		@Override
		public WorkTimezoneCommonSet getCommonSetting() {
			return new WorkTimezoneCommonSet(new WorkTimezoneCommonSetImpl());
		}

		@Override
		public boolean getUseHalfDayShift() {
			return useHalfDayShift;
		}

		@Override
		public List<FlexHalfDayWorkTime> getLstHalfDayWorkTimezone() {
			return flexHalfDayWorkTimes;
		}

		@Override
		public List<StampReflectTimezone> getLstStampReflectTimezone() {
			return Collections.emptyList();
		}

		@Override
		public FlexCalcSetting getCalculateSetting() {
			return new FlexCalcSetting();
		}
		

    }
	
}
