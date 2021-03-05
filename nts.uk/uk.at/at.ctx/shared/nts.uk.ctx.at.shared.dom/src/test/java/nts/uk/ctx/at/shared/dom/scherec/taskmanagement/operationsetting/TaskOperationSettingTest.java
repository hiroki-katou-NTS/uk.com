package nts.uk.ctx.at.shared.dom.scherec.taskmanagement.operationsetting;

import lombok.val;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.*;
import org.junit.Test;

import java.util.Collections;
import java.util.Optional;

public class TaskOperationSettingTest {
	@Test
	public void getters() {
		val task = Helper.createTaskOperationSetting();
		NtsAssert.invokeGetters(task);
	}
	
	public static class Helper{
		public static TaskOperationSetting createTaskOperationSetting() {
			TaskOperationMethod operationMethod = TaskOperationMethod.USE_ON_SCHEDULE;
			return new TaskOperationSetting(operationMethod.value);
		}
		
	}
}
