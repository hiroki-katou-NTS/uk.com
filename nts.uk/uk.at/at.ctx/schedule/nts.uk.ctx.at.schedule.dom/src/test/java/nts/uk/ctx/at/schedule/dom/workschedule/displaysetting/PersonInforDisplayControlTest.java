package nts.uk.ctx.at.schedule.dom.workschedule.displaysetting;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;

/**
 * 
 * @author HieuLt
 *
 */
public class PersonInforDisplayControlTest {

	@Test
	public void getter(){
		PersonInforDisplayControl target = PersonInforDisplayControlHelper.Dummy;
		NtsAssert.invokeGetters(target);
	}
}
