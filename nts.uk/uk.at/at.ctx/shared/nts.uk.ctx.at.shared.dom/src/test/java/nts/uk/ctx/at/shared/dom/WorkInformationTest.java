package nts.uk.ctx.at.shared.dom;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.WorkInformation.Require;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.workrule.ErrorStatusWorkInfo;
import nts.uk.ctx.at.shared.dom.worktime.common.AbolishAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneUse;
import nts.uk.ctx.at.shared.dom.worktime.predset.UseSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.DeprecateClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
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
				result = Optional.empty();
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



	@Test
	public void testGetWorkStyle_1() {
		WorkInformation workInformation = new WorkInformation("workTypeCode", "workTimeCode");

		new Expectations() {
			{
				require.checkWorkDay(workInformation.getWorkTypeCode().v());
				result = null;

			}
		};
		assertThat(workInformation.getWorkStyle(require).isPresent()).isFalse();
	}

	@Test
	public void testGetWorkStyle_2() {
		WorkInformation workInformation = new WorkInformation("workTypeCode", "workTimeCode");

		new Expectations() {
			{
				require.checkWorkDay(workInformation.getWorkTypeCode().v());
				result = WorkStyle.ONE_DAY_REST;

			}
		};

		assertThat(workInformation.getWorkStyle(require).get()).isEqualTo(WorkStyle.ONE_DAY_REST);
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
		assertThat(result.get().getListTimeZone().isEmpty()).isTrue();
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
		assertThat(result.get().getListTimeZone().size()).isEqualTo(listTimezoneUse.size()-1);
		//workNo 1
		assertThat(result.get().getListTimeZone().get(0).getStart()).isEqualTo(listTimezoneUse.get(1).getStart());
		assertThat(result.get().getListTimeZone().get(0).getEnd()).isEqualTo(listTimezoneUse.get(1).getEnd());
		//workNo 2
		assertThat(result.get().getListTimeZone().get(1).getStart()).isEqualTo(listTimezoneUse.get(0).getStart());
		assertThat(result.get().getListTimeZone().get(1).getEnd()).isEqualTo(listTimezoneUse.get(0).getEnd());
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


}
