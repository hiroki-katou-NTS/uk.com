package nts.uk.ctx.at.record.dom.stamp.application;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.record.dom.stamp.application.StamPromptApplicationHelper.StamPrompt;

/**
 * 
 * @author phongtq
 *
 */
@RunWith(JMockit.class)
public class StamPromptApplicationTest {

	@Test
	public void getters() {
		StampPromptApplication settingPerson = StamPrompt.DUMMY;
		NtsAssert.invokeGetters(settingPerson);
	}
	
	@Test
	public void testEnum() {
		CheckErrorType data = CheckErrorType.IMPRINT_LEAKAGE;
		assertThat(data.getErrorAlarm().get(0)).isEqualTo("S001");
		CheckErrorType data1 = CheckErrorType.HOKIDAY_EMBOSSING;
		assertThat(data1.getErrorAlarm().get(0)).isEqualTo("S005");
		CheckErrorType data2 = CheckErrorType.OVERTIME_DIVERGGENCE;
		assertThat(data2.getErrorAlarm().get(0)).isEqualTo("D001", "D003");
	}
	
	@Test
	public void testEnum_1() {
		CheckErrorType data = CheckErrorType.valueOf(0);
		assertThat(data.value).isEqualTo(0);
	}
}
