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
		CheckErrorType data1 = CheckErrorType.valueOf(9);
	}
}
