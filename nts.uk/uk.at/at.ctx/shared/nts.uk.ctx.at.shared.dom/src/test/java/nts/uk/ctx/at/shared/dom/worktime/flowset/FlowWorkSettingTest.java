package nts.uk.ctx.at.shared.dom.worktime.flowset;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.assertj.core.groups.Tuple;
import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.AllArgsConstructor;
import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.WorkNo;
import nts.uk.ctx.at.shared.dom.worktime.WorkSetting.Require;
import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTime;
import nts.uk.ctx.at.shared.dom.worktime.common.LegalOTSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.TimezoneOfFixedRestTimeSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetermineTime;
import nts.uk.ctx.at.shared.dom.worktime.predset.PrescribedTimezoneSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneUse;
import nts.uk.ctx.at.shared.dom.worktime.predset.UseSetting;
import nts.uk.shr.com.time.TimeWithDayAttr;

@RunWith(JMockit.class)
public class FlowWorkSettingTest {
	@Injectable
	private Require require;
	
	@Test
	public void getters(@Injectable FlowWorkSetting flowWorkSetting) {
		NtsAssert.invokeGetters(flowWorkSetting);
	}
	
	/**
	 * 休出か = false
	 * 休憩時間帯を固定にする = false
	 * 期待値：休憩時間帯を固定にする = false, 休憩時間帯 = empty
	 */
	@Test
	public void getBreakTimeZone_empty(@Injectable FlowRestTimezone flowRestTimezone, @Injectable FlowOffdayWorkTimezone offdayWTz) {
		val halfFixedRts = new TimezoneOfFixedRestTimeSet(Arrays.asList(
				  new DeductionTime(TimeWithDayAttr.hourMinute(12, 00), TimeWithDayAttr.hourMinute(12, 01))
				, new DeductionTime(TimeWithDayAttr.hourMinute(12, 02), TimeWithDayAttr.hourMinute(12, 03))
				, new DeductionTime(TimeWithDayAttr.hourMinute(12, 04), TimeWithDayAttr.hourMinute(12, 05))
				, new DeductionTime(TimeWithDayAttr.hourMinute(12, 06), TimeWithDayAttr.hourMinute(12, 07))
				));
		
		//休憩時間帯を固定にする = false
		val flexHalfRTz = new FlowWorkRestTimezone(false, halfFixedRts, flowRestTimezone);
		
		//平日勤務時間帯
		val halfDayWtz = new FlowHalfDayWorkTimezone(new FlowHalfDayWtzImpl(flexHalfRTz));
		
		val flowWS = new FlowWorkSetting(new FlowWorkSettingImpl(halfDayWtz, offdayWTz));
		
		val actual = flowWS.getBreakTimeZone(false, AmPmAtr.ONE_DAY);
		
		assertThat( actual.isFixed() ).isFalse();
		assertThat( actual.getBreakTimes() ).isEmpty();
		
	}
	
	/**
	 * 休出か = false
	 * 休憩時間帯を固定にする = true
	 * 期待値：休憩時間帯を固定にする = true, 休憩時間帯 = @平日勤務時間帯	.固定休憩時間帯.休憩時間帯を取得()
	 */
	@Test
	public void getBreakTimeZone_halfFixedRts(@Injectable FlowRestTimezone flowRestTimezone, @Injectable FlowOffdayWorkTimezone offdayWTz) {
		val halfFixedRts = new TimezoneOfFixedRestTimeSet(Arrays.asList(
				  new DeductionTime(TimeWithDayAttr.hourMinute(12, 00), TimeWithDayAttr.hourMinute(12, 01))
				, new DeductionTime(TimeWithDayAttr.hourMinute(12, 02), TimeWithDayAttr.hourMinute(12, 03))
				, new DeductionTime(TimeWithDayAttr.hourMinute(12, 04), TimeWithDayAttr.hourMinute(12, 05))
				, new DeductionTime(TimeWithDayAttr.hourMinute(12, 06), TimeWithDayAttr.hourMinute(12, 07))
				));
		
		//休憩時間帯を固定にする = true
		val flexHalfRTz = new FlowWorkRestTimezone(true, halfFixedRts, flowRestTimezone);
		
		//平日勤務時間帯
		val halfDayWtz = new FlowHalfDayWorkTimezone(new FlowHalfDayWtzImpl(flexHalfRTz));
		
		val flowWS = new FlowWorkSetting(new FlowWorkSettingImpl(halfDayWtz, offdayWTz));
		
		val actual = flowWS.getBreakTimeZone(false, AmPmAtr.ONE_DAY);
		
		assertThat( actual.isFixed() ).isTrue();
		assertThat( actual.getBreakTimes() ).containsExactlyInAnyOrderElementsOf(halfFixedRts.getRestTimezonesForCalc());
		
	}
	
	/**
	 * 休出か = true
	 * 休憩時間帯を固定にする = false
	 * 期待値：休憩時間帯を固定にする = false, 休憩時間帯 = empty
	 */
	@Test
	public void getBreakTimeZone_empty_1(@Injectable FlowRestTimezone flowRestTimezone, @Injectable FlowHalfDayWorkTimezone halfDayWtz) {
		val offFixedRts = new TimezoneOfFixedRestTimeSet(Arrays.asList(
				  new DeductionTime(TimeWithDayAttr.hourMinute(12, 00), TimeWithDayAttr.hourMinute(12, 01))
				, new DeductionTime(TimeWithDayAttr.hourMinute(12, 02), TimeWithDayAttr.hourMinute(12, 03))
				, new DeductionTime(TimeWithDayAttr.hourMinute(12, 04), TimeWithDayAttr.hourMinute(12, 05))
				, new DeductionTime(TimeWithDayAttr.hourMinute(12, 06), TimeWithDayAttr.hourMinute(12, 07))
				));
		
		//休憩時間帯を固定にする = false
		val offRTz = new FlowWorkRestTimezone(false, offFixedRts, flowRestTimezone);
		// 休日勤務時間帯
		val offdayWTz = new FlowOffdayWorkTimezone(new FlowOffdayWtzImpl(offRTz));
		
		val flowWS = new FlowWorkSetting(new FlowWorkSettingImpl(halfDayWtz, offdayWTz));
		
		//休出か = true
		val actual = flowWS.getBreakTimeZone(true, AmPmAtr.ONE_DAY);
		
		assertThat( actual.isFixed() ).isFalse();
		assertThat( actual.getBreakTimes() ).isEmpty();
		
	}
	
	
	/**
	 * 休出か = true
	 * 休憩時間帯を固定にする = true
	 * 期待値：休憩時間帯を固定にする = true, 休憩時間帯 = @休日勤務時間帯	.固定休憩時間帯.休憩時間帯を取得()
	 */
	@Test
	public void getBreakTimeZone_offFixedRts(@Injectable FlowRestTimezone flowRestTimezone, @Injectable FlowHalfDayWorkTimezone halfDayWtz) {
		val offFixedRts = new TimezoneOfFixedRestTimeSet(Arrays.asList(
				  new DeductionTime(TimeWithDayAttr.hourMinute(12, 00), TimeWithDayAttr.hourMinute(12, 01))
				, new DeductionTime(TimeWithDayAttr.hourMinute(12, 02), TimeWithDayAttr.hourMinute(12, 03))
				, new DeductionTime(TimeWithDayAttr.hourMinute(12, 04), TimeWithDayAttr.hourMinute(12, 05))
				, new DeductionTime(TimeWithDayAttr.hourMinute(12, 06), TimeWithDayAttr.hourMinute(12, 07))
				));
		
		//休憩時間帯を固定にする = true
		val offRTz = new FlowWorkRestTimezone(true, offFixedRts, flowRestTimezone);
		// 休日勤務時間帯
		val offdayWTz = new FlowOffdayWorkTimezone(new FlowOffdayWtzImpl(offRTz));
		
		val flowWS = new FlowWorkSetting(new FlowWorkSettingImpl(halfDayWtz, offdayWTz));
		
		//休出か = true
		val actual = flowWS.getBreakTimeZone(true, AmPmAtr.ONE_DAY);
		
		assertThat( actual.isFixed() ).isTrue();
		assertThat( actual.getBreakTimes() ).containsExactlyInAnyOrderElementsOf(offFixedRts.getRestTimezonesForCalc());
		
	}
	
	/**
	 * 所定時間設定.2回勤務か = FALSE
	 * 期待値：
	 */
	@Test
	public void getChangeableWorkingTimeZone_Shift_1(@Injectable PredetermineTime preTime
			, @Injectable FlowHalfDayWorkTimezone halfDayWtz
			, @Injectable FlowOffdayWorkTimezone offdayWTz) {
		
		val flowSetting = new FlowWorkSetting(new FlowWorkSettingImpl(halfDayWtz, offdayWTz));
		
		List<TimezoneUse> timeZones = Arrays.asList(
			    new TimezoneUse(TimeWithDayAttr.hourMinute(  8,  0 ), TimeWithDayAttr.hourMinute( 15, 00 ), UseSetting.USE, 1)
			,	new TimezoneUse(TimeWithDayAttr.hourMinute(  13,  0 ), TimeWithDayAttr.hourMinute( 22,  00), UseSetting.NOT_USE, 2)
		);
		
		PrescribedTimezoneSetting preTzSetting = Helper.PredTimeStg
				.createPrscTzStg(TimeWithDayAttr.hourMinute( 12,  0 ), TimeWithDayAttr.hourMinute( 13,  0 ), timeZones);
		
		// 所定時間設定		
		val predTimeStg = Helper.PreTimeSetting.create("001", TimeWithDayAttr.hourMinute( 5, 0 ), Helper.Time.toAtdTime(25, 15), preTzSetting, preTime);
		
		new Expectations() {
			{
				require.getPredetermineTimeSetting((WorkTimeCode) any);
				result = predTimeStg;
			}
		};
		
		val result = flowSetting.getChangeableWorkingTimeZone(require);
		
		assertThat( result.getForWholeDay() )
		.extracting(
			  d -> d.getWorkNo()
			, d -> d.getForStart().getStart().v()
			, d -> d.getForStart().getEnd().v()
			, d -> d.getForEnd().getStart().v()
			, d -> d.getForEnd().getEnd().v())
        .containsExactly( 
        		Tuple.tuple(
        				new WorkNo(1) 
        				, predTimeStg.getStartDateClock().v()
        				, predTimeStg.getEndDateClock().v()
        				, predTimeStg.getStartDateClock().v()
        				, predTimeStg.getEndDateClock().v()));
		
		assertThat( result.getForAm())
		.extracting(
			  d -> d.getWorkNo()
			, d -> d.getForStart().getStart().v()
			, d -> d.getForStart().getEnd().v()
			, d -> d.getForEnd().getStart().v()
			, d -> d.getForEnd().getEnd().v())
        .containsExactly( 
        		Tuple.tuple(
        				new WorkNo(1) 
        				, predTimeStg.getStartDateClock().v()
        				, predTimeStg.getPrescribedTimezoneSetting().getMorningEndTime().v()
          				, predTimeStg.getStartDateClock().v()
        				, predTimeStg.getPrescribedTimezoneSetting().getMorningEndTime().v()
        				)
        		);
		
		assertThat( result.getForPm())
		.extracting(
			  d -> d.getWorkNo()
			, d -> d.getForStart().getStart().v()
			, d -> d.getForStart().getEnd().v()
			, d -> d.getForEnd().getStart().v()
			, d -> d.getForEnd().getEnd().v())
        .containsExactly( 
        		Tuple.tuple(
        				new WorkNo(1) 
        				, predTimeStg.getPrescribedTimezoneSetting().getAfternoonStartTime().v()
        				, predTimeStg.getEndDateClock().v()
          				, predTimeStg.getPrescribedTimezoneSetting().getAfternoonStartTime().v()
        				, predTimeStg.getEndDateClock().v()
        				)
        		);
		
		assertThat( result.getForWorkOnDayOff())
		.extracting(
			  d -> d.getWorkNo()
			, d -> d.getForStart().getStart().v()
			, d -> d.getForStart().getEnd().v()
			, d -> d.getForEnd().getStart().v()
			, d -> d.getForEnd().getEnd().v())
        .containsExactly( 
        		Tuple.tuple(
        				new WorkNo(1) 
        				, predTimeStg.getStartDateClock().v()
        				, predTimeStg.getEndDateClock().v()
        				, predTimeStg.getStartDateClock().v()
        				, predTimeStg.getEndDateClock().v()
        				));
	}

	/**
	 * 所定時間設定.2回勤務か = TRUE
	 * 期待値：
	 */
	@Test
	public void getChangeableWorkingTimeZone_Shift_2(@Injectable PredetermineTime preTime
			, @Injectable FlowHalfDayWorkTimezone halfDayWtz
			, @Injectable FlowOffdayWorkTimezone offdayWTz) {
		
		val flowSetting = new FlowWorkSetting(new FlowWorkSettingImpl(halfDayWtz, offdayWTz));
		
		List<TimezoneUse> timeZones = Arrays.asList(
			    new TimezoneUse(TimeWithDayAttr.hourMinute(  8,  0 ), TimeWithDayAttr.hourMinute( 15, 00 ), UseSetting.NOT_USE, 1)
			,	new TimezoneUse(TimeWithDayAttr.hourMinute(  5,  0 ), TimeWithDayAttr.hourMinute( 23,  00), UseSetting.USE, 2)
		);
		
		PrescribedTimezoneSetting preTzSetting = Helper.PredTimeStg
				.createPrscTzStg(TimeWithDayAttr.hourMinute( 12,  0 ), TimeWithDayAttr.hourMinute( 13,  0 ), timeZones);
		
		// 所定時間設定		
		val predTimeStg = Helper.PreTimeSetting.create("001", TimeWithDayAttr.hourMinute( 5, 0 ), Helper.Time.toAtdTime(25, 15), preTzSetting, preTime);
		
		new Expectations() {
			{
				require.getPredetermineTimeSetting((WorkTimeCode) any);
				result = predTimeStg;
			}
		};
		
		val result = flowSetting.getChangeableWorkingTimeZone(require);
		
	    /**  1勤目, 2勤目	*/
		assertThat( result.getForWholeDay())
		.extracting(
			  d -> d.getWorkNo()
			, d -> d.getForStart().getStart().v()
			, d -> d.getForStart().getEnd().v()
			, d -> d.getForEnd().getStart().v()
			, d -> d.getForEnd().getEnd().v())
        .containsExactly( 
        		Tuple.tuple(
        				new WorkNo(1) 
        				, predTimeStg.getStartDateClock().v()
        				, predTimeStg.getEndDateClock().v()
        				, predTimeStg.getStartDateClock().v()
        				, predTimeStg.getEndDateClock().v())
        	  , Tuple.tuple(
    				new WorkNo(2) 
    				, predTimeStg.getStartDateClock().v()
    				, predTimeStg.getEndDateClock().v()
    				, predTimeStg.getStartDateClock().v()
    				, predTimeStg.getEndDateClock().v())	
        		
        		);
		
		assertThat( result.getForAm())
		.extracting(
			  d -> d.getWorkNo()
			, d -> d.getForStart().getStart().v()
			, d -> d.getForStart().getEnd().v()
			, d -> d.getForEnd().getStart().v()
			, d -> d.getForEnd().getEnd().v())
        .containsExactly( 
        		 Tuple.tuple(
        				new WorkNo(1) 
        				, predTimeStg.getStartDateClock().v()
        				, predTimeStg.getPrescribedTimezoneSetting().getMorningEndTime().v()
          				, predTimeStg.getStartDateClock().v()
        				, predTimeStg.getPrescribedTimezoneSetting().getMorningEndTime().v()
        				)
        		, Tuple.tuple(
        				new WorkNo(2) 
        				, predTimeStg.getStartDateClock().v()
        				, predTimeStg.getPrescribedTimezoneSetting().getMorningEndTime().v()
          				, predTimeStg.getStartDateClock().v()
        				, predTimeStg.getPrescribedTimezoneSetting().getMorningEndTime().v()
        				)
        		);
		
		assertThat( result.getForPm().get(0) )
		.extracting(
			  d -> d.getWorkNo()
			, d -> d.getForStart().getStart().v()
			, d -> d.getForStart().getEnd().v()
			, d -> d.getForEnd().getStart().v()
			, d -> d.getForEnd().getEnd().v()
			)
        .containsExactly( 
        		  Tuple.tuple(
        				new WorkNo(1) 
        				, predTimeStg.getPrescribedTimezoneSetting().getAfternoonStartTime().v()
        				, predTimeStg.getEndDateClock().v()
          				, predTimeStg.getPrescribedTimezoneSetting().getAfternoonStartTime().v()
        				, predTimeStg.getEndDateClock().v()
        				)
        		,  Tuple.tuple(
        				new WorkNo(2) 
        				, predTimeStg.getPrescribedTimezoneSetting().getAfternoonStartTime().v()
        				, predTimeStg.getEndDateClock().v()
          				, predTimeStg.getPrescribedTimezoneSetting().getAfternoonStartTime().v()
        				, predTimeStg.getEndDateClock().v()
        				)
        		);
		
		assertThat( result.getForWorkOnDayOff().get(0))
		.extracting(
			  d -> d.getWorkNo()
			, d -> d.getForStart().getStart().v()
			, d -> d.getForStart().getEnd().v()
			, d -> d.getForEnd().getStart().v()
			, d -> d.getForEnd().getEnd().v()
			)
        .containsExactly( 
        		  Tuple.tuple(
        				new WorkNo(1) 
        				, predTimeStg.getStartDateClock().v()
        				, predTimeStg.getEndDateClock().v()
        				, predTimeStg.getStartDateClock().v()
        				, predTimeStg.getEndDateClock().v()
        				)
        		, Tuple.tuple(
        				new WorkNo(2) 
        				, predTimeStg.getStartDateClock().v()
        				, predTimeStg.getEndDateClock().v()
        				, predTimeStg.getStartDateClock().v()
        				, predTimeStg.getEndDateClock().v()
        				)
        		);
	}
	
	
	
	@AllArgsConstructor
	static class FlowOffdayWtzImpl implements FlowOffdayWtzGetMemento{

		private FlowWorkRestTimezone restTimeZone;
		
		@Override
		public FlowWorkRestTimezone getRestTimeZone() {
			return this.restTimeZone;
		}

		@Override
		public List<FlowWorkHolidayTimeZone> getLstWorkTimezone() {
			
			return Arrays.asList(new FlowWorkHolidayTimeZone() );
		}
		
		
	}
	
	@AllArgsConstructor
	static class FlowHalfDayWtzImpl implements FlowHalfDayWtzGetMemento{
		
		private FlowWorkRestTimezone restTimezone;

		@Override
		public FlowWorkRestTimezone getRestTimezone() {
			return this.restTimezone;
		}

		@Override
		public FlowWorkTimezoneSetting getWorkTimeZone() {
			return new FlowWorkTimezoneSetting();
		}
		
		
	}
	
	@AllArgsConstructor
	static class FlowWorkSettingImpl implements FlowWorkSettingGetMemento{
		// 平日勤務時間帯
		private FlowHalfDayWorkTimezone halfDayWorkTimezone;
		
		// 休日勤務時間帯
		private FlowOffdayWorkTimezone flowOffDayWorkTime;

		@Override
		public String getCompanyId() {
			return "cid";
		}

		@Override
		public WorkTimeCode getWorkingCode() {
			return new WorkTimeCode("001");
		}

		@Override
		public FlowWorkRestSetting getRestSetting() {
			return new FlowWorkRestSetting();
		}

		@Override
		public FlowOffdayWorkTimezone getOffdayWorkTimezone() {
			return this.flowOffDayWorkTime;
		}

		@Override
		public WorkTimezoneCommonSet getCommonSetting() {
			return new WorkTimezoneCommonSet();
		}

		@Override
		public FlowHalfDayWorkTimezone getHalfDayWorkTimezone() {
			return this.halfDayWorkTimezone;
		}

		@Override
		public FlowStampReflectTimezone getStampReflectTimezone() {
			return new FlowStampReflectTimezone();
		}

		@Override
		public LegalOTSetting getLegalOTSetting() {
			return LegalOTSetting.LEGAL_INTERNAL_TIME;
		}

		@Override
		public FlowWorkDedicateSetting getFlowSetting() {
			return new FlowWorkDedicateSetting();
		}
		
	}
	
	static class Helper{
		
		static class PreTimeSetting{
			
			/**
			 * 所定時間設定を作成する
			 * @param code 就業時間帯コード
			 * @param startClock 1日の開始時刻
			 * @param rangeOfDay 1日の範囲(時間)
			 * @param prscTzStg 所定時間帯設定
			 * @return 所定時間設定
			 */
			public static PredetemineTimeSetting create(String code, TimeWithDayAttr startClock
					, AttendanceTime rangeOfDay, PrescribedTimezoneSetting prscTzStg
					, @Injectable PredetermineTime preTime) {
				return new PredetemineTimeSetting("CID", rangeOfDay, new WorkTimeCode(code), preTime, false, prscTzStg, startClock, false);
			}
		}
		
		public static class PredTimeStg {

			/**
			 * 所定時間帯設定(DUMMY)を作成する
			 * @return 所定時間帯設定(DUMMY)
			 */
			public static PrescribedTimezoneSetting createPrscTzStg(TimeWithDayAttr morningEndTime, TimeWithDayAttr afternoonStartTime, List<TimezoneUse> lstTimezone) {
				return new PrescribedTimezoneSetting( morningEndTime, afternoonStartTime, lstTimezone);
			}
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

			public static List<TimezoneUse> createDummyList() {
				return Arrays.asList(
								Timezone.createUsed(1, new TimeSpanForCalc(TimeWithDayAttr.hourMinute(  5,  0 ), TimeWithDayAttr.hourMinute( 23, 30 )))
							,	Timezone.createUnused(2)
						);
			}
		}
		
		
		
		
		
		
	}
	
}
