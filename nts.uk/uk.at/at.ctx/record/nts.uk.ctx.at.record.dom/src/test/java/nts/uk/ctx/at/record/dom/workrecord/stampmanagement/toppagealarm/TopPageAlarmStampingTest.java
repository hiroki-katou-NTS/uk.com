package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.toppagealarm;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;

public class TopPageAlarmStampingTest {

	@Test
	public void getters() {
		TopPageAlarmStamping topPageAlarmStamping = TopPageAlarmStampingHelper.getDefault();
		NtsAssert.invokeGetters(topPageAlarmStamping);
	}
	


}
