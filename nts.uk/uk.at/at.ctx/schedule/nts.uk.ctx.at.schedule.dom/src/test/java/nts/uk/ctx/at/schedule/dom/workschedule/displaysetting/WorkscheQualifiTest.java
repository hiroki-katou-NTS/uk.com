package nts.uk.ctx.at.schedule.dom.workschedule.displaysetting;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;


@RunWith(JMockit.class)
public class WorkscheQualifiTest {
	
	@Test
	public void getter(){
		WorkscheQualifi target = WorkscheQualifiHelper.Dummy;
		NtsAssert.invokeGetters(target);
	}
	@Test
	public void getMsg(){
		//Lam tiep di ban =))
	}

}
