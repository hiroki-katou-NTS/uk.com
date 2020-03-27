package nts.uk.ctx.at.record.dom.stamp.application;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.record.dom.stamp.application.StamPromptApplicationHelper.StamResult;
/**
 * 
 * @author phongtq
 *
 */
public class StampResultDisplayTest {
	@Test
	public void getters() {
		StampResultDisplay settingPerson = StamResult.DUMMY;
		NtsAssert.invokeGetters(settingPerson);
	}
}
