package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.toppagealarm;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;

/**
 * 
 * @author chungnt
 *
 */

public class TopPageArmDetailTest {

	@Test
	public void getters() {
		TopPageAlarmDetail topPageAlarmDetail = new TopPageAlarmDetail("DUMMY",
				1, //dummy
				"DUMMY");
		
		NtsAssert.invokeGetters(topPageAlarmDetail);
	}

}
