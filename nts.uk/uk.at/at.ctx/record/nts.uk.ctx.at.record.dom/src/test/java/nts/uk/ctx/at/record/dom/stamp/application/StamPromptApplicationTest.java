package nts.uk.ctx.at.record.dom.stamp.application;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.assertj.core.api.Assertions.assertThat;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.common.color.ColorCode;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 
 * @author phongtq
 *
 */
@RunWith(JMockit.class)
public class StamPromptApplicationTest {

	@Test
	public void getters() {
		StamPromptApplication settingPerson = new StamPromptApplication(
				"000000000000-0001", 
				Arrays.asList(
						new StampRecordDis(
								NotUseAtr.valueOf(1), 
								CheckErrorType.valueOf(1), 
								new PromptingMessage(
										new MessageContent("DUMMY"), 
										new ColorCode("#DUMMY")))));
		NtsAssert.invokeGetters(settingPerson);
	}
	
	@Test
	public void testEnum() {
		assertThat(CheckErrorType.getErrorAlarm(0).get(0)).isEqualTo("S001");
		assertThat(CheckErrorType.getErrorAlarm(1).get(0)).isEqualTo("S005");
		assertThat(CheckErrorType.getErrorAlarm(2).get(0)).isEqualTo("D001", "D003");
	}
}
