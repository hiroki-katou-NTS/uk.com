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
		RegionalTimeDifference regionalTimeDifference = new RegionalTimeDifference(new RegionCode(00001),
				new RegionName("dummy"), new RegionalTime(2));
		NtsAssert.invokeGetters(regionalTimeDifference);
	}

}
