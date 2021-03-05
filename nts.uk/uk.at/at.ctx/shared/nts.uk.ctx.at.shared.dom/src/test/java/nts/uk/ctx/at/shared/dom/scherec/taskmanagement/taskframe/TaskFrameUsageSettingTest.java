package nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe;

import lombok.val;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class TaskFrameUsageSettingTest {
	@Test
	public void getters() {
		val task = Helper.createTaskFrameUsageSetting();
		NtsAssert.invokeGetters(task);
	}
	
	public static class Helper{
		
		public static TaskFrameUsageSetting createTaskFrameUsageSetting() {
			List<TaskFrameSetting> settings = new ArrayList<>();
			settings.add(new TaskFrameSetting(1, "Frame 1", 1));
			settings.add(new TaskFrameSetting(2, "Frame 1", 1));
			settings.add(new TaskFrameSetting(3, "Frame 1", 1));
			settings.add(new TaskFrameSetting(4, "Frame 1", 1));
			settings.add(new TaskFrameSetting(5, "Frame 1", 1));
			return new TaskFrameUsageSetting(settings);
		}
		
	}
}
