package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.work;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.enums.EnumAdaptor;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.personallaborcondition.UseAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.work.WorkGroup.Require;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameName;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameSetting;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameUsageSetting;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.Task;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.TaskCode;

/**
 * 
 * @author chungnt
 *
 */

@RunWith(JMockit.class)
public class WorkGroupTest {

	@Injectable
	private Require require;

	List<TaskFrameSetting> frameSettingList = new ArrayList<>();

	TaskFrameNo taskFrameNo = new TaskFrameNo(1);

	GeneralDate date = GeneralDate.today();

	WorkCode code = new WorkCode("WorkCode");

	@Test
	public void testC0() {
		WorkGroup workGroup = new WorkGroup(new WorkCode("WorkCode"), Optional.of(new WorkCode("WorkCode")),
				Optional.of(new WorkCode("WorkCode")), Optional.of(new WorkCode("WorkCode")),
				Optional.of(new WorkCode("WorkCode")));
		NtsAssert.invokeGetters(workGroup);
	}

	//
	@Test
	public void testContracter1() {
		WorkGroup workGroup = WorkGroup.create(new WorkCode("WorkCode"), Optional.of(new WorkCode("WorkCode1")),
				Optional.of(new WorkCode("WorkCode2")), Optional.of(new WorkCode("WorkCode3")),
				Optional.of(new WorkCode("WorkCode4")));

		assertThat(workGroup.getWorkCD1().v()).isEqualTo("WorkCode");
		assertThat(workGroup.getWorkCD2().get().v()).isEqualTo("WorkCode1");
		assertThat(workGroup.getWorkCD3().get().v()).isEqualTo("WorkCode2");
		assertThat(workGroup.getWorkCD4().get().v()).isEqualTo("WorkCode3");
		assertThat(workGroup.getWorkCD5().get().v()).isEqualTo("WorkCode4");
	}

	//
	@Test
	public void testContracter2() {
		WorkGroup workGroup = WorkGroup.create("WorkCode", "WorkCode1", "WorkCode2", "WorkCode3", "WorkCode4");

		assertThat(workGroup.getWorkCD1().v()).isEqualTo("WorkCode");
		assertThat(workGroup.getWorkCD2().get().v()).isEqualTo("WorkCode1");
		assertThat(workGroup.getWorkCD3().get().v()).isEqualTo("WorkCode2");
		assertThat(workGroup.getWorkCD4().get().v()).isEqualTo("WorkCode3");
		assertThat(workGroup.getWorkCD5().get().v()).isEqualTo("WorkCode4");
	}

	// if(workContent.isPresent()) return false;
	@Test
	public void testPublic1_1() {

		WorkGroup workGroup = WorkGroup.create("WorkCode", "WorkCode1", "WorkCode2", "WorkCode3", "WorkCode4");

		frameSettingList.add(
				new TaskFrameSetting(taskFrameNo, new TaskFrameName("DUMMY"), EnumAdaptor.valueOf(1, UseAtr.class)));
		frameSettingList.add(
				new TaskFrameSetting(taskFrameNo, new TaskFrameName("DUMMY1"), EnumAdaptor.valueOf(0, UseAtr.class)));

		TaskFrameUsageSetting taskFrameUsageSetting = new TaskFrameUsageSetting(frameSettingList);

		new Expectations() {
			{
				require.getTaskFrameUsageSetting();
				result = taskFrameUsageSetting;
			}
		};

		boolean b = workGroup.checkWorkContents(require);
		assertThat(b).isFalse();
	}

	// // if(!workContent.isPresent()) return true;
	@Test
	public void testPublic1_2() {

		WorkGroup workGroup = WorkGroup.create("WorkCode", null, null, null, null);
		Task task = new Task(new TaskCode("Code"), taskFrameNo, null, new ArrayList<>(), new DatePeriod(date, date),
				null);

		frameSettingList.add(
				new TaskFrameSetting(taskFrameNo, new TaskFrameName("DUMMY"), EnumAdaptor.valueOf(1, UseAtr.class)));
		frameSettingList.add(
				new TaskFrameSetting(taskFrameNo, new TaskFrameName("DUMMY1"), EnumAdaptor.valueOf(0, UseAtr.class)));

		TaskFrameUsageSetting taskFrameUsageSetting = new TaskFrameUsageSetting(frameSettingList);

		new Expectations() {
			{
				require.getTaskFrameUsageSetting();
				result = taskFrameUsageSetting;

				require.getTask(taskFrameNo, new WorkCode("WorkCode"));
				result = Optional.ofNullable(task);
			}
		};

		boolean b = workGroup.checkWorkContents(require);
		assertThat(b).isTrue();
	}

	// [2] 作業内容の有効期限を確認する => Error
	@Test
	public void testPublic2_1() {

		frameSettingList.add(
				new TaskFrameSetting(taskFrameNo, new TaskFrameName("DUMMY"), EnumAdaptor.valueOf(1, UseAtr.class)));
		frameSettingList.add(
				new TaskFrameSetting(taskFrameNo, new TaskFrameName("DUMMY1"), EnumAdaptor.valueOf(0, UseAtr.class)));

		WorkGroup workGroup = WorkGroup.create("WorkCode", null, null, null, null);

		TaskFrameUsageSetting taskFrameUsageSetting = new TaskFrameUsageSetting(frameSettingList);

		new Expectations() {
			{
				require.getTask(taskFrameNo, code);
				result = Optional.empty();

				require.getTaskFrameUsageSetting();
				result = taskFrameUsageSetting;
			}
		};

		NtsAssert.businessException("Msg_2080", () -> workGroup.checkExpirationDate(require, date));
	}

	// [2] 作業内容の有効期限を確認する => NotError
	@Test
	public void testPublic2_2() {

		frameSettingList.add(
				new TaskFrameSetting(taskFrameNo, new TaskFrameName("DUMMY"), EnumAdaptor.valueOf(1, UseAtr.class)));
		frameSettingList.add(
				new TaskFrameSetting(taskFrameNo, new TaskFrameName("DUMMY1"), EnumAdaptor.valueOf(0, UseAtr.class)));

		WorkGroup workGroup = WorkGroup.create("WorkCode", null, null, null, null);

		Task task = new Task(new TaskCode("Code"), taskFrameNo, null, new ArrayList<>(), new DatePeriod(date, date),
				null);

		TaskFrameUsageSetting taskFrameUsageSetting = new TaskFrameUsageSetting(frameSettingList);

		new Expectations() {
			{
				require.getTask(taskFrameNo, code);
				result = Optional.of(task);

				require.getTaskFrameUsageSetting();
				result = taskFrameUsageSetting;
			}
		};

		workGroup.checkExpirationDate(require, date);
	}

	// if(!this.workCD1.v().equals(workGroup.getWorkCD1().v()))
	@Test
	public void testMethodCompare1() {
		WorkGroup workGroup = WorkGroup.create("WorkCode", null, null, null, null);
		WorkGroup workGroup1 = WorkGroup.create("WorkCode1", null, null, null, null);

		assertThat(workGroup.compare(workGroup1)).isFalse();
	}

	// if (!StringUtils.equals(this.workCD2.map(x ->
	// x.v()).orElse(null),workGroup.workCD2.map(x -> x.v()).orElse(null)))
	@Test
	public void testMethodCompare2() {
		WorkGroup workGroup = WorkGroup.create("WorkCode", "WorkCode2", null, null, null);
		WorkGroup workGroup1 = WorkGroup.create("WorkCode", null, null, null, null);

		assertThat(workGroup.compare(workGroup1)).isFalse();
	}

	// if (!StringUtils.equals(this.workCD3.map(x ->
	// x.v()).orElse(null),workGroup.workCD3.map(x -> x.v()).orElse(null)))
	@Test
	public void testMethodCompare3() {
		WorkGroup workGroup = WorkGroup.create("WorkCode", "WorkCode2", "WorkCode3", null, null);
		WorkGroup workGroup1 = WorkGroup.create("WorkCode", "WorkCode2", null, null, null);

		assertThat(workGroup.compare(workGroup1)).isFalse();
	}

	// if (!StringUtils.equals(this.workCD4.map(x ->
	// x.v()).orElse(null),workGroup.workCD4.map(x -> x.v()).orElse(null)))
	@Test
	public void testMethodCompare4() {
		WorkGroup workGroup = WorkGroup.create("WorkCode", "WorkCode2", "WorkCode3", "WorkCode4", null);
		WorkGroup workGroup1 = WorkGroup.create("WorkCode", "WorkCode2", "WorkCode3", null, null);

		assertThat(workGroup.compare(workGroup1)).isFalse();
	}

	// if (!StringUtils.equals(this.workCD5.map(x ->
	// x.v()).orElse(null),workGroup.workCD5.map(x -> x.v()).orElse(null)))
	@Test
	public void testMethodCompare5() {
		WorkGroup workGroup = WorkGroup.create("WorkCode", "WorkCode2", "WorkCode3", "WorkCode4", "WorkCode5");
		WorkGroup workGroup1 = WorkGroup.create("WorkCode", "WorkCode2", "WorkCode3", "WorkCode4", null);

		assertThat(workGroup.compare(workGroup1)).isFalse();
	}

	// Return True
	@Test
	public void testMethodCompare6() {
		WorkGroup workGroup = WorkGroup.create("WorkCode", "WorkCode2", "WorkCode3", "WorkCode4", "WorkCode5");
		WorkGroup workGroup1 = WorkGroup.create("WorkCode", "WorkCode2", "WorkCode3", "WorkCode4", "WorkCode5");

		assertThat(workGroup.compare(workGroup1)).isTrue();
	}
}
