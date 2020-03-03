package nts.uk.ctx.at.shared.dom;

import static org.assertj.core.api.Assertions.assertThat;

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
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.WorkingTimeSetting;
import nts.uk.ctx.at.shared.dom.workrule.ErrorStatusWorkInfo;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneUse;
import nts.uk.ctx.at.shared.dom.worktime.predset.UseSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.shr.com.time.TimeWithDayAttr;

@RunWith(JMockit.class)
public class WorkInformationTest {

	@Injectable
	private Require require;

	@Test
	public void getters() {
		WorkInformation workInformation = new WorkInformation("workTimeCode", "workTypeCode");
		workInformation.removeWorkTimeInHolydayWorkType();
		NtsAssert.invokeGetters(workInformation);
	}

	@Test
	public void testRemoveWorkTimeInHolydayWorkType() {
		WorkInformation workInformation = new WorkInformation("workTimeCode", "workTypeCode");
		workInformation.removeWorkTimeInHolydayWorkType();
		assertThat(workInformation.getWorkTimeCode()).isEqualTo(null);
	}

	@Test
	public void testWorkInformation() {
		WorkTimeCode workTimeCode = new WorkTimeCode("abc");
		WorkTypeCode workTypeCode = new WorkTypeCode("123");
		WorkInformation workInformation = new WorkInformation(workTimeCode, workTypeCode);
		assertThat(workInformation.getWorkTimeCode()).isEqualTo(workTimeCode);
		assertThat(workInformation.getWorkTypeCode()).isEqualTo(workTypeCode);
	}

	@Test
	public void testSetWorkTimeCode() {
		WorkTimeCode workTimeCode = new WorkTimeCode("abc");
		WorkInformation workInformation = new WorkInformation("workTimeCode", "workTypeCode");
		workInformation.setWorkTimeCode(workTimeCode);
		assertThat(workInformation.getWorkTimeCode()).isEqualTo(workTimeCode);
	}

	@Test
	public void testSetWorkTypeCode() {
		WorkTypeCode workTypeCode = new WorkTypeCode("123");
		WorkInformation workInformation = new WorkInformation("workTimeCode", "workTypeCode");
		workInformation.setWorkTypeCode(workTypeCode);
		assertThat(workInformation.getWorkTypeCode()).isEqualTo(workTypeCode);
	}

	@Test
	public void checkNormalCondition_is_true() {
		WorkInformation workInformation = new WorkInformation("workTimeCode", "workTypeCode");

		new Expectations() {
			{
				require.findByPK(workInformation.getWorkTypeCode().v());
				result = Optional.of(new WorkType());

				require.checkNeededOfWorkTimeSetting(workInformation.getWorkTypeCode().v());
				result = SetupType.REQUIRED;

				require.findByCode(workInformation.getWorkTimeCode().v());
				result = Optional.of(new WorkingTimeSetting());
			}
		};
		assertThat(workInformation.checkNormalCondition(require)).isTrue();
	}

	@Test
	public void checkNormalCondition_is_false() {
		WorkInformation workInformation = new WorkInformation("workTimeCode", "workTypeCode");

		new Expectations() {
			{
				require.findByPK(workInformation.getWorkTypeCode().v());
				result = Optional.empty();
			}
		};
		assertThat(workInformation.checkNormalCondition(require)).isFalse();
	}

	@Test
	public void testCheckErrorCondition_throw_Msg_1608() {
		WorkInformation workInformation = new WorkInformation("workTimeCode", "workTypeCode");

		new Expectations() {
			{
				require.findByPK(workInformation.getWorkTypeCode().v());
				result = Optional.empty();
			}
		};
		assertThat(workInformation.checkErrorCondition(require)).isEqualTo(ErrorStatusWorkInfo.WORKTYPE_WAS_DELETE);
	}

	@Test
	public void testCheckErrorCondition_throw_Msg_1609() {
		WorkInformation workInformation = new WorkInformation("workTimeCode", "workTypeCode");

		new Expectations() {
			{
				require.findByPK(workInformation.getWorkTypeCode().v());
				result = Optional.of(new WorkType());

				require.checkNeededOfWorkTimeSetting(workInformation.getWorkTypeCode().v());
				result = SetupType.REQUIRED;
			}
		};

		assertThat(workInformation.checkErrorCondition(require)).isEqualTo(ErrorStatusWorkInfo.WORKTIME_WAS_DELETE);
	}

	@Test
	public void testCheckErrorCondition_throw_Msg_435() {
		WorkInformation workInformation = new WorkInformation(null, "workTypeCode");

		new Expectations() {
			{

				require.findByPK(workInformation.getWorkTypeCode().v());
				result = Optional.of(new WorkType());

				require.checkNeededOfWorkTimeSetting(workInformation.getWorkTypeCode().v());
				result = SetupType.REQUIRED;
			}
		};

		assertThat(workInformation.checkErrorCondition(require))
				.isEqualTo(ErrorStatusWorkInfo.WORKTIME_ARE_REQUIRE_NOT_SET);
	}

	@Test
	public void testCheckErrorCondition_throw_Msg_434() {
		WorkInformation workInformation = new WorkInformation("workTimeCode", "workTypeCode");

		new Expectations() {
			{

				require.findByPK(workInformation.getWorkTypeCode().v());
				result = Optional.of(new WorkType());

				require.checkNeededOfWorkTimeSetting(workInformation.getWorkTypeCode().v());
				result = SetupType.NOT_REQUIRED;

			}
		};

		assertThat(workInformation.checkErrorCondition(require))
				.isEqualTo(ErrorStatusWorkInfo.WORKTIME_ARE_SET_WHEN_UNNECESSARY);
	}

	@Test
	public void testCheckErrorCondition_is_NORMAL_1() {
		WorkInformation workInformation = new WorkInformation("workTimeCode", "workTypeCode");

		new Expectations() {
			{
				require.findByPK(workInformation.getWorkTypeCode().v());
				result = Optional.of(new WorkType());

				require.checkNeededOfWorkTimeSetting(workInformation.getWorkTypeCode().v());
				result = SetupType.REQUIRED;

				require.findByCode(workInformation.getWorkTimeCode().v());
				result = Optional.of(new WorkingTimeSetting());

			}
		};

		assertThat(workInformation.checkErrorCondition(require)).isEqualTo(ErrorStatusWorkInfo.NORMAL);
	}

	@Test
	public void testCheckErrorCondition_is_NORMAL_2() {
		WorkInformation workInformation = new WorkInformation(null, "workTypeCode");

		new Expectations() {
			{
				require.findByPK(workInformation.getWorkTypeCode().v());
				result = Optional.of(new WorkType());

				require.checkNeededOfWorkTimeSetting(workInformation.getWorkTypeCode().v());
				result = SetupType.OPTIONAL;

			}
		};

		assertThat(workInformation.checkErrorCondition(require)).isEqualTo(ErrorStatusWorkInfo.NORMAL);
	}

	@Test
	public void testCheckErrorCondition_is_NORMAL_3() {
		WorkInformation workInformation = new WorkInformation(null, "workTypeCode");

		new Expectations() {
			{
				require.findByPK(workInformation.getWorkTypeCode().v());
				result = Optional.of(new WorkType());

				require.checkNeededOfWorkTimeSetting(workInformation.getWorkTypeCode().v());
				result = SetupType.NOT_REQUIRED;

			}
		};

		assertThat(workInformation.checkErrorCondition(require)).isEqualTo(ErrorStatusWorkInfo.NORMAL);
	}

	@Test
	public void testGetWorkStyle_1() {
		WorkInformation workInformation = new WorkInformation("workTimeCode", "workTypeCode");

		new Expectations() {
			{
				require.checkWorkDay(workInformation.getWorkTypeCode().v());
				result = null;

			}
		};
		NtsAssert.businessException("Msg_1636", () -> workInformation.getWorkStyle(require));
	}

	@Test
	public void testGetWorkStyle_2() {
		WorkInformation workInformation = new WorkInformation("workTimeCode", "workTypeCode");

		new Expectations() {
			{
				require.checkWorkDay(workInformation.getWorkTypeCode().v());
				result = WorkStyle.ONE_DAY_REST;

			}
		};

		assertThat(workInformation.getWorkStyle(require)).isEqualTo(WorkStyle.ONE_DAY_REST);
	}

	@Test
	public void getWorkInfoAndTimeZone_1() {
		WorkInformation workInformation = new WorkInformation("workTimeCode", "workTypeCode");

		new Expectations() {
			{
				require.findByPK(workInformation.getWorkTypeCode().v());
				result = Optional.empty();

			}
		};

		assertThat(workInformation.getWorkInfoAndTimeZone(require).isPresent()).isFalse();
	}

	@Test
	public void getWorkInfoAndTimeZone_2() {
		WorkInformation workInformation = new WorkInformation(null, "workTypeCode");

		new Expectations() {
			{
				require.findByPK(workInformation.getWorkTypeCode().v());
				result = Optional.of(new WorkType());

			}
		};

		assertThat(workInformation.getWorkInfoAndTimeZone(require).get().getListTimeZone().isEmpty()).isTrue();
		assertThat(workInformation.getWorkInfoAndTimeZone(require).get().getWorkTime().isPresent()).isFalse();
	}

	@Test
	public void getWorkInfoAndTimeZone_3() {
		WorkInformation workInformation = new WorkInformation("workTimeCode", "workTypeCode");

		new Expectations() {
			{
				require.findByPK(workInformation.getWorkTypeCode().v());
				result = Optional.of(new WorkType());

				require.findByCode(anyString);
				result = Optional.empty();

			}
		};

		assertThat(workInformation.getWorkInfoAndTimeZone(require).isPresent()).isFalse();
	}

	@Test
	public void getWorkInfoAndTimeZone_4() {
		WorkInformation workInformation = new WorkInformation("workTimeCode", "workTypeCode");

		List<TimezoneUse> listTimezoneUse = new ArrayList<>();
		listTimezoneUse.add(new TimezoneUse(new TimeWithDayAttr(1), new TimeWithDayAttr(2), UseSetting.USE, 1));
		listTimezoneUse.add(new TimezoneUse(new TimeWithDayAttr(2), new TimeWithDayAttr(3), UseSetting.USE, 0));
		new Expectations() {
			{
				require.findByPK(workInformation.getWorkTypeCode().v());
				result = Optional.of(new WorkType());

				require.findByCode(anyString);
				result = Optional.of(new WorkTimeSetting());

				require.getPredeterminedTimezone(anyString, anyString, null).getTimezones();
				result = listTimezoneUse;

			}
		};

		assertThat(workInformation.getWorkInfoAndTimeZone(require).isPresent()).isTrue();
	}

}
