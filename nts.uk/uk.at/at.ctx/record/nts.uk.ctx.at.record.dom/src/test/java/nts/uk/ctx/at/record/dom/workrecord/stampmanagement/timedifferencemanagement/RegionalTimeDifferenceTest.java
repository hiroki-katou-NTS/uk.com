package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timedifferencemanagement;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;

/**
 * 
 * @author chungnt
 *
 */

public class RegionalTimeDifferenceTest {

	@Test
	public void getters() {
		RegionalTimeDifference regionalTimeDifference = new RegionalTimeDifference(00001, "dummy", 2);
		NtsAssert.invokeGetters(regionalTimeDifference);
	}

}
