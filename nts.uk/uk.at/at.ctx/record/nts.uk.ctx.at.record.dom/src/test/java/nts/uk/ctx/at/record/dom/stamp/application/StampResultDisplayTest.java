package nts.uk.ctx.at.record.dom.stamp.application;

import java.util.Arrays;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.shr.com.enumcommon.NotUseAtr;
/**
 * 
 * @author phongtq
 *
 */
public class StampResultDisplayTest {
	@Test
	public void getters() {
		StampResultDisplay settingPerson = new StampResultDisplay(
				"000000000000-0001", 
				 NotUseAtr.valueOf(1),
				 Arrays.asList(123458, 122333));
		NtsAssert.invokeGetters(settingPerson);
	}
}
