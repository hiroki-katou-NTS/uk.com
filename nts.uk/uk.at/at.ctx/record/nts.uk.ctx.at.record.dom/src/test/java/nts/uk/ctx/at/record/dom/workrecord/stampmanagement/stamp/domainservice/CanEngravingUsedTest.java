package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;

/**
 * 
 * @author chungnt
 *
 */

public class CanEngravingUsedTest {

	@Test
	public void getters() {
		CanEngravingUsed canEngravingUsed = CanEngravingUsed.valueOf(0);
		NtsAssert.invokeGetters(canEngravingUsed);
	}

}
