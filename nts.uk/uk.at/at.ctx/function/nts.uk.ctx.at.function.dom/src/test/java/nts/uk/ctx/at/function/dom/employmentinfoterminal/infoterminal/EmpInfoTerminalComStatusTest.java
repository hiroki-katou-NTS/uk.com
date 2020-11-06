package nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;

/**
 * 
 * @author dungbn
 *
 */
@RunWith(JMockit.class)
public class EmpInfoTerminalComStatusTest {
	
	@Test
	public void getters() {
		EmpInfoTerminalComStatus empInfoTerminalComStatus = EmpInfoTerminalComStatusHelper.createEmpInfoTerminalComStatus();
		NtsAssert.invokeGetters(empInfoTerminalComStatus);
	}

}
