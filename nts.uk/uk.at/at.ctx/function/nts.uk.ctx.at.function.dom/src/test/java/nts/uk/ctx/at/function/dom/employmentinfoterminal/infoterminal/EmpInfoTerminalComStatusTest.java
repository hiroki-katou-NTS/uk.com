package nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDateTime;

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
	
	@Test
	public void isCommunicationError() {
		val intervalTime = new MonitorIntervalTime(1);
		EmpInfoTerminalComStatus empInfoTerminalComStatus = EmpInfoTerminalComStatusHelper.createEmpInfoTerminalComStatus();
		val result = empInfoTerminalComStatus.getSignalLastTime().addMinutes(intervalTime.v()).compareTo(GeneralDateTime.now()) < 0;
		assertThat(result).isFalse();
	}
}
