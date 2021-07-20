package nts.uk.ctx.at.shared.dom.scherec.taskmanagement.domainservice;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskassign.taskassignworkplace.NarrowingDownTaskByWorkplace;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskassign.taskassignworkplace.NarrowingDownTaskByWorkplaceFromEmployeesService;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameUsageSetting;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.Task;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.TaskCode;

/**
 * 
 * @author chungnt
 *
 */

@RunWith(JMockit.class)
public class GetWorkAvailableToEmployeesServiceTest {

	@Injectable
	private GetWorkAvailableToEmployeesService.Require require;

	private String companyID = "companyID";
	private String employeeID = "employeeID";
	private GeneralDate date = GeneralDate.today();
	private TaskFrameNo taskFrameNo = new TaskFrameNo(1);
	private TaskFrameNo taskFrameNo2 = new TaskFrameNo(2);

	private TaskFrameUsageSetting taskFrameUsageSetting = GetWorkAvailableToEmployeesServiceHelper.getTask();
	private Task task = GetWorkAvailableToEmployeesServiceHelper.getTaskDefault();
	private NarrowingDownTaskByWorkplace narrowingDown = GetWorkAvailableToEmployeesServiceHelper.getNarrowingDown();
	private List<Task> tasks = new ArrayList<>();

	// $作業枠利用設定 isNull
	@Test
	public void test_1() {

		new Expectations() {
			{
				require.getTask();
			}
		};

		List<Task> result = GetWorkAvailableToEmployeesService.get(require, companyID, employeeID, date, taskFrameNo,
				Optional.empty());
		assertThat(result.isEmpty()).isTrue();
	}

	// $作業枠利用設定 isNotNull
	// 作業枠NO <> 1 AND 上位枠作業コード.isPresent
	// if $職場別作業の絞込.isNotPresent()
	// $親作業 isnotPresent
	// return require.getTask(date, Arrays.asList(taskFrameNo));
	@Test
	public void test_2() {

		List<TaskFrameNo> taskFrameNo = new ArrayList<>();
		taskFrameNo.add(new TaskFrameNo(2));

		tasks.add(task);

		new Expectations() {
			{
				require.getTask();
				result = taskFrameUsageSetting;

				require.getTask(date, taskFrameNo);
				result = tasks;
			}
		};

		List<Task> result = GetWorkAvailableToEmployeesService.get(require, companyID, employeeID, date,
				new TaskFrameNo(2), Optional.of(new TaskCode("DUMMY")));
		assertThat(result.isEmpty()).isFalse();
		assertThat(result.size()).isEqualTo(1);
		assertThat(result.get(0).getCode().v()).isEqualTo("DUMMY");
		assertThat(result.get(0).getTaskFrameNo().v()).isEqualTo(2);
	}

	// return require.getTask(date, Arrays.asList(taskFrameNo));
	// if 子作業.isPresent AND $絞込作業.isPresent
	@Test
	public void test_3() {

		tasks.add(task);
		tasks.add(task);
		tasks.add(task);

		new Expectations() {
			{
				require.getTask();
				result = taskFrameUsageSetting;

				require.getOptionalTask(taskFrameNo, new TaskCode("DUMMY"));
				result = Optional.of(task);

				require.getListTask(date, taskFrameNo2, task.getChildTaskList());
				result = tasks;

			}
		};

		new MockUp<NarrowingDownTaskByWorkplaceFromEmployeesService>() {
			@Mock
			public Optional<NarrowingDownTaskByWorkplace> get(
					nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskassign.taskassignworkplace.NarrowingDownTaskByWorkplaceFromEmployeesService.Require require,
					String companyID, String employeeID, GeneralDate date, TaskFrameNo taskFrameNo) {
				return Optional.of(narrowingDown);
			}
		};

		List<Task> result = GetWorkAvailableToEmployeesService.get(require, companyID, employeeID, date,
				new TaskFrameNo(2), Optional.of(new TaskCode("DUMMY")));
		assertThat(result.isEmpty()).isFalse();
		assertThat(result.size()).isEqualTo(3);
		assertThat(result.get(0).getCode().v()).isEqualTo("DUMMY");
		assertThat(result.get(0).getTaskFrameNo().v()).isEqualTo(2);
	}

	// return require.getTask(date, Arrays.asList(taskFrameNo));
	// if !子作業.isPresent AND !$絞込作業.isPresent
//	@Test
//	public void test_4() {
//		tasks.add(task);
//		tasks.add(task);
//		tasks.add(task);
//
//		Optional<Task> optTask = Optional.empty();
//
//		List<TaskCode> childTaskListfilter = new ArrayList<TaskCode>();
//
//		childTaskListfilter.add(new TaskCode("DUMMY"));
//
//		new Expectations() {
//			{
//				require.getOptionalTask(new TaskFrameNo(2), new TaskCode("DUMMY"));
//				result = optTask; // => childTaskList == empty
//
//				require.getTask();
//				result = taskFrameUsageSetting;
//
//				require.getListTask(date, taskFrameNo2, childTaskListfilter);
//				result = tasks;
//			}
//		};
//
//		new MockUp<NarrowingDownTaskByWorkplaceFromEmployeesService>() {
//			@Mock
//			public Optional<NarrowingDownTaskByWorkplace> get(
//					nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskassign.taskassignworkplace.NarrowingDownTaskByWorkplaceFromEmployeesService.Require require,
//					String companyID, String employeeID, GeneralDate date, TaskFrameNo taskFrameNo) {
//				return Optional.of(narrowingDown);
//			}
//		};
//
//		List<Task> result = GetWorkAvailableToEmployeesService.get(require, companyID, employeeID, date,
//				new TaskFrameNo(2), Optional.of(new TaskCode("DUMMY")));
//
//		assertThat(result.isEmpty()).isFalse();
//		assertThat(result.size()).isEqualTo(3);
//		assertThat(result.get(0).getCode().v()).isEqualTo("DUMMY");
//		assertThat(result.get(0).getTaskFrameNo().v()).isEqualTo(2);
//	}
}
