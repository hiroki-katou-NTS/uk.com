package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.referstoinformation;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.referstoinformation.FingerType;

public class FingerTypeTest {

	/**
	 *  @author chungnt
	 */
	
	@Test
	public void getters() {
		FingerType fingerType = FingerType.valueOf(0);
		NtsAssert.invokeGetters(fingerType);
	}
	
}
