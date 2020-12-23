package nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster;

import java.util.Collections;
import java.util.Optional;

import org.junit.Test;

import lombok.val;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;

public class TaskTest {
	@Test
	public void getters() {
		val task = Helper.createTask();
		NtsAssert.invokeGetters(task);
	}
	
	public static class Helper{
		
		public static Task createTask() {
			val frameNo = new TaskFrameNo(1);
			val code = new TaskCode("code");
			val expriationDate = new DatePeriod(GeneralDate.ymd(2020, 1, 1), GeneralDate.ymd(9999, 12, 31));
			val displayInfo = new TaskDisplayInfo(new TaskName("name"), new TaskAbName("abName"), Optional.empty(), Optional.empty());
			val extCooperationInfo = new ExternalCooperationInfo(
					  Optional.ofNullable(new TaskExternalCode("01"))
					, Optional.ofNullable(new TaskExternalCode("02"))
					, Optional.ofNullable(new TaskExternalCode("03"))
					, Optional.empty(), Optional.empty()
					);
			
			return new Task(frameNo, code, expriationDate, displayInfo, extCooperationInfo, Collections.emptyList());
		}
		
	}
}
