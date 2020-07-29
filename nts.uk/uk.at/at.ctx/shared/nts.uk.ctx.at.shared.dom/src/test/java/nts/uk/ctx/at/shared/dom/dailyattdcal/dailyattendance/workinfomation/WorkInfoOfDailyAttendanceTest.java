package nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.workinfomation;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance.Require;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.DayOfWeek;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;

@RunWith(JMockit.class)
public class WorkInfoOfDailyAttendanceTest {
	@Injectable
	private Require require;

	@Test
	public void getters() {
		WorkInfoOfDailyAttendance workInfoOfDailyAttendance = new WorkInfoOfDailyAttendance(null, null, null, NotUseAttribute.Not_use, NotUseAttribute.Not_use,
				DayOfWeek.FRIDAY, new ArrayList<>());
		NtsAssert.invokeGetters(workInfoOfDailyAttendance);
	}
	
	/**
	 * 勤務実績の勤務情報.出勤・休日系の判定(require) == optional.empty
	 */
	@Test
	public void testGetWorkStyle() {
		WorkInformation recordInfo1 = new WorkInformation("ti1", "ty1");
		WorkInfoOfDailyAttendance workInfoOfDailyAttendance = new WorkInfoOfDailyAttendance(recordInfo1, null, null, NotUseAttribute.Not_use, NotUseAttribute.Not_use,
				DayOfWeek.FRIDAY, new ArrayList<>());
		new MockUp<WorkInformation>() {
			@Mock
			public Optional<WorkStyle> getWorkStyle(WorkInformation.Require require) {
				return Optional.empty();
			}
		};
		Optional<WorkStyle> workstyle = workInfoOfDailyAttendance.getWorkStyle(require);
		assertThat(workstyle.isPresent()).isFalse();
	}
	
	/**
	 * 勤務実績の勤務情報.出勤・休日系の判定(require) == WorkStyle.ONE_DAY_REST
	 */
	@Test
	public void testGetWorkStyle_1() {
		WorkInformation recordInfo1 = new WorkInformation("ti1", "ty1");
		WorkInfoOfDailyAttendance workInfoOfDailyAttendance = new WorkInfoOfDailyAttendance(recordInfo1, null, null, NotUseAttribute.Not_use, NotUseAttribute.Not_use,
				DayOfWeek.FRIDAY, new ArrayList<>());
		new MockUp<WorkInformation>() {
			@Mock
			public Optional<WorkStyle> getWorkStyle(WorkInformation.Require require) {
				return Optional.of(WorkStyle.ONE_DAY_REST);
			}
		};
		Optional<WorkStyle> workstyle = workInfoOfDailyAttendance.getWorkStyle(require);
		assertThat(workstyle.get().equals(WorkStyle.ONE_DAY_REST)).isTrue();
	}
	
	/**
	 * 勤務実績の勤務情報.出勤・休日系の判定(require) == WorkStyle.MORNING_WORK
	 */
	@Test
	public void testGetWorkStyle_2() {
		WorkInformation recordInfo1 = new WorkInformation("ti1", "ty1");
		WorkInfoOfDailyAttendance workInfoOfDailyAttendance = new WorkInfoOfDailyAttendance(recordInfo1, null, null, NotUseAttribute.Not_use, NotUseAttribute.Not_use,
				DayOfWeek.FRIDAY, new ArrayList<>());
		new MockUp<WorkInformation>() {
			@Mock
			public Optional<WorkStyle> getWorkStyle(WorkInformation.Require require) {
				return Optional.of(WorkStyle.MORNING_WORK);
			}
		};
		Optional<WorkStyle> workstyle = workInfoOfDailyAttendance.getWorkStyle(require);
		assertThat(workstyle.get().equals(WorkStyle.MORNING_WORK)).isTrue();
	}
	
	/**
	 * 勤務実績の勤務情報.出勤・休日系の判定(require) == WorkStyle.AFTERNOON_WORK
	 */
	@Test
	public void testGetWorkStyle_3() {
		WorkInformation recordInfo1 = new WorkInformation("ti1", "ty1");
		WorkInfoOfDailyAttendance workInfoOfDailyAttendance = new WorkInfoOfDailyAttendance(recordInfo1, null, null, NotUseAttribute.Not_use, NotUseAttribute.Not_use,
				DayOfWeek.FRIDAY, new ArrayList<>());
		new MockUp<WorkInformation>() {
			@Mock
			public Optional<WorkStyle> getWorkStyle(WorkInformation.Require require) {
				return Optional.of(WorkStyle.AFTERNOON_WORK);
			}
		};
		Optional<WorkStyle> workstyle = workInfoOfDailyAttendance.getWorkStyle(require);
		assertThat(workstyle.get().equals(WorkStyle.AFTERNOON_WORK)).isTrue();
	}
	
	/**
	 * 勤務実績の勤務情報.出勤・休日系の判定(require) == WorkStyle.ONE_DAY_WORK
	 */
	@Test
	public void testGetWorkStyle_4() {
		WorkInformation recordInfo1 = new WorkInformation("ti1", "ty1");
		WorkInfoOfDailyAttendance workInfoOfDailyAttendance = new WorkInfoOfDailyAttendance(recordInfo1, null, null, NotUseAttribute.Not_use, NotUseAttribute.Not_use,
				DayOfWeek.FRIDAY, new ArrayList<>());
		new MockUp<WorkInformation>() {
			@Mock
			public Optional<WorkStyle> getWorkStyle(WorkInformation.Require require) {
				return Optional.of(WorkStyle.ONE_DAY_WORK);
			}
		};
		Optional<WorkStyle> workstyle = workInfoOfDailyAttendance.getWorkStyle(require);
		assertThat(workstyle.get().equals(WorkStyle.ONE_DAY_WORK)).isTrue();
	}

}
