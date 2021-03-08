package nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskassign.taskassignemployee;

import lombok.val;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.TaskCode;
import org.junit.Test;

public class TaskAssignEmployeeTest {
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
