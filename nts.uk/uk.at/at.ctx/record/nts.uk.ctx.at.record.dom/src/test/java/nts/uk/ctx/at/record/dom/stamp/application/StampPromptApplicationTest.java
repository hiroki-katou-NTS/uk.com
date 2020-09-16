package nts.uk.ctx.at.record.dom.stamp.application;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;

/**
 * 
 * @author chungnt
 *
 */

public class StampPromptApplicationTest {

	@Test
	public void getter() {
		StampPromptApplication stampPromptApplication = StampPromptApplicationHelper.getDefault();
		NtsAssert.invokeGetters(stampPromptApplication);
	}
	
	@Test
	public void test_getErrorListApply() {
		StampPromptApplication stampPromptApplication = StampPromptApplicationHelper.getDefault();
		
		List<ErrorInformationApplication> lstError = stampPromptApplication.getErrorListApply();
		
		assertThat(lstError.get(0).getErrorAlarmCode().get(0).v()).isEqualTo("D001");
		assertThat(lstError.get(0).getErrorAlarmCode().get(1).v()).isEqualTo("D003");
	}
}
