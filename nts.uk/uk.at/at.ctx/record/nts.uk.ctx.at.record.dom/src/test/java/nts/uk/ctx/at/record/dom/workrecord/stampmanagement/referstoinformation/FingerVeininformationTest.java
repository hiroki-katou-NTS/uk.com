package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.referstoinformation;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.referstoinformation.FingerVeininformation;

/**
 * 
 * @author chungnt
 *
 */

public class FingerVeininformationTest {

	@Test
	public void getters() {
		FingerVeininformation fingerVeininformation = ReferstoinformationHelper.getFingerVeininformationDefault();
		NtsAssert.invokeGetters(fingerVeininformation);
	}

}
