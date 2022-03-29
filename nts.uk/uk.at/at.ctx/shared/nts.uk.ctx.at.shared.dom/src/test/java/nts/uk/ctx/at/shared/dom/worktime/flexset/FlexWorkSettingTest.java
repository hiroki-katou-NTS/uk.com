package nts.uk.ctx.at.shared.dom.worktime.flexset;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.assertj.core.groups.Tuple;
import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.worktime.ChangeableWorkingTimeZonePerNo;
import nts.uk.ctx.at.shared.dom.worktime.WorkSetting.Require;
import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTime;
import nts.uk.ctx.at.shared.dom.worktime.common.TimezoneOfFixedRestTimeSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingHelper.FlowWkRestTimezoneHelper;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingHelper.OverTimeOfTimeZoneSetHelper;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowRestSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkRestTimezone;
import nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneUse;
import nts.uk.ctx.at.shared.dom.worktime.predset.UseSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.WorkNo;
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
	public void getBreakTimeZone_isFixRestTime_true(@Injectable FlowWorkRestTimezone offRestTimeZone) {

		val fixedRestTimeSet = new TimezoneOfFixedRestTimeSet(Arrays.asList(
					  new DeductionTime(TimeWithDayAttr.hourMinute(12, 00), TimeWithDayAttr.hourMinute(13, 00))
					, new DeductionTime(TimeWithDayAttr.hourMinute(17, 30), TimeWithDayAttr.hourMinute(18, 00))));

		new Expectations(offRestTimeZone) {
			{
				offRestTimeZone.isFixRestTime();
				result = true;

				offRestTimeZone.getFixedRestTimezone();
				result = fixedRestTimeSet;
			}
		};

		val flexWorkSetting = FlexWorkSettingHelper.createFlexOffRestTimeZone(offRestTimeZone);

		val isWorkingOnDayOff = true;

		val result = flexWorkSetting.getBreakTimeZone(isWorkingOnDayOff, AmPmAtr.ONE_DAY);

		assertThat( result.isFixed() ).isTrue();
		assertThat( result.getBreakTimes() ).containsExactlyInAnyOrderElementsOf(fixedRestTimeSet.getRestTimezonesForCalc());
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
	public void getBreakTimeZone_isFixRestTime_false(@Injectable FlowWorkRestTimezone offRestTimeZone) {
		val isWorkingOnDayOff = true;

		new Expectations(offRestTimeZone) {
			{
				offRestTimeZone.isFixRestTime();
				result = false;
			}
		};

		val flexWorkSetting = FlexWorkSettingHelper.createFlexOffRestTimeZone(offRestTimeZone);

		val result = flexWorkSetting.getBreakTimeZone(isWorkingOnDayOff, AmPmAtr.ONE_DAY);
		assertThat( result.isFixed() ).isFalse();
		assertThat( result.getBreakTimes() ).isEmpty();

	}

	/**
	 * 休憩時間帯を取得する
	 * 休出か == FALSE
	 * 午前午後区分 = ONE_DAY
	 * 休憩時間帯を固定にする = TRUE
	 * 期待値：　固定 = TRUE, 休憩時間帯= 「フレックス勤務設定」の「平日勤務時間帯」のONE_DAYの「 休憩時間帯」
	 */
	@Test
	public void getBreakTimeZone_isWorkingOnDayOff_false_isFixRestTime_true(@Injectable FlowWorkRestTimezone flexHalfRestTimezone) {

		val isWorkingOnDayOff = false;

		// 休憩時間帯
		val restTimezone = FlowWkRestTimezoneHelper.createRestTimeZone(new FlowRestSetting(new AttendanceTime(120), new AttendanceTime(60))
				,  new FlowRestSetting(new AttendanceTime(240), new AttendanceTime(320)), false);

		new Expectations(flexHalfRestTimezone) {
			{
				flexHalfRestTimezone.isFixRestTime();
				result = true;

				flexHalfRestTimezone.getFixedRestTimezone();
				result = restTimezone.getFixedRestTimezone();
			}
		};

		val flexWorkSetting = FlexWorkSettingHelper.createFlexHaftRestTimeZone(flexHalfRestTimezone);

		val result = flexWorkSetting.getBreakTimeZone(isWorkingOnDayOff, AmPmAtr.ONE_DAY);

		assertThat( result.isFixed() ).isTrue();

		assertThat( result.getBreakTimes() ).containsExactlyInAnyOrderElementsOf(restTimezone.getFixedRestTimezone().getRestTimezonesForCalc());
	}


	/**
	 * 休憩時間帯を取得する
	 * 休出か == FALSE
	 * 午前午後区分 = ONE_DAY
	 * 休憩時間帯を固定にする = FALSE
	 * 期待値：　固定 = FALSE, 休憩時間帯= empty
	 */
	@Test
	public void getBreakTimeZone_isWorkingOnDayOff_false_isFixRestTime_false(@Injectable FlowWorkRestTimezone flexHalfRestTimezone) {

		val isWorkingOnDayOff = false;

		new Expectations(flexHalfRestTimezone) {
			{
				flexHalfRestTimezone.isFixRestTime();
				result = false;
			}
		};

		val flexWorkSetting = FlexWorkSettingHelper.createFlexHaftRestTimeZone(flexHalfRestTimezone);

		val actual = flexWorkSetting.getBreakTimeZone(isWorkingOnDayOff, AmPmAtr.ONE_DAY);

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
	public void createTimeZoneByAmPmCls_OneDay_CoreTime_Not_Use() {

		// 残業時間帯
		val oTTimezoneImpls = Arrays.asList(
					  OverTimeOfTimeZoneSetHelper.createOverTime(1, true, TimeWithDayAttr.hourMinute(  5, 0), TimeWithDayAttr.hourMinute(8, 30))
					, OverTimeOfTimeZoneSetHelper.createOverTime(2, true, TimeWithDayAttr.hourMinute( 18, 0), TimeWithDayAttr.hourMinute(22, 0))
					, OverTimeOfTimeZoneSetHelper.createOverTime(3, true, TimeWithDayAttr.hourMinute( 22, 0), TimeWithDayAttr.hourMinute(29, 0))
				);

		val flexWorkSetting = FlexWorkSettingHelper.createNotUse(oTTimezoneImpls, AmPmAtr.ONE_DAY);

		// Execute
		@SuppressWarnings("unchecked")
 		val result = (List<ChangeableWorkingTimeZonePerNo>) NtsAssert.Invoke.privateMethod(
								flexWorkSetting
							,	"createTimeZoneByAmPmCls"
							,	require
							,   new TimeSpanForCalc(TimeWithDayAttr.hourMinute(10,  0 ), TimeWithDayAttr.hourMinute( 12, 00 ))
							,   AmPmAtr.ONE_DAY
						);

		// 就業の時間帯 = (10:00, 12:00)ので、$勤務可能時間帯 = 残業時間帯のmin, 残業時間帯のmax
		val excepted = ChangeableWorkingTimeZonePerNo.createAsStartEqualsEnd(
				  new WorkNo(1)
				, new TimeSpanForCalc(TimeWithDayAttr.hourMinute(5,  0 ), TimeWithDayAttr.hourMinute( 29, 00 )));

		assertThat(result.get(0)).isEqualTo(excepted);


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
		// 残業時間帯
		val oTTimezoneImpls = Arrays.asList(
					  OverTimeOfTimeZoneSetHelper.createOverTime(1, true, TimeWithDayAttr.hourMinute(  5, 0), TimeWithDayAttr.hourMinute(8, 30))
					, OverTimeOfTimeZoneSetHelper.createOverTime(2, true, TimeWithDayAttr.hourMinute( 18, 0), TimeWithDayAttr.hourMinute(22, 0))
					, OverTimeOfTimeZoneSetHelper.createOverTime(3, true, TimeWithDayAttr.hourMinute( 22, 0), TimeWithDayAttr.hourMinute(29, 0))
				);

		val flexWorkSetting = FlexWorkSettingHelper.createUse_One_Day(oTTimezoneImpls);

		//コアタイム
		val timeSheet = new TimeSheet(TimeWithDayAttr.hourMinute(8,  0 ), TimeWithDayAttr.hourMinute(16,  0 ));

		// Execute
		@SuppressWarnings("unchecked")
 		val result = (List<ChangeableWorkingTimeZonePerNo>) NtsAssert.Invoke.privateMethod(
								flexWorkSetting
							,	"createTimeZoneByAmPmCls"
							,	require
							,   new TimeSpanForCalc(TimeWithDayAttr.hourMinute(10,  0 ), TimeWithDayAttr.hourMinute( 12, 00 ))
							,   AmPmAtr.ONE_DAY
						);

		// 就業の時間帯 = (10:00, 12:00)ので、時間帯1 = [残業時間帯のmin, コアタイムのstart],
		//							   時間帯2 = [コアタイムのend, 残業時間帯のmax]

		val excepted = ChangeableWorkingTimeZonePerNo.create(
				  new WorkNo(1)
				, new TimeSpanForCalc(TimeWithDayAttr.hourMinute(5,  0 ), timeSheet.getStartTime())
				, new TimeSpanForCalc(timeSheet.getEndTime(), TimeWithDayAttr.hourMinute( 29, 00 ))
				);

		assertThat(result.get(0)).isEqualTo(excepted);

	}

	/**
	 * 就業の時間帯 = (10:00, 12:00)
	 * 午前午後区分 = AM
	 * コアタイムを使用する
	 * コアタイム=(480: 960)
	 * $勤務可能時間帯= (300 ->　1740)
	 * 所定時間設定 = (12:00 -> 13:00)
	 * 結果：	$開始 = 計算時間帯( $勤務可能時間帯.開始時刻, 所定時間設定.開始時刻 )
	 *	  $終了 = 計算時間帯( 所定時間設定	.終了時刻, $勤務可能時間帯.終了時刻 )
	 *
	 */

	@Test
	public void createTimeZoneByAmPmCls_Morning_OneDay_CoreTime_Use() {
		// 残業時間帯
		val oTTimezoneImpls = Arrays.asList(
					  OverTimeOfTimeZoneSetHelper.createOverTime(1, true, TimeWithDayAttr.hourMinute(  5, 0), TimeWithDayAttr.hourMinute(8, 30))
					, OverTimeOfTimeZoneSetHelper.createOverTime(2, true, TimeWithDayAttr.hourMinute( 18, 0), TimeWithDayAttr.hourMinute(22, 0))
					, OverTimeOfTimeZoneSetHelper.createOverTime(3, true, TimeWithDayAttr.hourMinute( 22, 0), TimeWithDayAttr.hourMinute(29, 0))
				);

		val flexWorkSetting = FlexWorkSettingHelper.createUse_One_Day(oTTimezoneImpls);
		//コアタイム
		val timeSheet = new TimeSheet(TimeWithDayAttr.hourMinute(8,  0 ), TimeWithDayAttr.hourMinute(16,  0 ));

		val morningEndTime = TimeWithDayAttr.hourMinute( 12,  0 );
		val afternoonStartTime = TimeWithDayAttr.hourMinute( 13,  0 );

		// 所定時間設定
		val predTimeStg = FlexWorkSettingHelper.PredTimeStg.create(morningEndTime, afternoonStartTime);

		new Expectations() {
			{
				require.predetemineTimeSetting(anyString, (WorkTimeCode) any);
				result = Optional.of(predTimeStg);
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

		// 就業の時間帯 = (10:00, 12:00)ので、コアタイム=コアタイムのstart, 所定時間設定のmorningEndTime
		// 時間帯1 = [残業時間帯のmin, コアタイムのstart],
		// 時間帯2 = [コアタイムのend, 残業時間帯のmax]

		val excepted = ChangeableWorkingTimeZonePerNo.create(
				  new WorkNo(1)
				, new TimeSpanForCalc(TimeWithDayAttr.hourMinute(5,  0 ), timeSheet.getStartTime())
				, new TimeSpanForCalc(morningEndTime, TimeWithDayAttr.hourMinute( 29, 00 ))
				);

		assertThat(result.get(0)).isEqualTo(excepted);

	}

	/**
	 * 就業の時間帯 = (10:00, 12:00)
	 * 午前午後区分 = PM
	 * コアタイムを使用する
	 * 結果：	$開始 = 計算時間帯( $勤務可能時間帯.開始時刻, $コアタイム.開始時刻 )
	 *	  $終了 = 計算時間帯( $コアタイム.終了時刻, $勤務可能時間帯.終了時刻 )
	 *
	 */

	@Test
	public void createTimeZoneByAmPmCls_After_OneDay_CoreTime_Use() {
		// 残業時間帯
		val oTTimezoneImpls = Arrays.asList(
					  OverTimeOfTimeZoneSetHelper.createOverTime(1, true, TimeWithDayAttr.hourMinute(  5, 0), TimeWithDayAttr.hourMinute(8, 30))
					, OverTimeOfTimeZoneSetHelper.createOverTime(2, true, TimeWithDayAttr.hourMinute( 18, 0), TimeWithDayAttr.hourMinute(22, 0))
					, OverTimeOfTimeZoneSetHelper.createOverTime(3, true, TimeWithDayAttr.hourMinute( 22, 0), TimeWithDayAttr.hourMinute(29, 0))
				);

		val flexWorkSetting = FlexWorkSettingHelper.createUse_PM(oTTimezoneImpls);

		val morningEndTime = TimeWithDayAttr.hourMinute( 12,  0 );
		val afternoonStartTime = TimeWithDayAttr.hourMinute( 13,  0 );

		//コアタイム
		val timeSheet = new TimeSheet(TimeWithDayAttr.hourMinute(8,  0 ), TimeWithDayAttr.hourMinute(16,  0 ));

		// 所定時間設定
		val predTimeStg = FlexWorkSettingHelper.PredTimeStg.create(morningEndTime, afternoonStartTime);

		new Expectations() {
			{
				require.predetemineTimeSetting(anyString, (WorkTimeCode) any);
				result = Optional.of(predTimeStg);
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

		// 就業の時間帯 = (10:00, 12:00)ので、コアタイム= 所定時間設定のafternoonStartTime, コアタイムのend,
		// 時間帯1 = [残業時間帯のmin, コアタイムのstart],
		// 時間帯2 = [コアタイムのend, 残業時間帯のmax]
		val excepted = ChangeableWorkingTimeZonePerNo.create(
				  new WorkNo(1)
				, new TimeSpanForCalc(TimeWithDayAttr.hourMinute(5,  0 ), afternoonStartTime)
				, new TimeSpanForCalc(timeSheet.getEndTime(), TimeWithDayAttr.hourMinute( 29, 00 ))
				);

		assertThat(result.get(0)).isEqualTo(excepted);

	}


	/**
	 * [prv-3]休出時の時間帯を作成する not empty
	 * 「$.重複の判断処理( $休出の時間帯 ) != 非重複」
	 *  所定時間設定	時間帯1:　8:00 -> 15:00, 時間帯2:　13:00 -> 22:00
	 *  勤務時間帯		時間帯1:　8:00 -> 12:00, 時間帯2:　13:00 -> 18:00
	 *  期待値：　8:00　-> 18:00
	 */
	@Test
	public void createWorkOnDayOffTime() {
		val lstWorkTimezone=  Arrays.asList(
				   FlexWorkSettingHelper.createHdWorkTimeSheet(TimeWithDayAttr.hourMinute(8, 00), TimeWithDayAttr.hourMinute(12, 00))
				 , FlexWorkSettingHelper.createHdWorkTimeSheet(TimeWithDayAttr.hourMinute(13, 00), TimeWithDayAttr.hourMinute(18, 00))
				);

		val flexWorkSetting = FlexWorkSettingHelper.createFlexWorkSetting(lstWorkTimezone);

		List<TimezoneUse> timeZones = Arrays.asList(
					new TimezoneUse(TimeWithDayAttr.hourMinute(  8,  0 ), TimeWithDayAttr.hourMinute( 15, 00 ), UseSetting.USE, 1)
				,	new TimezoneUse(TimeWithDayAttr.hourMinute(  13,  0 ), TimeWithDayAttr.hourMinute( 22,  00), UseSetting.USE, 1)
			);

		// 所定時間設定
		val instance = FlexWorkSettingHelper.PredTimeStg.create(timeZones);

		new Expectations() {
			{
				require.predetemineTimeSetting(anyString, (WorkTimeCode)any);
				result = Optional.of(instance);
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
									, TimeWithDayAttr.hourMinute(8, 00)  //lstWorkTimezoneのmin
									, TimeWithDayAttr.hourMinute(18, 00) //lstWorkTimezoneのmax
									, TimeWithDayAttr.hourMinute(8, 00)  //lstWorkTimezoneのmin
									, TimeWithDayAttr.hourMinute(18, 00))); //lstWorkTimezoneのmax
	}

}
