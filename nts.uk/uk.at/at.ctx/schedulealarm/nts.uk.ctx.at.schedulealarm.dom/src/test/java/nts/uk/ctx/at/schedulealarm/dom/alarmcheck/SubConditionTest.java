package nts.uk.ctx.at.schedulealarm.dom.alarmcheck;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;

@RunWith(JMockit.class)
public class SubConditionTest {
	@Test
	public void getters() {
		val subCond = new SubCondition(new SubCode("sub_code"),
				new AlarmCheckMsgContent(new AlarmCheckMessage("default_msg"), new AlarmCheckMessage("fix_msg")),
				"explanation");
		NtsAssert.invokeGetters(subCond);

	}

	@Test
	public void create_subCondition_success() {
		
		val subCond = new SubCondition(new SubCode("01"),
				new AlarmCheckMsgContent(new AlarmCheckMessage("default_msg"), new AlarmCheckMessage("fix_msg")),
				"explanation");
		
		assertThat(subCond.getSubCode().v()).isEqualTo("01");
		assertThat(subCond.getMessage().getDefaultMsg().v()).isEqualTo("default_msg");
		assertThat(subCond.getMessage().getMessage().v()).isEqualTo("fix_msg");
		assertThat(subCond.getExplanation()).isEqualTo("explanation");
	
	}
}
