package nts.uk.ctx.at.shared.dom.worktime.flexset;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import mockit.Mock;
import mockit.MockUp;
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
import nts.uk.ctx.at.shared.dom.worktime.WorkSetting;
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
import nts.uk.ctx.at.shared.dom.worktime.common.RoundingSet;
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
import nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneUse;
import nts.uk.ctx.at.shared.dom.worktime.predset.UseSetting;
import nts.uk.shr.com.time.TimeWithDayAttr;

@RunWith(JMockit.class)
public class FlexWorkSettingTest {
	@Injectable
	private Require require;
	@Test
	public void getters() {
		FlexWorkSetting flexWorkSetting = new FlexWorkSetting(new FlexWorkSettingImpl());
		NtsAssert.invokeGetters(flexWorkSetting);
	}
	
	/**    [prv-3] 休出時の時間帯を作成する        **/
	
	/**
	 * 休出時の時間帯を作成する = empty
	 * 「$.重複の判断処理( $休出の時間帯 ) == 非重複」 がある
	 * 時間帯1:　5:00 -> 10:30
	 * 時間帯2:　13:00 -> 29:00
	 */
	@Test
	public void createWorkOnDayOffTime_empty() {
		//$.重複の判断処理( $休出の時間帯 ) == 非重複
		List<TimezoneUse> timeZones = Arrays.asList(
				    new TimezoneUse(TimeWithDayAttr.hourMinute(  5,  0 ), TimeWithDayAttr.hourMinute( 10, 30 ), UseSetting.USE, 1)
				,	new TimezoneUse(TimeWithDayAttr.hourMinute(  13,  0 ), TimeWithDayAttr.hourMinute( 29,  00), UseSetting.USE, 1)
			);
		
		// 所定時間設定		
		val instance = FlexWorkSettingHelper.PredTimeStg.create(timeZones);
		
		new Expectations() {
			{
				require.getPredetermineTimeSetting((WorkTimeCode)any);
				result = instance;
			}
		};
		
		List<HDWorkTimeSheetSetting> lstWorkTimezone=  new ArrayList<HDWorkTimeSheetSetting>(){{
			HDWorkTimeSheetSettingImpl memento = new HDWorkTimeSheetSettingImpl();
			add(new HDWorkTimeSheetSetting(memento));
			add(new HDWorkTimeSheetSetting(memento));
		}};
		
		val flexWorkSetting = createFlexWorkSetting(lstWorkTimezone);
		// Execute
		@SuppressWarnings("unchecked")
 		val result = (List<ChangeableWorkingTimeZonePerNo>)NtsAssert.Invoke.privateMethod(
				                flexWorkSetting
							,	"createWorkOnDayOffTime"
							,	require
						);
		assertThat( result ).isEmpty();
	}
	
	
	/**
	 * 休出時の時間帯を作成する not empty
	 * 「$.重複の判断処理( $休出の時間帯 ) != 非重複」 
	 * 時間帯1:　8:00 -> 15:00
	 * 時間帯2:　13:00 -> 22:00
	 */
	@Test
	public void createWorkOnDayOffTime() {
		//$.重複の判断処理( $休出の時間帯 ) != 非重複
		List<TimezoneUse> timeZones = Arrays.asList(
				    new TimezoneUse(TimeWithDayAttr.hourMinute(  8,  0 ), TimeWithDayAttr.hourMinute( 15, 00 ), UseSetting.USE, 1)
				,	new TimezoneUse(TimeWithDayAttr.hourMinute(  13,  0 ), TimeWithDayAttr.hourMinute( 22,  00), UseSetting.USE, 1)
			);
		
		// 所定時間設定		
		val instance = FlexWorkSettingHelper.PredTimeStg.create(timeZones);
		
		new Expectations() {
			{
				require.getPredetermineTimeSetting((WorkTimeCode)any);
				result = instance;
			}
		};
		
		List<HDWorkTimeSheetSetting> lstWorkTimezone=  new ArrayList<HDWorkTimeSheetSetting>(){{
			HDWorkTimeSheetSettingImpl memento = new HDWorkTimeSheetSettingImpl();
			add(new HDWorkTimeSheetSetting(memento));
			add(new HDWorkTimeSheetSetting(memento));
		}};
		
		val flexWorkSetting = createFlexWorkSetting(lstWorkTimezone);
		// Execute
		@SuppressWarnings("unchecked")
 		val result = (List<ChangeableWorkingTimeZonePerNo>)NtsAssert.Invoke.privateMethod(
				                flexWorkSetting
							,	"createWorkOnDayOffTime"
							,	require
						);
		
		val timeExcepted = TimeSpanForCalc.join( flexWorkSetting.getOffdayWorkTime().getLstWorkTimezone().stream()
				.map(c -> c.getTimezone().timeSpan())
				.collect(Collectors.toList()));
		
		assertThat( result ).extracting(d -> d.getWorkNo(), d -> d.getForStart(), d -> d.getForEnd())
		                    .containsExactly( Tuple.tuple(new WorkNo(1), timeExcepted.get(), timeExcepted.get()));
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
		val predTimeStg = FlexWorkSettingHelper.PredTimeStg.create(morningEndTime, afternoonStartTime);
		
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
		val predTimeStg = FlexWorkSettingHelper.PredTimeStg.create(morningEndTime, afternoonStartTime);
		
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
	
	private List<OverTimeOfTimeZoneSet> createOverTimeList(List<OverTimeOfTimeZoneSetImpl> overTimeList) {
		
		return overTimeList.stream().map(c -> {return new OverTimeOfTimeZoneSet(c);}).collect(Collectors.toList());
	}
	/**  [prv-1] 指定した午前午後区分の時間帯情報を作成する   **/
	/**
	 * 就業の時間帯 = (5:00, 12:00)
	 * 午前午後区分 = ONE_DAY
	 * コアタイムを使用しない⇒開始/終了が同じ
	 * 結果：$勤務可能時間帯
	 * 
	 */
	@Test
	public void createTimeZoneByAmPmCls() {
		// 休憩時間帯 
		val flexHalfRestTimezone = createRestTimeZone(new FlowRestSetting(new AttendanceTime(120), new AttendanceTime(60))
				,  new FlowRestSetting(new AttendanceTime(240), new AttendanceTime(320)), false);
		
		// 一日の就業時間帯
		val lstWorkingTimeOneDay = Arrays.asList(
				   new EmTimeZoneSet(new EmTimeFrameNo(1)
						   , new TimeZoneRounding(TimeWithDayAttr.hourMinute( 5,  0 ), TimeWithDayAttr.hourMinute( 29, 0 )
						   , new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN)))
						);
		// 残業時間帯
		val oTTimezoneImpls = Arrays.asList(new OverTimeOfTimeZoneSetImpl(new EmTimezoneNo(1), true
				, new TimeZoneRounding(TimeWithDayAttr.hourMinute(5, 0), TimeWithDayAttr.hourMinute(8, 30), null)
				, new OTFrameNo(1), new OTFrameNo(1), new SettlementOrder(1))
				, new OverTimeOfTimeZoneSetImpl(new EmTimezoneNo(2), false
						, new TimeZoneRounding(TimeWithDayAttr.hourMinute(18, 0), TimeWithDayAttr.hourMinute(22, 0), null)
						, new OTFrameNo(2), new OTFrameNo(2), new SettlementOrder(2))
				, new OverTimeOfTimeZoneSetImpl(new EmTimezoneNo(3), false
						, new TimeZoneRounding(TimeWithDayAttr.hourMinute(22, 0), TimeWithDayAttr.hourMinute(29, 0), null)
						, new OTFrameNo(3), new OTFrameNo(3), new SettlementOrder(3)));
		
		val workingTimeOneDay = createWorkTime(AmPmAtr.ONE_DAY, flexHalfRestTimezone, createOverTimeList(oTTimezoneImpls), lstWorkingTimeOneDay);
		
		// 午前の就業時間帯
		val lstWorkingTimeMorning = Arrays.asList(
				   new EmTimeZoneSet(new EmTimeFrameNo(1)
						   , new TimeZoneRounding(TimeWithDayAttr.hourMinute( 5,  0 ), TimeWithDayAttr.hourMinute( 12, 0 )
						   , new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN)))
						);
		val workingTimeMorning = createWorkTime(AmPmAtr.ONE_DAY, flexHalfRestTimezone, createOverTimeList(oTTimezoneImpls), lstWorkingTimeMorning);
		
		// 午後の就業時間帯
		val lstWorkingTimeAfter= Arrays.asList(
				   new EmTimeZoneSet(new EmTimeFrameNo(1)
						   , new TimeZoneRounding(TimeWithDayAttr.hourMinute( 13,  0 ), TimeWithDayAttr.hourMinute( 29, 0 )
						   , new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN)))
						);
		val workingTimeAfter = createWorkTime(AmPmAtr.ONE_DAY, flexHalfRestTimezone, createOverTimeList(oTTimezoneImpls), lstWorkingTimeAfter);
		
		val flexHalfDayWorkTimes = Arrays.asList(workingTimeOneDay, workingTimeMorning, workingTimeAfter);
		
		val fixedRestTimeSet = new TimezoneOfFixedRestTimeSet(Arrays.asList(
				  new DeductionTime(TimeWithDayAttr.hourMinute(12, 00), TimeWithDayAttr.hourMinute(13, 00))
				, new DeductionTime(TimeWithDayAttr.hourMinute(17, 30), TimeWithDayAttr.hourMinute(18, 00))));
		
		val offRestTimeZone = new FlowWorkRestTimezone(true, fixedRestTimeSet, new FlowRestTimezone());
		
		val offdayWorkTimeImpl = new FlexOffdayWorkTimeGetMementoImpl(Arrays.asList(new HDWorkTimeSheetSetting(new HDWorkTimeSheetSettingImpl())), offRestTimeZone);

		// コアタイムを使用しない
		val flexWorkSetting = new FlexWorkSetting(new FlexWorkSettingImpl(ApplyAtr.NOT_USE, flexHalfDayWorkTimes, new FlexOffdayWorkTime(offdayWorkTimeImpl)));

		new MockUp<FlexWorkSetting>() {
	        @Mock
	        private TimeSpanForCalc getCoreTimeByAmPm(WorkSetting.Require require, AmPmAtr ampmAtr) {
	        		return new TimeSpanForCalc(TimeWithDayAttr.hourMinute(10,  0 ), TimeWithDayAttr.hourMinute( 23, 30 ));
	        }
	    };	
		
		// Execute
	    @SuppressWarnings("unchecked")
 		val result = (List<ChangeableWorkingTimeZonePerNo>) NtsAssert.Invoke.privateMethod(
				                flexWorkSetting
							,	"createTimeZoneByAmPmCls"
							,	require
							,   new TimeSpanForCalc(TimeWithDayAttr.hourMinute(10,  0 ), TimeWithDayAttr.hourMinute( 12, 00 ))
							,   AmPmAtr.ONE_DAY
						);
	    Optional<TimeSpanForCalc> excepted = TimeSpanForCalc.join(oTTimezoneImpls.stream().map(c -> {return c.getTimezone().timeSpan();}).collect(Collectors.toList()));
	    assertThat(result.get(0).getWorkNo()).isEqualTo(new WorkNo(1));
	    assertThat(result.get(0).getForStart()).isEqualTo(excepted.get());
	    assertThat(result.get(0).getForEnd()).isEqualTo(excepted.get());
//		
//		assertThat(result.getStart()).isEqualTo(predTimeStg.getPrescribedTimezoneSetting().getAfternoonStartTime());
//		assertThat(result.getEnd()).isEqualTo(flexWorkSetting.getCoreTimeSetting().getCoreTimeSheet().getEndTime());		
		
	}
	
	
	@AllArgsConstructor
	class FlowRestSetImpl implements FlowRestSetGetMemento{

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
	class FlowWorkRestSettingDetailImpl implements FlowWorkRestSettingDetailGetMemento{
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
	class FlowWorkRestSettingGetMementoImpl implements FlowWorkRestSettingGetMemento{
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
	class HDWorkTimeSheetSettingImpl implements HDWorkTimeSheetSettingGetMemento{

		@Override
		public Integer getWorkTimeNo() {
			return 1;
		}

		@Override
		public TimeZoneRounding getTimezone() {
			val timeRoundingSetting =  new TimeRoundingSetting(Unit.ROUNDING_TIME_15MIN, Rounding.ROUNDING_DOWN);
			
			return new TimeZoneRounding(TimeWithDayAttr.hourMinute(5, 0)
					, TimeWithDayAttr.hourMinute(29, 0)
					, timeRoundingSetting);
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
	class FlexOffdayWorkTimeGetMementoImpl implements FlexOffdayWorkTimeGetMemento{
		
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
	
	class IntervalTimeImpl implements IntervalTimeGetMemento{

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
	class IntervalTimeSettingOImpl implements IntervalTimeSettingGetMemento{

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
	class SubHolTransferSetImpl implements SubHolTransferSetGetMemento{
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
	class WorkTimezoneOtherSubHolTimeSetImpl implements WorkTimezoneOtherSubHolTimeSetGetMemento{

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
	class WorkTimezoneMedicalSetImpl implements WorkTimezoneMedicalSetGetMemento{

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
	class WorkTimezoneGoOutSetImpl implements WorkTimezoneGoOutSetGetMemento{
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
	class WorkTimezoneStampSetImpl implements WorkTimezoneStampSetGetMemento{
		@Override
		public List<RoundingSet> getRoundingSet() {
			return Arrays.asList(new RoundingSet());
		}

		@Override
		public List<PrioritySetting> getPrioritySet() {
			return Arrays.asList(new PrioritySetting());
		}
	}
	
	@AllArgsConstructor
	class WorkTimezoneCommonSetImpl  implements WorkTimezoneCommonSetGetMemento{

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
	class OverTimeOfTimeZoneSetImpl implements OverTimeOfTimeZoneSetGetMemento{
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
    class FlexHalfDayWorkTimeImpl implements FlexHalfDayWorkTimeGetMemento{
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
    class FlexWorkSettingImpl implements FlexWorkSettingGetMemento{
		
		private ApplyAtr coreSettingApplyAtr;
		
		private List<FlexHalfDayWorkTime> flexHalfDayWorkTimes;
		
		private FlexOffdayWorkTime flexOffDayWorkTime;

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
			return new CoreTimeSetting(
				      new TimeSheet(new TimeWithDayAttr(4800), new TimeWithDayAttr(9600))
						, coreSettingApplyAtr
						, new AttendanceTime(3000));
					

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
			return false;
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
	
	private List<DeductionTime> createTimeZones(){
		return Arrays.asList(
				  new DeductionTime(TimeWithDayAttr.hourMinute(9, 30), TimeWithDayAttr.hourMinute(9, 0))
				, new DeductionTime(TimeWithDayAttr.hourMinute(16, 30), TimeWithDayAttr.hourMinute(17,30))
		);
	}
	
	private List<FlowRestSetting> createFlowRestSets() {
		List<FlowRestSetting> result = Arrays.asList(
				  new FlowRestSetting(new AttendanceTime(1200), new AttendanceTime(1300))
				, new FlowRestSetting(new AttendanceTime(1730), new AttendanceTime(1800))
			);
		
		return result;
	}
	
	private FlexWorkSetting createFlexWorkSetting(List<HDWorkTimeSheetSetting> lstWorkTimezone) {
		// 休憩時間帯 
		val flexHalfRestTimezone = createRestTimeZone(new FlowRestSetting(new AttendanceTime(120), new AttendanceTime(60))
				,  new FlowRestSetting(new AttendanceTime(240), new AttendanceTime(320)), false);
		
		// 一日の就業時間帯
		val lstWorkingTimeOneDay = Arrays.asList(
				   new EmTimeZoneSet(new EmTimeFrameNo(1)
						   , new TimeZoneRounding(TimeWithDayAttr.hourMinute( 5,  0 ), TimeWithDayAttr.hourMinute( 29, 0 )
						   , new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN)))
						);
		// 残業時間帯
		val oTTimezoneImpls = Arrays.asList(new OverTimeOfTimeZoneSetImpl(new EmTimezoneNo(1), true
				, new TimeZoneRounding(TimeWithDayAttr.hourMinute(5, 0), TimeWithDayAttr.hourMinute(8, 30), null)
				, new OTFrameNo(1), new OTFrameNo(1), new SettlementOrder(1))
				, new OverTimeOfTimeZoneSetImpl(new EmTimezoneNo(2), false
						, new TimeZoneRounding(TimeWithDayAttr.hourMinute(18, 0), TimeWithDayAttr.hourMinute(22, 0), null)
						, new OTFrameNo(2), new OTFrameNo(2), new SettlementOrder(2))
				, new OverTimeOfTimeZoneSetImpl(new EmTimezoneNo(3), false
						, new TimeZoneRounding(TimeWithDayAttr.hourMinute(22, 0), TimeWithDayAttr.hourMinute(29, 0), null)
						, new OTFrameNo(3), new OTFrameNo(3), new SettlementOrder(3)));
		
		val workingTimeOneDay = createWorkTime(AmPmAtr.ONE_DAY, flexHalfRestTimezone, createOverTimeList(oTTimezoneImpls), lstWorkingTimeOneDay);
		
		// 午前の就業時間帯
		val lstWorkingTimeMorning = Arrays.asList(
				   new EmTimeZoneSet(new EmTimeFrameNo(1)
						   , new TimeZoneRounding(TimeWithDayAttr.hourMinute( 5,  0 ), TimeWithDayAttr.hourMinute( 12, 0 )
						   , new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN)))
						);
		val workingTimeMorning = createWorkTime(AmPmAtr.ONE_DAY, flexHalfRestTimezone, createOverTimeList(oTTimezoneImpls), lstWorkingTimeMorning);
		
		// 午後の就業時間帯
		val lstWorkingTimeAfter= Arrays.asList(
				   new EmTimeZoneSet(new EmTimeFrameNo(1)
						   , new TimeZoneRounding(TimeWithDayAttr.hourMinute( 13,  0 ), TimeWithDayAttr.hourMinute( 29, 0 )
						   , new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN)))
						);
		val workingTimeAfter = createWorkTime(AmPmAtr.ONE_DAY, flexHalfRestTimezone, createOverTimeList(oTTimezoneImpls), lstWorkingTimeAfter);
		
		val flexHalfDayWorkTimes = Arrays.asList(workingTimeOneDay, workingTimeMorning, workingTimeAfter);
		
		val fixedRestTimeSet = new TimezoneOfFixedRestTimeSet(Arrays.asList(
				  new DeductionTime(TimeWithDayAttr.hourMinute(12, 00), TimeWithDayAttr.hourMinute(13, 00))
				, new DeductionTime(TimeWithDayAttr.hourMinute(17, 30), TimeWithDayAttr.hourMinute(18, 00))));
		
		val offRestTimeZone = new FlowWorkRestTimezone(true, fixedRestTimeSet, new FlowRestTimezone());
		
		val offdayWorkTimeImpl = new FlexOffdayWorkTimeGetMementoImpl(lstWorkTimezone, offRestTimeZone);

		// コアタイムを使用しない
		val flexWorkSetting = new FlexWorkSetting(new FlexWorkSettingImpl(ApplyAtr.NOT_USE, flexHalfDayWorkTimes, new FlexOffdayWorkTime(offdayWorkTimeImpl)));

		return flexWorkSetting;
	}
	
	private FlowWorkRestTimezone createRestTimeZone(FlowRestSetting flowRestSetting, FlowRestSetting hereAfterRestSet, boolean fixRestTime) {
		val flowRestTimezone = new FlowRestTimezone(Arrays.asList(flowRestSetting), false,  hereAfterRestSet);
		return new FlowWorkRestTimezone(false, new TimezoneOfFixedRestTimeSet(), flowRestTimezone);
	}
	
	
	private FlexHalfDayWorkTime createWorkTime(AmPmAtr amPmAtr, FlowWorkRestTimezone restTimezone
			, List<OverTimeOfTimeZoneSet> lstOTTimezone, List<EmTimeZoneSet> lstWorkingTimezone) {
//		//流動休憩設定1
//		val flowRestSetting1 = new FlowRestSetting(new AttendanceTime(120), new AttendanceTime(60));
//		//設定以降の休憩設定
//		val  hereAfterRestSet = new FlowRestSetting(new AttendanceTime(240), new AttendanceTime(320));
//		//流動休憩時間帯    
//		val flowRestTimezone1 = new FlowRestTimezone(Arrays.asList(flowRestSetting1), false,  hereAfterRestSet);
//		/** 休憩時間帯 */
//		val restTimezone = new FlowWorkRestTimezone(false, new TimezoneOfFixedRestTimeSet(), flowRestTimezone1);
		//勤務時間帯
		val workTimezone = new FixedWorkTimezoneSet();
		
		// 就業時間帯
//		val lstWorkingTimezone = Arrays.asList(
//				   new EmTimeZoneSet(new EmTimeFrameNo(1)
//						   , new TimeZoneRounding(TimeWithDayAttr.hourMinute( 5,  0 ), TimeWithDayAttr.hourMinute( 29, 0 )
//						   , new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN)))
//						);
		
//		// 残業時間帯
//		val oTTimezoneImpl = new OverTimeOfTimeZoneSetImpl(new EmTimezoneNo(1), false, false
//				, new TimeZoneRounding(TimeWithDayAttr.hourMinute(18, 0), TimeWithDayAttr.hourMinute(29, 0),
//						new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN))
//				, new OTFrameNo(1), new OTFrameNo(1), new SettlementOrder(1));

		workTimezone.setLstOTTimezone(lstOTTimezone);
		workTimezone.setLstWorkingTimezone(lstWorkingTimezone);
		
		FlexHalfDayWorkTimeImpl  flexHalfMorningImpl = new FlexHalfDayWorkTimeImpl(restTimezone, workTimezone, amPmAtr);
		
		return new FlexHalfDayWorkTime(flexHalfMorningImpl);
	}
	
//	private FlexHalfDayWorkTime createWorkTimeMorning(AmPmAtr morning) {
//		//流動休憩設定1
//		val flowRestSetting1 = new FlowRestSetting(new AttendanceTime(120), new AttendanceTime(60));
//		//設定以降の休憩設定
//		val  hereAfterRestSet = new FlowRestSetting(new AttendanceTime(240), new AttendanceTime(320));
//		//流動休憩時間帯    
//		val flowRestTimezone1 = new FlowRestTimezone(Arrays.asList(flowRestSetting1), false,  hereAfterRestSet);
//		/** 休憩時間帯 */
//		val restTimezone = new FlowWorkRestTimezone(false, new TimezoneOfFixedRestTimeSet(), flowRestTimezone1);
//		//勤務時間帯
//		val workTimezone = new FixedWorkTimezoneSet();
//		
//		// 就業時間帯
//		val lstWorkingTimezone = Arrays.asList(
//				   new EmTimeZoneSet(new EmTimeFrameNo(1)
//						   , new TimeZoneRounding(TimeWithDayAttr.hourMinute( 5,  0 ), TimeWithDayAttr.hourMinute( 12, 0 )
//						   , new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN)))
//						);
//
//		workTimezone.setLstWorkingTimezone(lstWorkingTimezone);
//		
//		FlexHalfDayWorkTimeImpl  flexHalfMorningImpl = new FlexHalfDayWorkTimeImpl(restTimezone, workTimezone, morning);
//		
//		return new FlexHalfDayWorkTime(flexHalfMorningImpl);
//	}
//	
//	private FlexHalfDayWorkTime createWorkTimeAfterNoon(AmPmAtr afternoon) {
//		//流動休憩設定1
//		val flowRestSetting1 = new FlowRestSetting(new AttendanceTime(120), new AttendanceTime(60));
//		//設定以降の休憩設定
//		val  hereAfterRestSet = new FlowRestSetting(new AttendanceTime(240), new AttendanceTime(320));
//		//流動休憩時間帯    
//		val flowRestTimezone1 = new FlowRestTimezone(Arrays.asList(flowRestSetting1), false,  hereAfterRestSet);
//		/** 休憩時間帯 */
//		val restTimezone = new FlowWorkRestTimezone(false, new TimezoneOfFixedRestTimeSet(), flowRestTimezone1);
//		//勤務時間帯
//		val workTimezone = new FixedWorkTimezoneSet();
//		
//		// 就業時間帯
//		val lstWorkingTimezone = Arrays.asList(
//				   new EmTimeZoneSet(new EmTimeFrameNo(1)
//						   , new TimeZoneRounding(TimeWithDayAttr.hourMinute( 13,  0 ), TimeWithDayAttr.hourMinute( 29, 0 )
//						   , new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN)))
//						);
//
//		workTimezone.setLstWorkingTimezone(lstWorkingTimezone);
//		
//		FlexHalfDayWorkTimeImpl  flexHalfAfterImpl = new FlexHalfDayWorkTimeImpl(restTimezone, workTimezone, afternoon);
//		
//		return new FlexHalfDayWorkTime(flexHalfAfterImpl);
//	}
//	
	
	
	
//	static class Helper {
//		
//		public FlexWorkSetting createFlexWorkSetting() {
//			//val flexWorkSetting = new FlexWorkSetting(new FlexWorkSettingGetMemento());
//			//flexWorkSetting.se
//			val coreTimeSetting = new CoreTimeSetting(
//					  new TimeSheet(new TimeWithDayAttr(4800), new TimeWithDayAttr(9600))
//					, ApplyAtr.USE
//					, new AttendanceTime(3000));
//			val commonRestSetting = new CommonRestSetting(RestTimeOfficeWorkCalcMethod.APPROP_ALL);
//			val flowRestSetting = 
//			
//			
//			//val flowRestSetting = new 
//			//val FlowWorkRestSetting = 
//			
////			return new FlexWorkSetting("cid", new WorkTimeCode("001")
////					, );
//			
//			return null;
//		}
//		
//		
//		
//	}
}
