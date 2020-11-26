package nts.uk.ctx.at.shared.dom;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.WorkInformation.Require;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.WorkNo;
import nts.uk.ctx.at.shared.dom.workrule.BreakTimeZone;
import nts.uk.ctx.at.shared.dom.workrule.ErrorStatusWorkInfo;
import nts.uk.ctx.at.shared.dom.worktime.ChangeableWorkingTimeZone;
import nts.uk.ctx.at.shared.dom.worktime.ChangeableWorkingTimeZonePerNo;
import nts.uk.ctx.at.shared.dom.worktime.ChangeableWorkingTimeZonePerNo.ClockAreaAtr;
import nts.uk.ctx.at.shared.dom.worktime.WorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.AbolishAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneUse;
import nts.uk.ctx.at.shared.dom.worktime.predset.UseSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.AttendanceDayAttr;
import nts.uk.ctx.at.shared.dom.worktype.DeprecateClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.shr.com.time.TimeWithDayAttr;

@RunWith(JMockit.class)
public class WorkInformationTest {

	@Injectable
	private Require require;

	@Test
	public void getters() {
		WorkInformation workInformation = new WorkInformation("workTypeCode", "workTimeCode");
		NtsAssert.invokeGetters(workInformation);
	}

	@Test
	public void testRemoveWorkTimeInHolydayWorkType() {
		WorkInformation workInformation = new WorkInformation("workTypeCode", "workTimeCode");
		workInformation.removeWorkTimeInHolydayWorkType();
		assertThat(workInformation.getWorkTimeCode()).isEqualTo(null);
	}

	@Test
	public void testWorkInformation() {
		WorkTypeCode workTypeCode = new WorkTypeCode("123");
		WorkTimeCode workTimeCode = new WorkTimeCode("abc");

		WorkInformation workInformation = new WorkInformation(workTypeCode, workTimeCode);
		assertThat(workInformation.getWorkTimeCode()).isEqualTo(workTimeCode);
		assertThat(workInformation.getWorkTypeCode()).isEqualTo(workTypeCode);
	}

	@Test
	public void testSetWorkTimeCode() {
		WorkTimeCode workTimeCode = new WorkTimeCode("abc");
		WorkInformation workInformation = new WorkInformation("workTypeCode", "workTimeCode");
		workInformation.setWorkTimeCode(workTimeCode);
		assertThat(workInformation.getWorkTimeCode()).isEqualTo(workTimeCode);
	}

	@Test
	public void testSetWorkTypeCode() {
		WorkTypeCode workTypeCode = new WorkTypeCode("123");
		WorkInformation workInformation = new WorkInformation("workTypeCode", "workTimeCode");
		workInformation.setWorkTypeCode(workTypeCode);
		assertThat(workInformation.getWorkTypeCode()).isEqualTo(workTypeCode);
	}

	@Test
	public void checkNormalCondition_is_true() {
		WorkInformation workInformation = new WorkInformation("workTypeCode", "workTimeCode");
		WorkTimeSetting workTimeSetting = WorkinfoHelper.getWorkTimeSettingDefault();
		workTimeSetting.setAbolishAtr(AbolishAtr.NOT_ABOLISH);
		new Expectations() {
			{
				require.getWorkType(workInformation.getWorkTypeCode().v());
				result = Optional.of(new WorkType());

				require.checkNeededOfWorkTimeSetting(workInformation.getWorkTypeCode().v());
				result = SetupType.REQUIRED;

				require.getWorkTime(workInformation.getWorkTimeCode().v());
				result = Optional.empty();
				result = Optional.of(workTimeSetting);
			}
		};
		assertThat(workInformation.checkNormalCondition(require)).isFalse();
	}

	@Test
	public void checkNormalCondition_is_false() {
		WorkInformation workInformation = new WorkInformation("workTypeCode", "workTimeCode");

		new Expectations() {
			{
				require.getWorkType(workInformation.getWorkTypeCode().v());
			}
		};
		assertThat(workInformation.checkNormalCondition(require)).isFalse();
	}

	@Test
	public void testCheckErrorCondition_is_WORKTYPE_WAS_DELETE() {
		WorkInformation workInformation = new WorkInformation("workTypeCode", "workTimeCode");

		new Expectations() {
			{
				require.getWorkType(workInformation.getWorkTypeCode().v());
			}
		};
		assertThat(workInformation.checkErrorCondition(require)).isEqualTo(ErrorStatusWorkInfo.WORKTYPE_WAS_DELETE);
	}

	/**
	 * if SetupType = REQUIRED
	 * @就業時間帯コード ==null
	 *
	 */
	@Test
	public void testCheckErrorCondition_is_WORKTIME_ARE_REQUIRE_NOT_SET() {
		WorkInformation workInformation = new WorkInformation("workTypeCode", null);

		new Expectations() {
			{
				require.getWorkType(workInformation.getWorkTypeCode().v());
				result = Optional.of(new WorkType());

				require.checkNeededOfWorkTimeSetting(workInformation.getWorkTypeCode().v());
				result = SetupType.REQUIRED;

			}
		};

		assertThat(workInformation.checkErrorCondition(require)).isEqualTo(ErrorStatusWorkInfo.WORKTIME_ARE_REQUIRE_NOT_SET);
	}
	/**
	 * if SetupType = REQUIRED
	 * @就業時間帯コード !=null
	 * require.就業時間帯を取得する(ログイン会社ID, @就業時間帯コード) empty
	 *
	 */
	@Test
	public void testCheckErrorCondition_is_WORKTIME_WAS_DELETE() {
		WorkInformation workInformation = new WorkInformation("workTypeCode", "workTimecode");
		new Expectations() {
			{

				require.getWorkType(workInformation.getWorkTypeCode().v());
				result = Optional.of(new WorkType());

				require.checkNeededOfWorkTimeSetting(workInformation.getWorkTypeCode().v());
				result = SetupType.REQUIRED;

				require.getWorkTime(workInformation.getWorkTimeCode().v());
			}
		};

		assertThat(workInformation.checkErrorCondition(require))
				.isEqualTo(ErrorStatusWorkInfo.WORKTIME_WAS_DELETE);
	}


	/**
	 * if SetupType = REQUIRED
	 * @就業時間帯コード !=null
	 * require.就業時間帯を取得する(ログイン会社ID, @就業時間帯コード) not empty
	 * if $勤務種類.廃止区分 != 廃止する
	 * if $就業時間帯.廃止区分 == 廃止する
	 */
	@Test
	public void testCheckErrorCondition_is_WORKTIME_HAS_BEEN_ABOLISHED() {
		WorkInformation workInformation = new WorkInformation("workTypeCode", "workTimecode");
		WorkType workType = new WorkType();
		workType.setDeprecate(DeprecateClassification.NotDeprecated);
		WorkTimeSetting workTimeSetting = WorkinfoHelper.getWorkTimeSettingDefault();
		workTimeSetting.setAbolishAtr(AbolishAtr.ABOLISH);
		new Expectations() {
			{

				require.getWorkType(workInformation.getWorkTypeCode().v());
				result = Optional.of(new WorkType());

				require.checkNeededOfWorkTimeSetting(workInformation.getWorkTypeCode().v());
				result = SetupType.REQUIRED;

				require.getWorkTime(workInformation.getWorkTimeCode().v());
				result = Optional.of(workTimeSetting);
			}
		};

		assertThat(workInformation.checkErrorCondition(require))
				.isEqualTo(ErrorStatusWorkInfo.WORKTIME_HAS_BEEN_ABOLISHED);
	}

	/**
	 * if SetupType = REQUIRED
	 * @就業時間帯コード !=null
	 * require.就業時間帯を取得する(ログイン会社ID, @就業時間帯コード) not empty
	 * if $勤務種類.廃止区分 != 廃止する
	 * if $就業時間帯.廃止区分 != 廃止する
	 */
	@Test
	public void testCheckErrorCondition_is_NORMAL() {
		WorkInformation workInformation = new WorkInformation("workTypeCode", "workTimecode");
		WorkType workType = new WorkType();
		workType.setDeprecate(DeprecateClassification.NotDeprecated);
		WorkTimeSetting workTimeSetting = WorkinfoHelper.getWorkTimeSettingDefault();
		workTimeSetting.setAbolishAtr(AbolishAtr.NOT_ABOLISH);
		new Expectations() {
			{

				require.getWorkType(workInformation.getWorkTypeCode().v());
				result = Optional.of(new WorkType());

				require.checkNeededOfWorkTimeSetting(workInformation.getWorkTypeCode().v());
				result = SetupType.REQUIRED;

				require.getWorkTime(workInformation.getWorkTimeCode().v());
				result = Optional.of(workTimeSetting);
			}
		};

		assertThat(workInformation.checkErrorCondition(require))
				.isEqualTo(ErrorStatusWorkInfo.NORMAL);
	}

	/**
	 * if SetupType = OPTIONAL
	 * require.就業時間帯を取得する(ログイン会社ID, @就業時間帯コード) empty
	 *
	 */
	@Test
	public void testCheckErrorCondition_is_WORKTIME_WAS_DELETE_1() {
		WorkInformation workInformation = new WorkInformation("workTypeCode", "workTimecode");
		new Expectations() {
			{

				require.getWorkType(workInformation.getWorkTypeCode().v());
				result = Optional.of(new WorkType());

				require.checkNeededOfWorkTimeSetting(workInformation.getWorkTypeCode().v());
				result = SetupType.OPTIONAL;

				require.getWorkTime(workInformation.getWorkTimeCode().v());
			}
		};

		assertThat(workInformation.checkErrorCondition(require))
				.isEqualTo(ErrorStatusWorkInfo.WORKTIME_WAS_DELETE);
	}

	/**
	 *
	 * @就業時間帯コード !=null
	 * require.就業時間帯を取得する(ログイン会社ID, @就業時間帯コード) not empty
	 * if $勤務種類.廃止区分 == 廃止する
	 */
	@Test
	public void testCheckErrorCondition_is_WORKTYPE_WAS_ABOLISHED_1() {
		WorkInformation workInformation = new WorkInformation("workTypeCode", "workTimecode");
		WorkType workType = new WorkType();
		workType.setDeprecate(DeprecateClassification.Deprecated);
		new Expectations() {
			{

				require.getWorkType(workInformation.getWorkTypeCode().v());
				result = Optional.of(workType);
			}
		};

		assertThat(workInformation.checkErrorCondition(require))
				.isEqualTo(ErrorStatusWorkInfo.WORKTYPE_WAS_ABOLISHED);
	}

	/**
	 * if SetupType = OPTIONAL
	 * @就業時間帯コード !=null
	 * require.就業時間帯を取得する(ログイン会社ID, @就業時間帯コード) not empty
	 * if $勤務種類.廃止区分 != 廃止する
	 * if $就業時間帯.廃止区分 == 廃止する
	 */
	@Test
	public void testCheckErrorCondition_is_WORKTIME_HAS_BEEN_ABOLISHED_1() {
		WorkInformation workInformation = new WorkInformation("workTypeCode", "workTimecode");
		WorkType workType = new WorkType();
		workType.setDeprecate(DeprecateClassification.NotDeprecated);
		WorkTimeSetting workTimeSetting = WorkinfoHelper.getWorkTimeSettingDefault();
		workTimeSetting.setAbolishAtr(AbolishAtr.ABOLISH);
		new Expectations() {
			{

				require.getWorkType(workInformation.getWorkTypeCode().v());
				result = Optional.of(workType);

				require.checkNeededOfWorkTimeSetting(workInformation.getWorkTypeCode().v());
				result = SetupType.OPTIONAL;

				require.getWorkTime(workInformation.getWorkTimeCode().v());
				result = Optional.of(workTimeSetting);
			}
		};

		assertThat(workInformation.checkErrorCondition(require))
				.isEqualTo(ErrorStatusWorkInfo.WORKTIME_HAS_BEEN_ABOLISHED);
	}

	/**
	 * if SetupType = OPTIONAL
	 * @就業時間帯コード !=null
	 * require.就業時間帯を取得する(ログイン会社ID, @就業時間帯コード) not empty
	 * if $勤務種類.廃止区分 != 廃止する
	 * if $就業時間帯.廃止区分 != 廃止する
	 */
	@Test
	public void testCheckErrorCondition_is_NORMAL_1() {
		WorkInformation workInformation = new WorkInformation("workTypeCode", "workTimecode");
		WorkType workType = new WorkType();
		workType.setDeprecate(DeprecateClassification.NotDeprecated);
		WorkTimeSetting workTimeSetting = WorkinfoHelper.getWorkTimeSettingDefault();
		workTimeSetting.setAbolishAtr(AbolishAtr.NOT_ABOLISH);
		new Expectations() {
			{

				require.getWorkType(workInformation.getWorkTypeCode().v());
				result = Optional.of(workType);

				require.checkNeededOfWorkTimeSetting(workInformation.getWorkTypeCode().v());
				result = SetupType.OPTIONAL;

				require.getWorkTime(workInformation.getWorkTimeCode().v());
				result = Optional.of(workTimeSetting);
			}
		};

		assertThat(workInformation.checkErrorCondition(require))
				.isEqualTo(ErrorStatusWorkInfo.NORMAL);
	}


	/**
	 * if SetupType = NOT_REQUIRED
	 * @就業時間帯コード == null
	 * require.就業時間帯を取得する(ログイン会社ID, @就業時間帯コード) empty
	 */
	@Test
	public void testCheckErrorCondition_is_WORKTIME_WAS_DELETE_2() {
		WorkInformation workInformation = new WorkInformation("workTypeCode", null);
		WorkType workType = new WorkType();
		new Expectations() {
			{

				require.getWorkType(workInformation.getWorkTypeCode().v());
				result = Optional.of(workType);

				require.checkNeededOfWorkTimeSetting(workInformation.getWorkTypeCode().v());
				result = SetupType.NOT_REQUIRED;

			}
		};

		assertThat(workInformation.checkErrorCondition(require))
				.isEqualTo(ErrorStatusWorkInfo.NORMAL);
	}



	/**
	 * if SetupType = NOT_REQUIRED
	 * @就業時間帯コード !=null
	 * require.就業時間帯を取得する(ログイン会社ID, @就業時間帯コード) not empty
	 * if $勤務種類.廃止区分 != 廃止する
	 * if $就業時間帯.廃止区分 != 廃止する
	 */
	@Test
	public void testCheckErrorCondition_is_WORKTIME_ARE_SET_WHEN_UNNECESSARY() {
		WorkInformation workInformation = new WorkInformation("workTypeCode", "workTimeCode");
		WorkType workType = new WorkType();
		workType.setDeprecate(DeprecateClassification.NotDeprecated);
		WorkTimeSetting workTimeSetting = WorkinfoHelper.getWorkTimeSettingDefault();
		workTimeSetting.setAbolishAtr(AbolishAtr.NOT_ABOLISH);
		new Expectations() {
			{

				require.getWorkType(workInformation.getWorkTypeCode().v());
				result = Optional.of(new WorkType());

				require.checkNeededOfWorkTimeSetting(workInformation.getWorkTypeCode().v());
				result = SetupType.NOT_REQUIRED;
			}
		};

		assertThat(workInformation.checkErrorCondition(require))
				.isEqualTo(ErrorStatusWorkInfo.WORKTIME_ARE_SET_WHEN_UNNECESSARY);
	}

	/**
	 * test ver 5
	 * worktimecode is null
	 */
	@Test
	public void testCheckErrorCondition_1() {
		WorkInformation workInformation = new WorkInformation("workTypeCode", null);
		new Expectations() {
			{

				require.getWorkType(workInformation.getWorkTypeCode().v());
				result = Optional.of(new WorkType());

				require.checkNeededOfWorkTimeSetting(workInformation.getWorkTypeCode().v());
				result = SetupType.OPTIONAL;

			}
		};

		assertThat(workInformation.checkErrorCondition(require))
				.isEqualTo(ErrorStatusWorkInfo.NORMAL);
	}



	/**
	 * Target	: getWorkStyle
	 * Pattern	: 勤務種類が取得できない
	 * Result	: Output -> Optional.empty
	 */
	@Test
	public void testGetWorkStyle_WorkTypeIsEmpty() {

		new Expectations() {{

			// 勤務種類を取得する
			require.getWorkType( anyString );

		}};

		// Execute
		val result = new WorkInformation("WktpCd", "WktmCd").getWorkStyle( require );

		// Assertion
		assertThat( result ).isEmpty();

	}

	/**
	 * Target	: getWorkStyle
	 * Pattern	: 1日の勤務：勤務の単位＝1日 / 勤務の分類＝出勤
	 * Result	: Output -> 1日系( WorkStyle.ONE_DAY_WORK )
	 */
	@Test
	public void testGetWorkStyle_WorkTypeIsOneDay_ClassTypeIsWork() {

		// 1日の勤務：単位＝1日 / 分類＝出勤
		val dailyWork = WorkinfoHelper.createDailyWorkAsOneDay(WorkTypeClassification.Attendance);
		// 勤務種類
		val workType = WorkinfoHelper.createWorkType(false, "cid", "WktpCd", "テスト", "テスト", "テ" , dailyWork);

		new Expectations() {{

			// 勤務種類を取得する
			require.getWorkType( anyString );
			result = Optional.of( workType );

		}};

		// Execute
		val result = new WorkInformation(workType.getWorkTypeCode().v(), "WktmCd").getWorkStyle( require );

		// Assertion
		assertThat( result ).isPresent();
		assertThat( result.get() ).isEqualTo( WorkStyle.ONE_DAY_WORK );

	}

	/**
	 * Target	: getWorkStyle
	 * Pattern	: 1日の勤務：勤務の単位＝半日 / 勤務の分類(午前)＝休日系 / 勤務の分類(午後)＝休日系
	 * Result	: Output -> 1日休日系( WorkStyle.ONE_DAY_REST )
	 */
	@Test
	public void testGetWorkStyle_WorkTypeIsHalfDay_ClassTypeIsLeaveAndLeave() {

		// 1日の勤務：単位＝1日 / 分類(午前)＝代休 / 分類(午後)＝特休
		val dailyWork = WorkinfoHelper.createDailyWorkAsHalfDay(
									WorkTypeClassification.SubstituteHoliday
								,	WorkTypeClassification.SpecialHoliday
							);
		// 勤務種類
		val workType = WorkinfoHelper.createWorkType(false, "cid", "WktpCd", "テスト", "テスト", "テ" , dailyWork);

		new Expectations() {{

			// 勤務種類を取得する
			require.getWorkType( anyString );
			result = Optional.of( workType );

		}};

		// Execute
		val result = new WorkInformation(workType.getWorkTypeCode().v(), "WktmCd").getWorkStyle( require );

		// Assertion
		assertThat( result ).isPresent();
		assertThat( result.get() ).isEqualTo( WorkStyle.ONE_DAY_REST );

	}

	/**
	 * Target	: getWorkStyle
	 * Pattern	: 1日の勤務：勤務の単位＝半日 / 勤務の分類(午前)＝出勤系 / 勤務の分類(午後)＝休日系
	 * Result	: Output -> 午前出勤系( WorkStyle.MORNING_WORK )
	 */
	@Test
	public void testGetWorkStyle_WorkTypeIsHalfDay_ClassTypeIsWorkAndLeave() {

		// 1日の勤務：単位＝1日 / 分類(午前)＝振出 / 分類(午後)＝年休
		val dailyWork = WorkinfoHelper.createDailyWorkAsHalfDay(
									WorkTypeClassification.Shooting
								,	WorkTypeClassification.AnnualHoliday
							);
		// 勤務種類
		val workType = WorkinfoHelper.createWorkType(false, "cid", "WktpCd", "テスト", "テスト", "テ" , dailyWork);

		new Expectations() {{

			// 勤務種類を取得する
			require.getWorkType( anyString );
			result = Optional.of( workType );

		}};

		// Execute
		val result = new WorkInformation(workType.getWorkTypeCode().v(), "WktmCd").getWorkStyle( require );

		// Assertion
		assertThat( result ).isPresent();
		assertThat( result.get() ).isEqualTo( WorkStyle.MORNING_WORK );

	}


	@Test
	public void getWorkInfoAndTimeZone_1() {
		WorkInformation workInformation = new WorkInformation("workTypeCode", "workTimeCode");

		new Expectations() {
			{
				require.getWorkType(workInformation.getWorkTypeCode().v());

			}
		};

		assertThat(workInformation.getWorkInfoAndTimeZone(require).isPresent()).isFalse();
	}

	@Test
	public void getWorkInfoAndTimeZone_2() {
		WorkInformation workInformation = new WorkInformation("workTypeCode", null);
		WorkType workType = new WorkType();
		workType.setWorkTypeCode(workInformation.getWorkTypeCode());
		new Expectations() {
			{
				require.getWorkType(workInformation.getWorkTypeCode().v());
				result = Optional.of(workType);

			}
		};
		Optional<WorkInfoAndTimeZone>  result = workInformation.getWorkInfoAndTimeZone(require);
		assertThat(result.get().getTimeZones().isEmpty()).isTrue();
		assertThat(result.get().getWorkTime().isPresent()).isFalse();
		assertThat(result.get().getWorkType().getWorkTypeCode()).isEqualTo(workInformation.getWorkTypeCode());
	}

	@Test
	public void getWorkInfoAndTimeZone_3() {
		WorkInformation workInformation = new WorkInformation("workTypeCode", "workTimeCode");

		new Expectations() {
			{
				require.getWorkType(workInformation.getWorkTypeCode().v());
				result = Optional.of(new WorkType());

				require.getWorkTime(anyString);

			}
		};

		assertThat(workInformation.getWorkInfoAndTimeZone(require).isPresent()).isFalse();
	}

	@Test
	public void getWorkInfoAndTimeZone_4() {
		WorkInformation workInformation = new WorkInformation("workTypeCode", "workTimeCode");

		List<TimezoneUse> listTimezoneUse = new ArrayList<>();
		listTimezoneUse.add(new TimezoneUse(new TimeWithDayAttr(1), new TimeWithDayAttr(2), UseSetting.USE, 2));
		listTimezoneUse.add(new TimezoneUse(new TimeWithDayAttr(2), new TimeWithDayAttr(3), UseSetting.USE, 1));
		listTimezoneUse.add(new TimezoneUse(new TimeWithDayAttr(4), new TimeWithDayAttr(5), UseSetting.NOT_USE, 3));
		new Expectations() {
			{
				require.getWorkType(workInformation.getWorkTypeCode().v());
				result = Optional.of(new WorkType());

				require.getWorkTime(anyString);
				result = Optional.of(new WorkTimeSetting());

				require.getPredeterminedTimezone(anyString, anyString, null).getTimezones();
				result = listTimezoneUse;

			}
		};
		Optional<WorkInfoAndTimeZone>  result = workInformation.getWorkInfoAndTimeZone(require);
		assertThat(result.isPresent()).isTrue();
		assertThat(result.get().getTimeZones().size()).isEqualTo(listTimezoneUse.size()-1);
		//workNo 1
		assertThat(result.get().getTimeZones().get(0).getStart()).isEqualTo(listTimezoneUse.get(1).getStart());
		assertThat(result.get().getTimeZones().get(0).getEnd()).isEqualTo(listTimezoneUse.get(1).getEnd());
		//workNo 2
		assertThat(result.get().getTimeZones().get(1).getStart()).isEqualTo(listTimezoneUse.get(0).getStart());
		assertThat(result.get().getTimeZones().get(1).getEnd()).isEqualTo(listTimezoneUse.get(0).getEnd());
	}


	/**
	 * Target	: getChangeableWorkingTimezones
	 * Pattern	: 勤務種類 is empty
	 * Result	: Output -> List.empty
	 */
	@Test
	public void getChangeableWorkingTimezones_WorkTypeIsEmpty() {

		// 勤務情報
		val instance = new WorkInformation( "WorkTypeCode", "WorkTimeCode" );

		new Expectations() {{
			// 勤務種類を取得する
			require.getWorkType( anyString );
		}};

		// Execute
		val result = instance.getChangeableWorkingTimezones(require);

		// Assertion
		assertThat( result ).isEmpty();

	}

	/**
	 * Target	: getChangeableWorkingTimezones
	 * Pattern	: 勤務設定 is empty
	 * Result	: Output -> List.empty
	 */
	@Test
	public void getChangeableWorkingTimezones_WorkSettingIsEmpty(@Mocked WorkType workType) {

		// 勤務情報
		val instance = new WorkInformation( "WorkTypeCode", "WorkTimeCode" );

		new Expectations() {{
			// 勤務種類を取得する
			require.getWorkType( anyString );
			result = Optional.of(workType);

			// 就業時間帯を取得する
			require.getWorkTime( anyString );
		}};

		// Execute
		val result = instance.getChangeableWorkingTimezones(require);

		// Assertion
		assertThat( result ).isEmpty();

	}

	/**
	 * Target	: getChangeableWorkingTimezones
	 * Pattern	: 勤務設定 is empty
	 * Result	: Output -> 出勤日区分による変更可能な時間帯 の結果
	 */
	@Test
	public void getChangeableWorkingTimezones_Complete(
			@Mocked WorkType workType
		,	@Mocked WorkTimeSetting workTime
		,	@Mocked WorkSetting workSetting
	) {

		// 勤務情報
		val instance = new WorkInformation( "WorkTypeCode", "WorkTimeCode" );
		// 出勤日区分
		val atdDayAtr = AttendanceDayAttr.HALF_TIME_PM;
		// 変更可能な勤務時間帯
		val cwtzPerNoList = Arrays.asList(ChangeableWorkingTimeZonePerNo.createAsUnchangeable(new WorkNo(1).toAttendance()
									, new TimeSpanForCalc( TimeWithDayAttr.hourMinute( 8, 0 ), TimeWithDayAttr.hourMinute( 17, 0 ) )
							));
		val chgWrkTz = ChangeableWorkingTimeZone.createWithoutSeparationOfHalfDay(cwtzPerNoList, cwtzPerNoList);

		new Expectations() {{
			// 勤務種類を取得する
			require.getWorkType( anyString );
			result = Optional.of(workType);

			// 就業時間帯を取得する
			require.getWorkTime( anyString );
			result = Optional.of(workTime);
			// 勤務設定を取得する
			workTime.getWorkSetting( require );
			result = workSetting;

			// 出勤日区分を取得する
			workType.chechAttendanceDay();
			result = atdDayAtr;	// 出勤日区分：半日出勤系(午前)

			// 出勤日区分による変更可能な時間帯を取得する
			workSetting.getChangeableWorkingTimeZone( require );
			result = chgWrkTz;

			// 出勤日区分による変更可能な時間帯
			chgWrkTz.getByAtr( atdDayAtr );
			times = 1;
		}};

		// Execute
		val result = instance.getChangeableWorkingTimezones(require);

		// Assertion
		assertThat( result ).containsExactlyInAnyOrderElementsOf( chgWrkTz.getByAtr( atdDayAtr ) );

	}


	/**
	 * Target	: containsOnChangeableWorkingTime
	 */
	@Test
	public void containsOnChangeableWorkingTime(
			@Mocked WorkType workType
		,	@Mocked WorkTimeSetting workTime
		,	@Mocked FixedWorkSetting workSetting
	) {

		// 勤務情報
		val instance = new WorkInformation( "WorkTypeCode", "WorkTimeCode" );
		// 出勤日区分
		val atdDayAtr = AttendanceDayAttr.HOLIDAY_WORK;
		// 勤務NOごとの変更可能な勤務時間帯リスト
		val cwtzPerNoList = Arrays.asList(
				ChangeableWorkingTimeZonePerNo.createAsStartEqualsEnd(new WorkNo(1).toAttendance()
						, new TimeSpanForCalc( TimeWithDayAttr.hourMinute(  8,  0 ), TimeWithDayAttr.hourMinute( 17,  0 ) ))
			,	ChangeableWorkingTimeZonePerNo.createAsStartEqualsEnd(new WorkNo(2).toAttendance()
						, new TimeSpanForCalc( TimeWithDayAttr.hourMinute( 18, 30 ), TimeWithDayAttr.hourMinute( 25, 30 ) ))
		);
		val chgWrkTz = ChangeableWorkingTimeZone.createWithoutSeparationOfHalfDay(cwtzPerNoList, cwtzPerNoList);

		// チェック対象
		val checkTarget = ClockAreaAtr.END;					// チェック対象時刻区分
		val time = TimeWithDayAttr.hourMinute( 20, 15 );	// 時刻

		new Expectations() {{
			// 変更可能な勤務時間帯を取得する
			// 勤務種類を取得する
			require.getWorkType( anyString );
			result = Optional.of(workType);

			// 就業時間帯を取得する
			require.getWorkTime( anyString );
			result = Optional.of(workTime);
			// 勤務設定を取得する
			workTime.getWorkSetting( require );
			result = workSetting;

			// 出勤日区分を取得する
			workType.chechAttendanceDay();
			result = atdDayAtr;

			// 出勤日区分による変更可能な時間帯を取得する
			workSetting.getChangeableWorkingTimeZone( require );
			result = chgWrkTz;
		}};

		// Pattern: 指定された勤務NOがリストに存在しない
		{
			// Execute
			val result = instance.containsOnChangeableWorkingTime(require, checkTarget, new WorkNo(3), time);

			// Assertion
			assertThat( result.isContains() ).isFalse();
			assertThat( result.getTimeSpan() ).isEmpty();
		}

		// Pattern: 指定された勤務NOがリストに存在する
		{
			// Execute
			val result = instance.containsOnChangeableWorkingTime(require, checkTarget, cwtzPerNoList.get(1).getWorkNo().toTemporary(), time);

			// Assertion
			assertThat( result ).isEqualTo( cwtzPerNoList.get(1).contains(time, checkTarget) );
		}

	}


	/**
	 * Target	: getBreakTimeZone
	 * Pattern	: 勤務種類 is empty
	 * Result	: Output -> Optional.empty
	 */
	@Test
	public void getBreakTimeZone_WorkTypeIsEmpty() {

		// 勤務情報
		val instance = new WorkInformation( "WorkTypeCode", "WorkTimeCode" );

		new Expectations() {{
			// 勤務種類を取得する
			require.getWorkType( anyString );
		}};

		// Execute
		val result = instance.getBreakTimeZone(require);

		// Assertion
		assertThat( result ).isEmpty();

	}

	/**
	 * Target	: getBreakTimeZone
	 * Pattern	: 勤務設定 is empty
	 * Result	: Output -> Optional.empty
	 */
	@Test
	public void getBreakTimeZone_WorkSettingIsEmpty(@Mocked WorkType workType) {

		// 勤務情報
		val instance = new WorkInformation( "WorkTypeCode", "WorkTimeCode" );

		new Expectations() {{
			// 勤務種類を取得する
			require.getWorkType( anyString );
			result = Optional.of(workType);

			// 就業時間帯を取得する
			require.getWorkTime( anyString );
		}};

		// Execute
		val result = instance.getBreakTimeZone(require);

		// Assertion
		assertThat( result ).isEmpty();

	}

	/**
	 * Target	: getBreakTimeZone
	 * Pattern	: 出勤日区分 is 休日
	 * Result	: Output -> Optional.empty
	 */
	@Test
	public void getBreakTimeZone_AtdDayAtrIsHoliday(
				@Mocked WorkType workType
			,	@Mocked WorkTimeSetting workTime
			,	@Mocked WorkSetting workSetting
	) {

		// 勤務情報
		val instance = new WorkInformation( "WorkTypeCode", "WorkTimeCode" );

		new Expectations() {{
			// 勤務種類を取得する
			require.getWorkType( anyString );
			result = Optional.of(workType);

			// 就業時間帯を取得する
			require.getWorkTime( anyString );
			result = Optional.of(workTime);
			// 勤務設定を取得する
			workTime.getWorkSetting( require );
			result = workSetting;

			// 出勤日区分を取得する
			workType.chechAttendanceDay();
			result = AttendanceDayAttr.HOLIDAY;	// 出勤日区分：1日休日系
		}};

		// Execute
		val result = instance.getBreakTimeZone(require);

		// Assertion
		assertThat( result ).isEmpty();

	}

	/**
	 * Target	: getBreakTimeZone
	 * Pattern	: 出勤日区分 is not 休日
	 * Result	: Output -> 休憩時間
	 */
	@Test
	public void getBreakTimeZone_AtdDayAtrIsNotHoliday(
				@Mocked WorkType workType
			,	@Mocked WorkTimeSetting workTime
			,	@Mocked FixedWorkSetting workSetting
			,	@Mocked BreakTimeZone brkTz
	) {

		// 勤務情報
		val instance = new WorkInformation( "WorkTypeCode", "WorkTimeCode" );
		// 出勤日区分
		val atdDayAtr = AttendanceDayAttr.HALF_TIME_AM;	// 半日出勤系(午前)
		val isDayOffWork = atdDayAtr.isHolidayWork();	// 休出かどうか
		val amPmAtr = atdDayAtr.toAmPmAtr().get();		// 午前午後区分

		new Expectations() {{
			// 勤務種類を取得する
			require.getWorkType( anyString );
			result = Optional.of(workType);

			// 就業時間帯を取得する
			require.getWorkTime( anyString );
			result = Optional.of(workTime);
			// 勤務設定を取得する
			workTime.getWorkSetting( require );
			result = workSetting;

			// 出勤日区分を取得する
			workType.chechAttendanceDay();
			result = atdDayAtr;	// 出勤日区分：半日出勤系(午前)

			// 休憩時間を取得する
			workSetting.getBreakTimeZone( isDayOffWork, amPmAtr );
			result = brkTz;
		}};

		// Execute
		val result = instance.getBreakTimeZone(require);

		// Assertion
		assertThat( result ).isPresent();
		assertThat( result.get() ).isEqualTo( brkTz );

	}


	/**
	 * Target	: getWorkSetting
	 * Pattern	: 就業時間帯コード is empty
	 * Result	: Output -> Optional.empty
	 */
	@Test
	public void getWorkSetting_WorkTimeCodeIsEmpty() {

		// 勤務情報
		// 就業時間帯コード⇒Optional.empty※emptyを設定するにはnullを指定する必要がある
		val instance = new WorkInformation( "WorkTypeCode", null );

		// Execute
		@SuppressWarnings("unchecked")
		val result = (Optional<WorkSetting>)NtsAssert.Invoke.privateMethod(instance
							, "getWorkSetting"
							, require
						);

		// Assertion
		assertThat( result ).isEmpty();

	}

	/**
	 * Target	: getWorkSetting
	 * Pattern	: 就業時間帯 is empty
	 * Result	: Output -> Optional.empty
	 */
	@Test
	public void getWorkSetting_WorkTimeIsEmpty() {

		// 勤務情報
		val instance = new WorkInformation( "WorkTypeCode", "WorkTimeCode" );

		new Expectations() {{
			// 就業時間帯を取得する
			require.getWorkTime( anyString );
		}};

		// Execute
		@SuppressWarnings("unchecked")
		val result = (Optional<WorkSetting>)NtsAssert.Invoke.privateMethod(instance
							, "getWorkSetting"
							, require
						);

		// Assertion
		assertThat( result ).isEmpty();

	}

	/**
	 * Target	: getWorkSetting
	 * Pattern	: 就業時間帯 is present
	 * Result	: Output -> 勤務設定
	 */
	@Test
	public void getWorkSetting_WorkTimeIsPresent(@Mocked WorkTimeSetting workTime, @Mocked WorkSetting workSetting) {

		// 勤務情報
		val instance = new WorkInformation( "WorkTypeCode", "WorkTimeCode" );
		// 就業時間帯

		new Expectations() {{
			// 就業時間帯を取得する
			require.getWorkTime( anyString );
			result = Optional.of(workTime);
			// 勤務設定を取得する
			workTime.getWorkSetting( require );
			result = workSetting;
		}};

		// Execute
		@SuppressWarnings("unchecked")
		val result = (Optional<WorkSetting>)NtsAssert.Invoke.privateMethod(instance
							, "getWorkSetting"
							, require
						);

		// Assertion
		assertThat( result ).isPresent();
		assertThat( result.get() ).isEqualTo( workSetting );

	}
	
	@Test
	public void testEquals_differentWorkType() {
		
		WorkInformation target = new WorkInformation(new WorkTypeCode("k01"), new WorkTimeCode("s01"));
		WorkInformation otherObject = new WorkInformation(new WorkTypeCode("k02"), new WorkTimeCode("s01"));
		
		assertThat( target.equals(otherObject) ).isFalse();
	}
	
	@Test
	public void testEquals_sameWorkType_workTimesAllEmpty() {
		
		WorkInformation target = new WorkInformation(new WorkTypeCode("k01"), null );
		WorkInformation otherObject = new WorkInformation(new WorkTypeCode("k01"), null );
		
		assertThat( target.equals(otherObject) ).isTrue();
	}
	
	@Test
	public void testEquals_sameWorkType_workTimeNotMatch_case1() {
		
		WorkInformation target = new WorkInformation(new WorkTypeCode("k01"), new WorkTimeCode("s01") );
		WorkInformation otherObject = new WorkInformation(new WorkTypeCode("k01"), null );
		
		assertThat( target.equals(otherObject) ).isFalse();
	}
	
	@Test
	public void testEquals_sameWorkType_workTimeNotMatch_case2() {
		
		WorkInformation target = new WorkInformation(new WorkTypeCode("k01"), null );
		WorkInformation otherObject = new WorkInformation(new WorkTypeCode("k01"), new WorkTimeCode("s01") );
		
		assertThat( target.equals(otherObject) ).isFalse();
	}
	
	@Test
	public void testEquals_sameWorkType_sameWorkTime() {
		
		WorkInformation target = new WorkInformation(new WorkTypeCode("k01"), new WorkTimeCode("s01") );
		WorkInformation otherObject = new WorkInformation(new WorkTypeCode("k01"), new WorkTimeCode("s01") );
		
		assertThat( target.equals(otherObject) ).isTrue();
	}


}
