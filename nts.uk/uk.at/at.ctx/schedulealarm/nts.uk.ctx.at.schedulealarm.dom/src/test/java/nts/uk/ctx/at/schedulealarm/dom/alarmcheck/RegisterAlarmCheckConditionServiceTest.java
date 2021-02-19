package nts.uk.ctx.at.schedulealarm.dom.alarmcheck;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.schedulealarm.dom.alarmcheck.RegisterAlarmCheckConditionService.Require;;

@RunWith(JMockit.class)
public class RegisterAlarmCheckConditionServiceTest {
	@Injectable
	private Require require;
	
	@Test
	public void testRegister_success_1() {
		NtsAssert.atomTask(
				() -> RegisterAlarmCheckConditionService.register(
						require,
						new AlarmCheckConditionScheduleCode("01"),
						Arrays.asList(
								new MessageInfo(new SubCode("01"), new AlarmCheckMessage("updated1")),
								new MessageInfo(new SubCode("02"), new AlarmCheckMessage("updated2")),
								new MessageInfo(new SubCode("03"), new AlarmCheckMessage("updated3")),
								new MessageInfo(new SubCode("04"), new AlarmCheckMessage("updated4"))
								)), 
				any -> require.updateMessage(any.get()));
	}
}
