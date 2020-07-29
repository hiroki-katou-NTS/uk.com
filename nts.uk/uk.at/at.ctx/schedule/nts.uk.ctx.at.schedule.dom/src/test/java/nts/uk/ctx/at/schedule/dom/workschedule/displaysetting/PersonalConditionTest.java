package nts.uk.ctx.at.schedule.dom.workschedule.displaysetting;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
/**
 * 
 * @author HieuLt
 *
 */
@RunWith(JMockit.class)
public class PersonalConditionTest {
	
	
	@Test
	public void getter(){
		NtsAssert.invokeGetters(PersonalConditionHelper.getData());
	}
}
