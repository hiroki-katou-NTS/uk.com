package nts.uk.ctx.at.shared.dom.worktime.flexset;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.assertj.core.groups.Tuple;
import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.common.timerounding.Rounding;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.common.timerounding.Unit;
import nts.uk.ctx.at.shared.dom.common.usecls.ApplyAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.primitives.BonusPaySettingCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.WorkNo;
import nts.uk.ctx.at.shared.dom.worktime.ChangeableWorkingTimeZonePerNo;
import nts.uk.ctx.at.shared.dom.worktime.WorkSetting.Require;
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
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingTest.Helper.EmTimeZoneSetHelper;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingTest.Helper.FlexHalfDayWorkTimeHelper;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingTest.Helper.FlowWkRestTimezoneHelper;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingTest.Helper.OverTimeOfTimeZoneSetHelper;
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

@RunWith(JMockit.class)
public class FlexWorkSettingTest {
	@Injectable
	private Require require;
	
	@Test
	public void getters(@Injectable FlexWorkSetting flexWorkSetting) {
		NtsAssert.invokeGetters(flexWorkSetting);
	}
	
	/**
	 * 休憩時間帯を取得する
	 * 休出か == TRUE
	 * 午前午後区分 = ONE_DAY
	 * 休憩時間帯を固定にする = TRUE
	 * 期待値：　固定 = TRUE, 休憩時間帯= 「フレックス勤務設定」の「休日勤務時間帯」の「固定休憩時間帯」
	 */
	@Test
	public void getBreakTimeZone_isFixRestTime_true(@Injectable TimeSheet timeSheet) {
			val fixedRestTimeSet = new TimezoneOfFixedRestTimeSet(Arrays.asList(
					  new DeductionTime(TimeWithDayAttr.hourMinute(12, 00), TimeWithDayAttr.hourMinute(13, 00))
					, new DeductionTime(TimeWithDayAttr.hourMinute(17, 30), TimeWithDayAttr.hourMinute(18, 00))));
			val offRestTimeZone = new FlowWorkRestTimezone(true, fixedRestTimeSet, new FlowRestTimezone());
			val offdayWorkTimeImpl = new FlexOffdayWorkTimeGetMementoImpl(Arrays.asList(new HDWorkTimeSheetSetting()), offRestTimeZone);
			val flexWorkSetting = new FlexWorkSetting(new FlexWorkSettingImpl(ApplyAtr.USE, Collections.emptyList()
													, new FlexOffdayWorkTime(offdayWorkTimeImpl), true, timeSheet));
			
			val actual = flexWorkSetting.getBreakTimeZone(true, AmPmAtr.ONE_DAY);
			assertThat( actual.isFixed() ).isTrue();
			assertThat( actual.getBreakTimes() ).containsExactlyInAnyOrderElementsOf(fixedRestTimeSet.getRestTimezonesForCalc());
	}
	
	/**
	 * 休憩時間帯を取得する
	 * 休出か == TRUE
	 * 午前午後区分 = ONE_DAY
	 * 休憩時間帯を固定にする = FALSE
	 * 期待値：　固定 = FALSE, 休憩時間帯= empty
	 * 
	 */
	@Test
	public void getBreakTimeZone_isFixRestTime_false(@Injectable TimeSheet timeSheet) {
			val fixedRestTimeSet = new TimezoneOfFixedRestTimeSet(Arrays.asList(
					  new DeductionTime(TimeWithDayAttr.hourMinute(12, 00), TimeWithDayAttr.hourMinute(13, 00))
					, new DeductionTime(TimeWithDayAttr.hourMinute(17, 30), TimeWithDayAttr.hourMinute(18, 00))));
			//休憩時間帯を固定にする = FALSE
			val offRestTimeZone = new FlowWorkRestTimezone(false, fixedRestTimeSet, new FlowRestTimezone());
			
			val offdayWorkTimeImpl = new FlexOffdayWorkTimeGetMementoImpl(Arrays.asList(new HDWorkTimeSheetSetting()), offRestTimeZone);
			
			val flexWorkSetting = new FlexWorkSetting(new FlexWorkSettingImpl(ApplyAtr.USE, Collections.emptyList()
													, new FlexOffdayWorkTime(offdayWorkTimeImpl), true, timeSheet));

			val actual = flexWorkSetting.getBreakTimeZone(true, AmPmAtr.ONE_DAY);
			assertThat( actual.isFixed() ).isFalse();
			assertThat( actual.getBreakTimes() ).isEmpty();
	
	}
	
	/**
	 * 休憩時間帯を取得する
	 * 休出か == FALSE
	 * 午前午後区分 = ONE_DAY
	 * 休憩時間帯を固定にする = TRUE
	 * 期待値：　固定 = TRUE, 休憩時間帯= 「フレックス勤務設定」の「平日勤務時間帯」のONE_DAYの「 休憩時間帯」
	 */
	@Test
	public void getBreakTimeZone_isWorkingOnDayOff_false_isFixRestTime_true(@Injectable TimeSheet timeSheet) {
		// 休憩時間帯 
		val flexHalfRestTimezone = FlowWkRestTimezoneHelper.createRestTimeZone(new FlowRestSetting(new AttendanceTime(120), new AttendanceTime(60))
				,  new FlowRestSetting(new AttendanceTime(240), new AttendanceTime(320)), false);

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
		
		val flexWorkSetting = new FlexWorkSetting(new FlexWorkSettingImpl(ApplyAtr.USE, flexHalfDayWorkTimes
												, new FlexOffdayWorkTime(offdayWorkTimeImpl), true, timeSheet));

		val actual = flexWorkSetting.getBreakTimeZone(true, AmPmAtr.ONE_DAY);
		
		assertThat( actual.isFixed() ).isTrue();
		
		assertThat( actual.getBreakTimes() ).containsExactlyInAnyOrderElementsOf(fixedRestTimeSet.getRestTimezonesForCalc());
	}
	

	/**
	 * 休憩時間帯を取得する
	 * 休出か == FALSE
	 * 午前午後区分 = ONE_DAY
	 * 休憩時間帯を固定にする = FALSE
	 * 期待値：　固定 = TRUE, 休憩時間帯= empty
	 */
	@Test
	public void getBreakTimeZone_isWorkingOnDayOff_false_isFixRestTime_false(@Injectable TimeSheet timeSheet) {
			val fixedRestTimeSet = new TimezoneOfFixedRestTimeSet(Arrays.asList(
					  new DeductionTime(TimeWithDayAttr.hourMinute(12, 00), TimeWithDayAttr.hourMinute(13, 00))
					, new DeductionTime(TimeWithDayAttr.hourMinute(17, 30), TimeWithDayAttr.hourMinute(18, 00))));
			//休憩時間帯を固定にする = FALSE
			val offRestTimeZone = new FlowWorkRestTimezone(false, fixedRestTimeSet, new FlowRestTimezone());
			
			val offdayWorkTimeImpl = new FlexOffdayWorkTimeGetMementoImpl(Arrays.asList(new HDWorkTimeSheetSetting()), offRestTimeZone);
			
			val flexWorkSetting = new FlexWorkSetting(new FlexWorkSettingImpl(ApplyAtr.USE, Collections.emptyList()
													, new FlexOffdayWorkTime(offdayWorkTimeImpl), true, timeSheet));

			val actual = flexWorkSetting.getBreakTimeZone(true, AmPmAtr.ONE_DAY);
			
			assertThat( actual.isFixed() ).isFalse();
			
			assertThat( actual.getBreakTimes() ).isEmpty();
	
	}
	

	/**  [prv-1] 指定した午前午後区分の時間帯情報を作成する   **/
	/**
	 * 就業の時間帯 = (10:00, 12:00)
	 * 午前午後区分 = ONE_DAY
	 * コアタイムを使用しない⇒開始/終了が同じ
	 * 結果：$勤務可能時間帯
	 * 
	 */
	@Test
	public void createTimeZoneByAmPmCls_OneDay_CoreTime_Not_Use(@Injectable TimeSheet timeSheet) {
		// 休憩時間帯 
		val flexHalfRestTimezone = FlowWkRestTimezoneHelper.createRestTimeZone(new FlowRestSetting(new AttendanceTime(120), new AttendanceTime(60))
				,  new FlowRestSetting(new AttendanceTime(240), new AttendanceTime(320)), false);

		// 残業時間帯
		val oTTimezoneImpls = Arrays.asList(
				      OverTimeOfTimeZoneSetHelper.createOverTime(1, true, TimeWithDayAttr.hourMinute(  5, 0), TimeWithDayAttr.hourMinute(8, 30))
					, OverTimeOfTimeZoneSetHelper.createOverTime(2, true, TimeWithDayAttr.hourMinute( 18, 0), TimeWithDayAttr.hourMinute(22, 0))
					, OverTimeOfTimeZoneSetHelper.createOverTime(3, true, TimeWithDayAttr.hourMinute( 22, 0), TimeWithDayAttr.hourMinute(29, 0))
				);
		
		val flexHalfDayWorkTimes = Arrays.asList(
				  FlexHalfDayWorkTimeHelper.createWorkTime(AmPmAtr.ONE_DAY, flexHalfRestTimezone
						  , OverTimeOfTimeZoneSetHelper.createOverTimeList(oTTimezoneImpls)
						  , Arrays.asList(
								  EmTimeZoneSetHelper.createWorkingTimezoneList(new EmTimeFrameNo(1) , TimeWithDayAttr.hourMinute( 5,  0 ), TimeWithDayAttr.hourMinute( 29, 0 ))
							))
				, FlexHalfDayWorkTimeHelper.createWorkTime(AmPmAtr.AM, flexHalfRestTimezone
						,  OverTimeOfTimeZoneSetHelper.createOverTimeList(oTTimezoneImpls)
						, Arrays.asList(
								 EmTimeZoneSetHelper.createWorkingTimezoneList(new EmTimeFrameNo(2) , TimeWithDayAttr.hourMinute( 5,  0 ), TimeWithDayAttr.hourMinute( 12, 0 ))
						))
				);
		// コアタイムを使用しない
		val flexWorkSetting = new FlexWorkSetting(new FlexWorkSettingImpl(ApplyAtr.NOT_USE, flexHalfDayWorkTimes
												, new FlexOffdayWorkTime(), false, timeSheet));
		
		// Execute
	    @SuppressWarnings("unchecked")
 		val result = (List<ChangeableWorkingTimeZonePerNo>) NtsAssert.Invoke.privateMethod(
				                flexWorkSetting
							,	"createTimeZoneByAmPmCls"
							,	require
							,   new TimeSpanForCalc(TimeWithDayAttr.hourMinute(10,  0 ), TimeWithDayAttr.hourMinute( 12, 00 ))
							,   AmPmAtr.ONE_DAY
						);
	    assertThat(result.get(0).getWorkNo()).isEqualTo(new WorkNo(1));
	    assertThat(result.get(0).getForStart().getStart().v()).isEqualTo(300);
	    assertThat(result.get(0).getForStart().getEnd().v()).isEqualTo(1740);
	    assertThat(result.get(0).getForEnd().getStart().v()).isEqualTo(300);
	    assertThat(result.get(0).getForEnd().getEnd().v()).isEqualTo(1740);
		
	}
	
	/**
	 * 就業の時間帯 = (10:00, 12:00)
	 * 午前午後区分 = ONE_DAY
	 * コアタイム=(480: 960)
	 * $勤務可能時間帯= (300:　1740)
	 * 所定時間設定 = (12:00 -> 13:00)
	 * $開始 = 計算時間帯( $勤務可能時間帯.開始時刻, $コアタイム.開始時刻 )	[300 -> 480]
	 * $終了 = 計算時間帯( $コアタイム.終了時刻, $勤務可能時間帯.終了時刻 )	[960 -> 1740]
	 * 
	 */
	@Test
	public void createTimeZoneByAmPmCls_OneDay_CoreTime_Use() {
		// 休憩時間帯 
		val flexHalfRestTimezone = FlowWkRestTimezoneHelper.createRestTimeZone(new FlowRestSetting(new AttendanceTime(120), new AttendanceTime(60))
				,  new FlowRestSetting(new AttendanceTime(240), new AttendanceTime(320)), false);
		
		// 残業時間帯
		val oTTimezoneImpls = Arrays.asList(
				      OverTimeOfTimeZoneSetHelper.createOverTime(1, true, TimeWithDayAttr.hourMinute(  5, 0), TimeWithDayAttr.hourMinute(8, 30))
					, OverTimeOfTimeZoneSetHelper.createOverTime(2, true, TimeWithDayAttr.hourMinute( 18, 0), TimeWithDayAttr.hourMinute(22, 0))
					, OverTimeOfTimeZoneSetHelper.createOverTime(3, true, TimeWithDayAttr.hourMinute( 22, 0), TimeWithDayAttr.hourMinute(29, 0))
				);
		
		//就業時間帯
		val flexHalfDayWorkTimes = Arrays.asList(
				  FlexHalfDayWorkTimeHelper.createWorkTime(AmPmAtr.ONE_DAY, flexHalfRestTimezone
						  , OverTimeOfTimeZoneSetHelper.createOverTimeList(oTTimezoneImpls)
						  , Arrays.asList(
								  EmTimeZoneSetHelper.createWorkingTimezoneList(new EmTimeFrameNo(1) , TimeWithDayAttr.hourMinute( 5,  0 ), TimeWithDayAttr.hourMinute( 29, 0 ))
							))
				, FlexHalfDayWorkTimeHelper.createWorkTime(AmPmAtr.AM, flexHalfRestTimezone
						, OverTimeOfTimeZoneSetHelper.createOverTimeList(oTTimezoneImpls)
						, Arrays.asList(
								 EmTimeZoneSetHelper.createWorkingTimezoneList(new EmTimeFrameNo(2) , TimeWithDayAttr.hourMinute( 5,  0 ), TimeWithDayAttr.hourMinute( 12, 0 ))
						))
				, FlexHalfDayWorkTimeHelper.createWorkTime(AmPmAtr.PM, flexHalfRestTimezone
						, OverTimeOfTimeZoneSetHelper.createOverTimeList(oTTimezoneImpls)
						, Arrays.asList(
								 EmTimeZoneSetHelper.createWorkingTimezoneList(new EmTimeFrameNo(3) , TimeWithDayAttr.hourMinute( 13,  0 ), TimeWithDayAttr.hourMinute( 29, 0 ))
						))
				);
		
		val timeSheet = new TimeSheet(new TimeWithDayAttr(480), new TimeWithDayAttr(960));

		// コアタイムを使用する
		val flexWorkSetting = new FlexWorkSetting(new FlexWorkSettingImpl(ApplyAtr.USE, flexHalfDayWorkTimes
												, new FlexOffdayWorkTime(), false, timeSheet));
		
		// Execute
	    @SuppressWarnings("unchecked")
 		val result = (List<ChangeableWorkingTimeZonePerNo>) NtsAssert.Invoke.privateMethod(
				                flexWorkSetting
							,	"createTimeZoneByAmPmCls"
							,	require
							,   new TimeSpanForCalc(TimeWithDayAttr.hourMinute(10,  0 ), TimeWithDayAttr.hourMinute( 12, 00 ))
							,   AmPmAtr.ONE_DAY
						);
	    
	    assertThat(result.get(0).getWorkNo()).isEqualTo(new WorkNo(1));
	    assertThat(result.get(0).getForStart().getStart().v()).isEqualTo(300);
	    assertThat(result.get(0).getForStart().getEnd().v()).isEqualTo(480);
	    assertThat(result.get(0).getForEnd().getStart().v()).isEqualTo(960);
	    assertThat(result.get(0).getForEnd().getEnd().v()).isEqualTo(1740);
		
	}
	
	/**
	 * 就業の時間帯 = (10:00, 12:00)
	 * 午前午後区分 = AM
	 * $勤務可能時間帯= (300:　1740)
	 * コアタイムを使用しない⇒開始/終了が同じ
	 * 結果：$勤務可能時間帯
	 * 
	 */
	
	@Test
	public void createTimeZoneByAmPmCls_Morning_OneDay_CoreTime_Not_Use(@Injectable TimeSheet timeSheet) {
		// 休憩時間帯 
		val flexHalfRestTimezone = FlowWkRestTimezoneHelper.createRestTimeZone(
				   new FlowRestSetting(new AttendanceTime(120), new AttendanceTime(60))
				,  new FlowRestSetting(new AttendanceTime(240), new AttendanceTime(320)), false);

		// 残業時間帯
		val oTTimezoneImpls = Arrays.asList(
					  OverTimeOfTimeZoneSetHelper.createOverTime(1, true, TimeWithDayAttr.hourMinute(  5, 0), TimeWithDayAttr.hourMinute(8, 30))
					, OverTimeOfTimeZoneSetHelper.createOverTime(2, true, TimeWithDayAttr.hourMinute( 18, 0), TimeWithDayAttr.hourMinute(22, 0))
					, OverTimeOfTimeZoneSetHelper.createOverTime(3, true, TimeWithDayAttr.hourMinute( 22, 0), TimeWithDayAttr.hourMinute(29, 0))
				);
		//就業時間帯
		val flexHalfDayWorkTimes = Arrays.asList(
				  FlexHalfDayWorkTimeHelper.createWorkTime(AmPmAtr.ONE_DAY, flexHalfRestTimezone
						  ,  OverTimeOfTimeZoneSetHelper.createOverTimeList(oTTimezoneImpls)
						  , Arrays.asList(
								  EmTimeZoneSetHelper.createWorkingTimezoneList(new EmTimeFrameNo(1) , TimeWithDayAttr.hourMinute( 5,  0 ), TimeWithDayAttr.hourMinute( 29, 0 ))
							))
				, FlexHalfDayWorkTimeHelper.createWorkTime(AmPmAtr.AM, flexHalfRestTimezone
						,  OverTimeOfTimeZoneSetHelper.createOverTimeList(oTTimezoneImpls)
						, Arrays.asList(
								 EmTimeZoneSetHelper.createWorkingTimezoneList(new EmTimeFrameNo(2) , TimeWithDayAttr.hourMinute( 5,  0 ), TimeWithDayAttr.hourMinute( 12, 0 ))
						))
				, FlexHalfDayWorkTimeHelper.createWorkTime(AmPmAtr.PM, flexHalfRestTimezone
						, OverTimeOfTimeZoneSetHelper.createOverTimeList(oTTimezoneImpls)
						, Arrays.asList(
								 EmTimeZoneSetHelper.createWorkingTimezoneList(new EmTimeFrameNo(3) , TimeWithDayAttr.hourMinute( 13,  0 ), TimeWithDayAttr.hourMinute( 29, 0 ))
						))
				);
		
		val fixedRestTimeSet = new TimezoneOfFixedRestTimeSet(Arrays.asList(
				  new DeductionTime(TimeWithDayAttr.hourMinute(12, 00), TimeWithDayAttr.hourMinute(13, 00))
				, new DeductionTime(TimeWithDayAttr.hourMinute(17, 30), TimeWithDayAttr.hourMinute(18, 00))));
		
		val offdayWorkTimeImpl = new FlexOffdayWorkTimeGetMementoImpl(
				  Arrays.asList(new HDWorkTimeSheetSetting(new HDWorkTimeSheetSettingImpl(TimeWithDayAttr.hourMinute(5, 00), TimeWithDayAttr.hourMinute(29, 00))))
				, new FlowWorkRestTimezone(true, fixedRestTimeSet, new FlowRestTimezone()));

		// コアタイムを使用する
		val flexWorkSetting = new FlexWorkSetting(new FlexWorkSettingImpl(ApplyAtr.NOT_USE, flexHalfDayWorkTimes
												, new FlexOffdayWorkTime(offdayWorkTimeImpl), false, timeSheet));
		
		// Execute
	    @SuppressWarnings("unchecked")
 		val result = (List<ChangeableWorkingTimeZonePerNo>) NtsAssert.Invoke.privateMethod(
				                flexWorkSetting
							,	"createTimeZoneByAmPmCls"
							,	require
							,   new TimeSpanForCalc(TimeWithDayAttr.hourMinute(10,  0 ), TimeWithDayAttr.hourMinute( 12, 00 ))
							,   AmPmAtr.AM
						);
	    
	    assertThat(result.get(0).getWorkNo()).isEqualTo(new WorkNo(1));
	    assertThat(result.get(0).getForStart().getStart().v()).isEqualTo(300);
	    assertThat(result.get(0).getForStart().getEnd().v()).isEqualTo(1740);	
	    assertThat(result.get(0).getForEnd().getStart().v()).isEqualTo(300);
	    assertThat(result.get(0).getForEnd().getEnd().v()).isEqualTo(1740);	
		
	}
	
	/**
	 * 就業の時間帯 = (10:00, 12:00)
	 * 午前午後区分 = AM
	 * コアタイムを使用する
	 * コアタイム=(480: 960)
	 * $勤務可能時間帯= (300 ->　1740)
	 * 所定時間設定 = (12:00 -> 13:00)
	 * 結果：	$開始 = 計算時間帯( $勤務可能時間帯.開始時刻, 所定時間設定.開始時刻 )																				
     *      $終了 = 計算時間帯( 所定時間設定	.終了時刻, $勤務可能時間帯.終了時刻 )
	 * 
	 */
	
	@Test
	public void createTimeZoneByAmPmCls_Morning_OneDay_CoreTime_Use() {
		// 休憩時間帯 
		val flexHalfRestTimezone = FlowWkRestTimezoneHelper.createRestTimeZone(new FlowRestSetting(new AttendanceTime(120), new AttendanceTime(60))
				,  new FlowRestSetting(new AttendanceTime(240), new AttendanceTime(320)), false);
		
		// 残業時間帯
		val oTTimezoneImpls = Arrays.asList(
				      OverTimeOfTimeZoneSetHelper.createOverTime(1, true, TimeWithDayAttr.hourMinute(  5, 0), TimeWithDayAttr.hourMinute(8, 30))
					, OverTimeOfTimeZoneSetHelper.createOverTime(2, true, TimeWithDayAttr.hourMinute( 18, 0), TimeWithDayAttr.hourMinute(22, 0))
					, OverTimeOfTimeZoneSetHelper.createOverTime(3, true, TimeWithDayAttr.hourMinute( 22, 0), TimeWithDayAttr.hourMinute(29, 0))
				);
		
		//就業時間帯
		val flexHalfDayWorkTimes = Arrays.asList(
				  FlexHalfDayWorkTimeHelper.createWorkTime(AmPmAtr.ONE_DAY, flexHalfRestTimezone
						  , OverTimeOfTimeZoneSetHelper.createOverTimeList(oTTimezoneImpls)
						  , Arrays.asList(
								  EmTimeZoneSetHelper.createWorkingTimezoneList(new EmTimeFrameNo(1) , TimeWithDayAttr.hourMinute( 5,  0 ), TimeWithDayAttr.hourMinute( 29, 0 ))
							))
				, FlexHalfDayWorkTimeHelper.createWorkTime(AmPmAtr.AM, flexHalfRestTimezone
						, OverTimeOfTimeZoneSetHelper.createOverTimeList(oTTimezoneImpls)
						, Arrays.asList(
								 EmTimeZoneSetHelper.createWorkingTimezoneList(new EmTimeFrameNo(2) , TimeWithDayAttr.hourMinute( 5,  0 ), TimeWithDayAttr.hourMinute( 12, 0 ))
						))
				, FlexHalfDayWorkTimeHelper.createWorkTime(AmPmAtr.PM, flexHalfRestTimezone
						, OverTimeOfTimeZoneSetHelper.createOverTimeList(oTTimezoneImpls)
						, Arrays.asList(
								 EmTimeZoneSetHelper.createWorkingTimezoneList(new EmTimeFrameNo(3) , TimeWithDayAttr.hourMinute( 13,  0 ), TimeWithDayAttr.hourMinute( 29, 0 ))
						))
				);
		
		
		val fixedRestTimeSet = new TimezoneOfFixedRestTimeSet(Arrays.asList(
				  new DeductionTime(TimeWithDayAttr.hourMinute(12, 00), TimeWithDayAttr.hourMinute(13, 00))
				, new DeductionTime(TimeWithDayAttr.hourMinute(17, 30), TimeWithDayAttr.hourMinute(18, 00))));
		
		val offdayWorkTimeImpl = new FlexOffdayWorkTimeGetMementoImpl(
				  Arrays.asList(new HDWorkTimeSheetSetting(new HDWorkTimeSheetSettingImpl(TimeWithDayAttr.hourMinute(5, 00), TimeWithDayAttr.hourMinute(29, 00))))
				, new FlowWorkRestTimezone(true, fixedRestTimeSet, new FlowRestTimezone()));

		val timeSheet = new TimeSheet(new TimeWithDayAttr(480), new TimeWithDayAttr(960));
		// コアタイムを使用しない
		val flexWorkSetting = new FlexWorkSetting(new FlexWorkSettingImpl(ApplyAtr.USE, flexHalfDayWorkTimes
												, new FlexOffdayWorkTime(offdayWorkTimeImpl), false, timeSheet));

		val morningEndTime = TimeWithDayAttr.hourMinute( 12,  0 );
		val afternoonStartTime = TimeWithDayAttr.hourMinute( 13,  0 );
		
		// 所定時間設定		
		val predTimeStg = Helper.PredTimeStg.create(morningEndTime, afternoonStartTime);
		
		new Expectations() {
			{
				require.getPredetermineTimeSetting((WorkTimeCode) any);
				result = predTimeStg;
			}
		};
		
		val worktime = new TimeSpanForCalc(TimeWithDayAttr.hourMinute(10,  0 ), TimeWithDayAttr.hourMinute( 12, 00 ));
		// Execute
	    @SuppressWarnings("unchecked")
 		val result = (List<ChangeableWorkingTimeZonePerNo>) NtsAssert.Invoke.privateMethod(
				                flexWorkSetting
							,	"createTimeZoneByAmPmCls"
							,	require
							,   worktime
							,   AmPmAtr.AM
						);
	    //480: 600
	    assertThat(result.get(0).getWorkNo()).isEqualTo(new WorkNo(1));
	    assertThat(result.get(0).getForStart().getStart().v()).isEqualTo(300);
	    assertThat(result.get(0).getForStart().getEnd().v()).isEqualTo(480);	
	    assertThat(result.get(0).getForEnd().getStart().v()).isEqualTo(720);
	    assertThat(result.get(0).getForEnd().getEnd().v()).isEqualTo(1740);	
		
	}
	
	/**
	 * 就業の時間帯 = (10:00, 12:00)
	 * 午前午後区分 = AM
	 * 平日勤務時間帯リスト中に午前午後区分 = AMを存在しない
	 * コアタイムを使用する
	 * 結果：	$開始 = 計算時間帯(  就業の時間帯.開始時刻 , 所定時間設定の開始時刻 ) 																		
     *      $終了 = 計算時間帯( $コアタイム.終了時刻, 就業の時間帯.終了時刻 )
	 * 
	 */
	
	@Test
	public void createTimeZoneByAmPmCls_Morning_Not_Exist(@Injectable TimeSheet timeSheet) {
		// 休憩時間帯 
		val flexHalfRestTimezone = FlowWkRestTimezoneHelper.createRestTimeZone(new FlowRestSetting(new AttendanceTime(120), new AttendanceTime(60))
				,  new FlowRestSetting(new AttendanceTime(240), new AttendanceTime(320)), false);

		// 残業時間帯
		val oTTimezoneImpls = Arrays.asList(
				      OverTimeOfTimeZoneSetHelper.createOverTime(1, true, TimeWithDayAttr.hourMinute(  5, 0), TimeWithDayAttr.hourMinute(8, 30))
					, OverTimeOfTimeZoneSetHelper.createOverTime(2, true, TimeWithDayAttr.hourMinute( 18, 0), TimeWithDayAttr.hourMinute(22, 0))
					, OverTimeOfTimeZoneSetHelper.createOverTime(3, true, TimeWithDayAttr.hourMinute( 22, 0), TimeWithDayAttr.hourMinute(29, 0))
				);
		
		//就業時間帯
		val flexHalfDayWorkTimes = Arrays.asList(
				  FlexHalfDayWorkTimeHelper.createWorkTime(AmPmAtr.ONE_DAY, flexHalfRestTimezone
						  , OverTimeOfTimeZoneSetHelper.createOverTimeList(oTTimezoneImpls)
						  , Arrays.asList(
								  EmTimeZoneSetHelper.createWorkingTimezoneList(new EmTimeFrameNo(1) , TimeWithDayAttr.hourMinute( 5,  0 ), TimeWithDayAttr.hourMinute( 29, 0 ))
							))
				, FlexHalfDayWorkTimeHelper.createWorkTime(AmPmAtr.PM, flexHalfRestTimezone
						, OverTimeOfTimeZoneSetHelper.createOverTimeList(oTTimezoneImpls)
						, Arrays.asList(
								 EmTimeZoneSetHelper.createWorkingTimezoneList(new EmTimeFrameNo(3) , TimeWithDayAttr.hourMinute( 13,  0 ), TimeWithDayAttr.hourMinute( 29, 0 ))
						))
				);
		
		
		val fixedRestTimeSet = new TimezoneOfFixedRestTimeSet(Arrays.asList(
				  new DeductionTime(TimeWithDayAttr.hourMinute(12, 00), TimeWithDayAttr.hourMinute(13, 00))
				, new DeductionTime(TimeWithDayAttr.hourMinute(17, 30), TimeWithDayAttr.hourMinute(18, 00))));
		
		val offdayWorkTimeImpl = new FlexOffdayWorkTimeGetMementoImpl(
				  Arrays.asList(new HDWorkTimeSheetSetting(new HDWorkTimeSheetSettingImpl(TimeWithDayAttr.hourMinute(5, 00), TimeWithDayAttr.hourMinute(29, 00))))
				, new FlowWorkRestTimezone(true, fixedRestTimeSet, new FlowRestTimezone()));
		

		// コアタイムを使用する
		val flexWorkSetting = new FlexWorkSetting(new FlexWorkSettingImpl(ApplyAtr.USE, flexHalfDayWorkTimes
												, new FlexOffdayWorkTime(offdayWorkTimeImpl), false, timeSheet));

		val morningEndTime = TimeWithDayAttr.hourMinute( 12,  0 );
		val afternoonStartTime = TimeWithDayAttr.hourMinute( 13,  0 );
		
		// 所定時間設定		
		val predTimeStg = Helper.PredTimeStg.create(morningEndTime, afternoonStartTime);
		
		new Expectations() {
			{
				require.getPredetermineTimeSetting((WorkTimeCode) any);
				result = predTimeStg;
			}
		};
		
		val worktime = new TimeSpanForCalc(TimeWithDayAttr.hourMinute(10,  0 ), TimeWithDayAttr.hourMinute( 12, 00 ));
		// Execute
	    @SuppressWarnings("unchecked")
 		val result = (List<ChangeableWorkingTimeZonePerNo>) NtsAssert.Invoke.privateMethod(
				                flexWorkSetting
							,	"createTimeZoneByAmPmCls"
							,	require
							,   worktime
							,   AmPmAtr.AM
						);
	    //480 720
	    assertThat(result.get(0).getWorkNo()).isEqualTo(new WorkNo(1));
	    assertThat(result.get(0).getForStart().getStart().v()).isEqualTo(600);
	    assertThat(result.get(0).getForStart().getEnd().v()).isEqualTo(480);	
	    assertThat(result.get(0).getForEnd().getStart().v()).isEqualTo(720);
	    assertThat(result.get(0).getForEnd().getEnd().v()).isEqualTo(720);		
		
	}
	
	/**
	 * 就業の時間帯 = (10:00, 12:00)
	 * 午前午後区分 = PM
	 * コアタイムを使用しない⇒開始/終了が同じ
	 * 結果：$勤務可能時間帯
	 * 
	 */
	
	@Test
	public void createTimeZoneByAmPmCls_After_OneDay_CoreTime_Not_Use(@Injectable TimeSheet timeSheet) {
		// 休憩時間帯 
		val flexHalfRestTimezone = FlowWkRestTimezoneHelper.createRestTimeZone(new FlowRestSetting(new AttendanceTime(120), new AttendanceTime(60))
				,  new FlowRestSetting(new AttendanceTime(240), new AttendanceTime(320)), false);
		
		// 残業時間帯
		val oTTimezoneImpls = Arrays.asList(
				      OverTimeOfTimeZoneSetHelper.createOverTime(1, true, TimeWithDayAttr.hourMinute(  5, 0), TimeWithDayAttr.hourMinute(8, 30))
					, OverTimeOfTimeZoneSetHelper.createOverTime(2, true, TimeWithDayAttr.hourMinute( 18, 0), TimeWithDayAttr.hourMinute(22, 0))
					, OverTimeOfTimeZoneSetHelper.createOverTime(3, true, TimeWithDayAttr.hourMinute( 22, 0), TimeWithDayAttr.hourMinute(29, 0))
				);
		
		//就業時間帯
		val flexHalfDayWorkTimes = Arrays.asList(
				  FlexHalfDayWorkTimeHelper.createWorkTime(AmPmAtr.ONE_DAY, flexHalfRestTimezone
						  , OverTimeOfTimeZoneSetHelper.createOverTimeList(oTTimezoneImpls)
						  , Arrays.asList(
								  EmTimeZoneSetHelper.createWorkingTimezoneList(new EmTimeFrameNo(1) , TimeWithDayAttr.hourMinute( 5,  0 ), TimeWithDayAttr.hourMinute( 29, 0 ))
							))
				, FlexHalfDayWorkTimeHelper.createWorkTime(AmPmAtr.AM, flexHalfRestTimezone
						, OverTimeOfTimeZoneSetHelper.createOverTimeList(oTTimezoneImpls)
						, Arrays.asList(
								 EmTimeZoneSetHelper.createWorkingTimezoneList(new EmTimeFrameNo(2) , TimeWithDayAttr.hourMinute( 5,  0 ), TimeWithDayAttr.hourMinute( 12, 0 ))
						))
				, FlexHalfDayWorkTimeHelper.createWorkTime(AmPmAtr.PM, flexHalfRestTimezone
						, OverTimeOfTimeZoneSetHelper.createOverTimeList(oTTimezoneImpls)
						, Arrays.asList(
								 EmTimeZoneSetHelper.createWorkingTimezoneList(new EmTimeFrameNo(3) , TimeWithDayAttr.hourMinute( 13,  0 ), TimeWithDayAttr.hourMinute( 29, 0 ))
						))
				);
		
		val fixedRestTimeSet = new TimezoneOfFixedRestTimeSet(Arrays.asList(
				  new DeductionTime(TimeWithDayAttr.hourMinute(12, 00), TimeWithDayAttr.hourMinute(13, 00))
				, new DeductionTime(TimeWithDayAttr.hourMinute(17, 30), TimeWithDayAttr.hourMinute(18, 00))));
		
		val offRestTimeZone = new FlowWorkRestTimezone(true, fixedRestTimeSet, new FlowRestTimezone());
		
		val offdayWorkTimeImpl = new FlexOffdayWorkTimeGetMementoImpl(
				  Arrays.asList(new HDWorkTimeSheetSetting(new HDWorkTimeSheetSettingImpl(TimeWithDayAttr.hourMinute(5, 00), TimeWithDayAttr.hourMinute(29, 00))))
				, offRestTimeZone);

		// コアタイムを使用する
		val flexWorkSetting = new FlexWorkSetting(new FlexWorkSettingImpl(ApplyAtr.NOT_USE, flexHalfDayWorkTimes
												, new FlexOffdayWorkTime(offdayWorkTimeImpl), false, timeSheet));
		
		// Execute
	    @SuppressWarnings("unchecked")
 		val result = (List<ChangeableWorkingTimeZonePerNo>) NtsAssert.Invoke.privateMethod(
				                flexWorkSetting
							,	"createTimeZoneByAmPmCls"
							,	require
							,   new TimeSpanForCalc(TimeWithDayAttr.hourMinute(10,  0 ), TimeWithDayAttr.hourMinute( 12, 00 ))
							,   AmPmAtr.PM
						);
	    
	    Optional<TimeSpanForCalc> wkTimePossibles = TimeSpanForCalc.join(oTTimezoneImpls.stream().map(c -> {return c.getTimezone().timeSpan();}).collect(Collectors.toList()));
	    assertThat(result.get(0).getWorkNo()).isEqualTo(new WorkNo(1));
	    assertThat(result.get(0).getForStart()).isEqualTo(wkTimePossibles.get());
	    assertThat(result.get(0).getForEnd()).isEqualTo(wkTimePossibles.get());	
		
	}
	
	/**
	 * 就業の時間帯 = (10:00, 12:00)
	 * 午前午後区分 = PM
	 * コアタイムを使用する
	 * 結果：	$開始 = 計算時間帯( $勤務可能時間帯.開始時刻, $コアタイム.開始時刻 )																				
     *      $終了 = 計算時間帯( $コアタイム.終了時刻, $勤務可能時間帯.終了時刻 )
	 * 
	 */
	
	@Test
	public void createTimeZoneByAmPmCls_After_OneDay_CoreTime_Use(@Injectable TimeSheet timeSheet) {
		// 休憩時間帯 
		val flexHalfRestTimezone = FlowWkRestTimezoneHelper.createRestTimeZone(new FlowRestSetting(new AttendanceTime(120), new AttendanceTime(60))
				,  new FlowRestSetting(new AttendanceTime(240), new AttendanceTime(320)), false);
		
		// 残業時間帯
		val oTTimezoneImpls = Arrays.asList(
				      OverTimeOfTimeZoneSetHelper.createOverTime(1, true, TimeWithDayAttr.hourMinute(  5, 0), TimeWithDayAttr.hourMinute(8, 30))
					, OverTimeOfTimeZoneSetHelper.createOverTime(2, true, TimeWithDayAttr.hourMinute( 18, 0), TimeWithDayAttr.hourMinute(22, 0))
					, OverTimeOfTimeZoneSetHelper.createOverTime(3, true, TimeWithDayAttr.hourMinute( 22, 0), TimeWithDayAttr.hourMinute(29, 0))
				);
		
		//就業時間帯
		val flexHalfDayWorkTimes = Arrays.asList(
				  FlexHalfDayWorkTimeHelper.createWorkTime(AmPmAtr.ONE_DAY, flexHalfRestTimezone
						  , OverTimeOfTimeZoneSetHelper.createOverTimeList(oTTimezoneImpls)
						  , Arrays.asList(
								  EmTimeZoneSetHelper.createWorkingTimezoneList(new EmTimeFrameNo(1) , TimeWithDayAttr.hourMinute( 5,  0 ), TimeWithDayAttr.hourMinute( 29, 0 ))
							))
				, FlexHalfDayWorkTimeHelper.createWorkTime(AmPmAtr.AM, flexHalfRestTimezone
						, OverTimeOfTimeZoneSetHelper.createOverTimeList(oTTimezoneImpls)
						, Arrays.asList(
								 EmTimeZoneSetHelper.createWorkingTimezoneList(new EmTimeFrameNo(2) , TimeWithDayAttr.hourMinute( 5,  0 ), TimeWithDayAttr.hourMinute( 12, 0 ))
						))
				, FlexHalfDayWorkTimeHelper.createWorkTime(AmPmAtr.PM, flexHalfRestTimezone
						, OverTimeOfTimeZoneSetHelper.createOverTimeList(oTTimezoneImpls)
						, Arrays.asList(
								 EmTimeZoneSetHelper.createWorkingTimezoneList(new EmTimeFrameNo(3) , TimeWithDayAttr.hourMinute( 13,  0 ), TimeWithDayAttr.hourMinute( 29, 0 ))
						))
				);
		
		val fixedRestTimeSet = new TimezoneOfFixedRestTimeSet(Arrays.asList(
				  new DeductionTime(TimeWithDayAttr.hourMinute(12, 00), TimeWithDayAttr.hourMinute(13, 00))
				, new DeductionTime(TimeWithDayAttr.hourMinute(17, 30), TimeWithDayAttr.hourMinute(18, 00))));
		
		val offdayWorkTimeImpl = new FlexOffdayWorkTimeGetMementoImpl(
				  Arrays.asList(new HDWorkTimeSheetSetting(new HDWorkTimeSheetSettingImpl(TimeWithDayAttr.hourMinute(5, 00), TimeWithDayAttr.hourMinute(29, 00))))
				, new FlowWorkRestTimezone(true, fixedRestTimeSet, new FlowRestTimezone()));

		// コアタイムを使用しない
		val flexWorkSetting = new FlexWorkSetting(new FlexWorkSettingImpl(ApplyAtr.USE, flexHalfDayWorkTimes
												, new FlexOffdayWorkTime(offdayWorkTimeImpl), false, timeSheet));

		val morningEndTime = TimeWithDayAttr.hourMinute( 12,  0 );
		val afternoonStartTime = TimeWithDayAttr.hourMinute( 13,  0 );
		
		// 所定時間設定		
		val predTimeStg = Helper.PredTimeStg.create(morningEndTime, afternoonStartTime);
		
		new Expectations() {
			{
				require.getPredetermineTimeSetting((WorkTimeCode) any);
				result = predTimeStg;
			}
		};
		
		// Execute
	    @SuppressWarnings("unchecked")
 		val result = (List<ChangeableWorkingTimeZonePerNo>) NtsAssert.Invoke.privateMethod(
				                flexWorkSetting
							,	"createTimeZoneByAmPmCls"
							,	require
							,   new TimeSpanForCalc(TimeWithDayAttr.hourMinute(10,  0 ), TimeWithDayAttr.hourMinute( 12, 00 ))
							,   AmPmAtr.PM
						);
	    
	    Optional<TimeSpanForCalc> wkTimePossibles = TimeSpanForCalc.join(oTTimezoneImpls.stream().map(c -> {return c.getTimezone().timeSpan();}).collect(Collectors.toList()));
	    val coreTime = new TimeSpanForCalc(predTimeStg.getPrescribedTimezoneSetting().getAfternoonStartTime(), flexWorkSetting.getCoreTimeSetting().getCoreTimeSheet().getEndTime());
	    val startTime_excepted = new TimeSpanForCalc(wkTimePossibles.get().getStart(), coreTime.getStart());
	    val endTime_excepted = new TimeSpanForCalc(coreTime.getEnd(), wkTimePossibles.get().getEnd());
	    assertThat(result.get(0).getWorkNo()).isEqualTo(new WorkNo(1));
	    assertThat(result.get(0).getForStart()).isEqualTo(startTime_excepted);
	    assertThat(result.get(0).getForEnd()).isEqualTo(endTime_excepted);	
		
	}
	
	
	/** 
	 * [prv-2] 指定した午前午後区分のコアタイム時間帯を取得する
	 * 午前午後区分 = ONE_DAY
	 * 開始時刻 = コアタイム時間帯.コアタイム時間帯.開始時刻
	 * 終了時刻 = コアタイム時間帯.コアタイム時間帯.終了時刻
	 */
	@Test
	public void getCoreTimeByAmPm_one_day() {
		
		val flexWorkSetting = new FlexWorkSetting(new FlexWorkSettingImpl());
		// Execute
 		val result = (TimeSpanForCalc) NtsAssert.Invoke.privateMethod(
				                flexWorkSetting
							,	"getCoreTimeByAmPm"
							,	require
							,   AmPmAtr.ONE_DAY
						);
		
		assertThat(result.getStart()).isEqualTo(flexWorkSetting.getCoreTimeSetting().getCoreTimeSheet().getStartTime());
		assertThat(result.getEnd()).isEqualTo(flexWorkSetting.getCoreTimeSetting().getCoreTimeSheet().getEndTime());		
		
	}
	
	/**
	 * [prv-2] 指定した午前午後区分のコアタイム時間帯を取得する
	 *  午前午後区分 = AM
	 *  開始時刻 = コアタイム時間帯.コアタイム時間帯.開始時刻
	 *  終了時刻 = 所定時間設定.所定時間帯.午前終了時刻
	 */
	@Test
	public void getCoreTimeByAmPm_Am() {
		val morningEndTime = TimeWithDayAttr.hourMinute( 12,  0 );
		val afternoonStartTime = TimeWithDayAttr.hourMinute( 13,  0 );
		
		// 所定時間設定		
		val predTimeStg = Helper.PredTimeStg.create(morningEndTime, afternoonStartTime);
		
		new Expectations() {
			{
				require.getPredetermineTimeSetting((WorkTimeCode)any);
				result = predTimeStg;
			}
		};
		
		val flexWorkSetting = new FlexWorkSetting(new FlexWorkSettingImpl());
		// Execute
 		val result = (TimeSpanForCalc) NtsAssert.Invoke.privateMethod(
				                flexWorkSetting
							,	"getCoreTimeByAmPm"
							,	require
							,   AmPmAtr.AM
						);
		
		assertThat(result.getStart()).isEqualTo(flexWorkSetting.getCoreTimeSetting().getCoreTimeSheet().getStartTime());
		assertThat(result.getEnd()).isEqualTo(predTimeStg.getPrescribedTimezoneSetting().getMorningEndTime());		
	}
	
	/**
	 * [prv-2] 指定した午前午後区分のコアタイム時間帯を取得する
	 * 午前午後区分 = PM
	 * 開始時刻 = 所定時間設定.所定時間帯.午後開始時刻
	 * 終了時刻 = コアタイム時間帯.コアタイム時間帯.終了時刻
	 */
	@Test
	public void getCoreTimeByAmPm_Pm() {
		val morningEndTime = TimeWithDayAttr.hourMinute( 12,  0 );
		val afternoonStartTime = TimeWithDayAttr.hourMinute( 13,  0 );
		
		// 所定時間設定		
		val predTimeStg = Helper.PredTimeStg.create(morningEndTime, afternoonStartTime);
		
		new Expectations() {
			{
				require.getPredetermineTimeSetting((WorkTimeCode)any);
				result = predTimeStg;
			}
		};
		
		val flexWorkSetting = new FlexWorkSetting(new FlexWorkSettingImpl());
		// Execute
 		val result = (TimeSpanForCalc) NtsAssert.Invoke.privateMethod(
				                flexWorkSetting
							,	"getCoreTimeByAmPm"
							,	require
							,   AmPmAtr.PM
						);
		
		assertThat(result.getStart()).isEqualTo(predTimeStg.getPrescribedTimezoneSetting().getAfternoonStartTime());
		assertThat(result.getEnd()).isEqualTo(flexWorkSetting.getCoreTimeSetting().getCoreTimeSheet().getEndTime());		
		
	}
	
	
	/**
	 * [prv-3]休出時の時間帯を作成する not empty
	 * 「$.重複の判断処理( $休出の時間帯 ) != 非重複」 
	 *  所定時間設定	時間帯1:　8:00 -> 15:00, 時間帯2:　13:00 -> 22:00
	 *  勤務時間帯        時間帯1:　8:00 -> 12:00, 時間帯2:　13:00 -> 18:00
	 *  期待値：　8:00　-> 18:00
	 */
	@Test
	public void createWorkOnDayOffTime() {
		List<HDWorkTimeSheetSetting> lstWorkTimezone=  Arrays.asList(
				   new HDWorkTimeSheetSetting(new HDWorkTimeSheetSettingImpl(TimeWithDayAttr.hourMinute(8, 00), TimeWithDayAttr.hourMinute(12, 00)))
				,  new HDWorkTimeSheetSetting(new HDWorkTimeSheetSettingImpl(TimeWithDayAttr.hourMinute(13, 00), TimeWithDayAttr.hourMinute(18, 00)))
				
				);
		
		val flexWorkSetting = Helper.createFlexWorkSetting(lstWorkTimezone);
		
		List<TimezoneUse> timeZones = Arrays.asList(
				    new TimezoneUse(TimeWithDayAttr.hourMinute(  8,  0 ), TimeWithDayAttr.hourMinute( 15, 00 ), UseSetting.USE, 1)
				,	new TimezoneUse(TimeWithDayAttr.hourMinute(  13,  0 ), TimeWithDayAttr.hourMinute( 22,  00), UseSetting.USE, 1)
			);
		
		// 所定時間設定		
		val instance = Helper.PredTimeStg.create(timeZones);
		
		new Expectations() {
			{
				require.getPredetermineTimeSetting((WorkTimeCode)any);
				result = instance;
			}
		};
		

		// Execute
		@SuppressWarnings("unchecked")
 		val result = (List<ChangeableWorkingTimeZonePerNo>)NtsAssert.Invoke.privateMethod(
				                flexWorkSetting
							,	"createWorkOnDayOffTime"
							,	require
						);
		
		assertThat( result )
						.extracting(
							  d -> d.getWorkNo()
							, d -> d.getForStart().getStart()
							, d -> d.getForStart().getEnd()
							, d -> d.getForEnd().getStart()
							, d -> d.getForEnd().getEnd())
	                    .containsExactly( 
                    		Tuple.tuple(
                    				new WorkNo(1) 
                    				, TimeWithDayAttr.hourMinute(8, 00)
                    				, TimeWithDayAttr.hourMinute(18, 00)
                    				, TimeWithDayAttr.hourMinute(8, 00)
                    				, TimeWithDayAttr.hourMinute(18, 00)));
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
    public static class FlexWorkSettingImpl implements FlexWorkSettingGetMemento{
		
		private ApplyAtr coreSettingApplyAtr;
		
		private List<FlexHalfDayWorkTime> flexHalfDayWorkTimes;
		
		private FlexOffdayWorkTime flexOffDayWorkTime;
		
		private boolean useHalfDayShift;
		
		private TimeSheet timeSheet;

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
	
	public static class Helper {
		
		@Injectable
		static FlowRestTimezone flowRestTimeZone;
		
		@Injectable
		static TimezoneOfFixedRestTimeSet fixedRestTimeSet;
		
		@Injectable
		static List<FlexHalfDayWorkTime> flexHalfDayWorkTimes;
		
		@Injectable
		static ApplyAtr isUse;
		
		@Injectable 
		static TimeSheet timeSheet;
		
		public static FlexWorkSetting createFlexWorkSetting(List<HDWorkTimeSheetSetting> lstWorkTimezone) {
			
			val offRestTimeZone = new FlowWorkRestTimezone(true, fixedRestTimeSet, flowRestTimeZone);
			
			val offdayWorkTimeImpl = new FlexOffdayWorkTimeGetMementoImpl(lstWorkTimezone, offRestTimeZone);

			// コアタイムを使用しない
			val flexWorkSetting = new FlexWorkSetting(new FlexWorkSettingImpl(isUse, flexHalfDayWorkTimes
													, new FlexOffdayWorkTime(offdayWorkTimeImpl), false, timeSheet));

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
		
	}

}
