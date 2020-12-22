package nts.uk.ctx.at.schedulealarm.dom.alarmcheck;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;

@RunWith(JMockit.class)
public class AlarmCheckMsgContentTest {
	@Test
	public void getters() {
		val msgContent =  new AlarmCheckMsgContent(new AlarmCheckMessage("default_msg"), new AlarmCheckMessage("fix_msg"));

		NtsAssert.invokeGetters(msgContent);

	}
	
	@Test
	public void create_alarmCheckMsgContent_success() {
		val msgContent =  new AlarmCheckMsgContent(new AlarmCheckMessage("default_msg"), new AlarmCheckMessage("fix_msg"));
		
		assertThat(msgContent.getDefaultMsg().v()).isEqualTo("default_msg");
		assertThat(msgContent.getMessage().v()).isEqualTo("fix_msg");
	}
}
