package nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskassign.taskassignemployee;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.error.BusinessException;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.domainservice.CheckExistenceMasterDomainService;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.TaskCode;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JMockit.class)
public class TaskAssignEmployeeTest {
	@Injectable
	TaskAssignEmployee.Require require;

	@Test
	public void testCreateSuccess() {
		new Expectations(CheckExistenceMasterDomainService.class) {{
			CheckExistenceMasterDomainService.checkExistenceTaskMaster(
					require,
					new TaskFrameNo(1),
					Arrays.asList(new TaskCode("A0000000000000000001"))
			);
		}};
		TaskAssignEmployee instance = TaskAssignEmployee.create(require, "00000000000000000000000000000", 1, "A0000000000000000001");
		assertThat(instance.getTaskFrameNo().v() == 1);
	}

	@Test
	public void testCreateFail() {
		new Expectations(CheckExistenceMasterDomainService.class) {{
			CheckExistenceMasterDomainService.checkExistenceTaskMaster(
					require,
					new TaskFrameNo(1),
					Arrays.asList(new TaskCode("A0000000000000000001"))
			);
			result = new BusinessException("Msg_2065");
		}};
		NtsAssert.businessException("Msg_2065", () -> TaskAssignEmployee.create(
				require,
				"00000000000000000000000000000",
				1,
				"A0000000000000000001"
		));
	}

	@Test
	public void getters() {
		val task = Helper.createTaskAssignEmployee();
		NtsAssert.invokeGetters(task);
	}
	
	public static class Helper{
		public static TaskAssignEmployee createTaskAssignEmployee() {
			String employeeId = "00000000000000";
			val frameNo = new TaskFrameNo(1);
			val code = new TaskCode("code");
			return new TaskAssignEmployee(employeeId, frameNo, code);
		}
		
	}
}
