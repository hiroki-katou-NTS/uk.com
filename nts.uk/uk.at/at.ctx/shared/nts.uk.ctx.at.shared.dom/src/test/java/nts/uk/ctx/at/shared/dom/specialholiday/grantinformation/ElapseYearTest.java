package nts.uk.ctx.at.shared.dom.specialholiday.grantinformation;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;

@RunWith(JMockit.class)
public class ElapseYearTest {
	
	@Test
	public void getters() {
		ElapseYear elapseYear = ElapseYearHelper.createElapseYear();
		NtsAssert.invokeGetters(elapseYear);
	}
}
