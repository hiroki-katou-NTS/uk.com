package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.toppagealarm;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;

public class TopPageAlarmStampingTest {

	@Test
	public void getters() {
		TopPageAlarmStamping topPageAlarmStamping = TopPageAlarmStampingHelper.getDefault();
		NtsAssert.invokeGetters(topPageAlarmStamping);
	}
	
	@Test
	public void get_lsterror_empty() {
		
		TopPageAlarmStamping topPageAlarmStamping = TopPageAlarmStampingHelper.getListErrorNull();
		
		assertThat(topPageAlarmStamping.lstTopPageDetail).isEmpty();
	}

}
