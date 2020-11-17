package nts.uk.ctx.at.shared.dom.worktime.flexset;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
import nts.uk.ctx.at.shared.dom.common.usecls.ApplyAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.WorkNo;
import nts.uk.ctx.at.shared.dom.worktime.ChangeableWorkingTimeZonePerNo;
import nts.uk.ctx.at.shared.dom.worktime.WorkSetting.Require;
import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTime;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.HDWorkTimeSheetSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.TimezoneOfFixedRestTimeSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingHelper.EmTimeZoneSetHelper;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingHelper.FlexHalfDayWorkTimeHelper;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingHelper.FlexOffdayWorkTimeGetMementoImpl;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingHelper.FlexWorkSettingImpl;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingHelper.FlowWkRestTimezoneHelper;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingHelper.HDWorkTimeSheetSettingImpl;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingHelper.OverTimeOfTimeZoneSetHelper;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowRestSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowRestTimezone;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkRestTimezone;
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
	public void getBreakTimeZone_isFixRestTime_true(@Injectable FlowWorkRestTimezone offRestTimeZone) {
			val fixedRestTimeSet = new TimezoneOfFixedRestTimeSet(Arrays.asList(
					  new DeductionTime(TimeWithDayAttr.hourMinute(12, 00), TimeWithDayAttr.hourMinute(13, 00))
					, new DeductionTime(TimeWithDayAttr.hourMinute(17, 30), TimeWithDayAttr.hourMinute(18, 00))));
			
			val flexWorkSetting = FlexWorkSettingHelper.createFlexOffRestTimeZone(offRestTimeZone);
			
			val isWorkingOnDayOff = true;
			
			new Expectations(offRestTimeZone) {
				{
					offRestTimeZone.isFixRestTime();
					result = true;
					
					offRestTimeZone.getFixedRestTimezone();
					result = fixedRestTimeSet;
				}
			};
			
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
		
		val flexWorkSetting = FlexWorkSettingHelper.createFlexOffRestTimeZone(offRestTimeZone);
		
		val isWorkingOnDayOff = true;
		
		new Expectations(offRestTimeZone) {
			{
				offRestTimeZone.isFixRestTime();
				result = false;
			}
		};

			val actual = flexWorkSetting.getBreakTimeZone(isWorkingOnDayOff, AmPmAtr.ONE_DAY);
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
	public void getBreakTimeZone_isWorkingOnDayOff_false_isFixRestTime_true(@Injectable FlowWorkRestTimezone flexHalfRestTimezone) {
		
		val isWorkingOnDayOff = false;
		
		// 休憩時間帯 
		val restTimezone = FlowWkRestTimezoneHelper.createRestTimeZone(new FlowRestSetting(new AttendanceTime(120), new AttendanceTime(60))
				,  new FlowRestSetting(new AttendanceTime(240), new AttendanceTime(320)), false);
		
		val flexWorkSetting = FlexWorkSettingHelper.createFlexHaftRestTimeZone(flexHalfRestTimezone);
		
		new Expectations(flexHalfRestTimezone) {
			{
				flexHalfRestTimezone.isFixRestTime();
				result = true;
				
				flexHalfRestTimezone.getFixedRestTimezone();
				result = restTimezone.getFixedRestTimezone();
			}
		};
		
		val actual = flexWorkSetting.getBreakTimeZone(isWorkingOnDayOff, AmPmAtr.ONE_DAY);
		
		assertThat( actual.isFixed() ).isTrue();
		
		assertThat( actual.getBreakTimes() ).containsExactlyInAnyOrderElementsOf(restTimezone.getFixedRestTimezone().getRestTimezonesForCalc());
	}
	

	/**
	 * 休憩時間帯を取得する
	 * 休出か == FALSE
	 * 午前午後区分 = ONE_DAY
	 * 休憩時間帯を固定にする = FALSE
	 * 期待値：　固定 = TRUE, 休憩時間帯= empty
	 */
	@Test
	public void getBreakTimeZone_isWorkingOnDayOff_false_isFixRestTime_false(@Injectable FlowWorkRestTimezone flexHalfRestTimezone) {
		
		val isWorkingOnDayOff = false;
		
		val flexWorkSetting = FlexWorkSettingHelper.createFlexHaftRestTimeZone(flexHalfRestTimezone);
		
		new Expectations(flexHalfRestTimezone) {
			{
				flexHalfRestTimezone.isFixRestTime();
				result = false;
			}
		};

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
		val predTimeStg = FlexWorkSettingHelper.PredTimeStg.create(morningEndTime, afternoonStartTime);
		
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
		val predTimeStg = FlexWorkSettingHelper.PredTimeStg.create(morningEndTime, afternoonStartTime);
		
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
		val predTimeStg = FlexWorkSettingHelper.PredTimeStg.create(morningEndTime, afternoonStartTime);
		
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
		
		val flexWorkSetting = FlexWorkSettingHelper.createFlexWorkSetting(lstWorkTimezone);
		
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


	

	
	


}
