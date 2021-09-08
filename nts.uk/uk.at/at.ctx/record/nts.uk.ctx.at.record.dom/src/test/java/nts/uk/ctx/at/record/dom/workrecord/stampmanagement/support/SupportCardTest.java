package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;

public class SupportCardTest {

	@Test
	public void getters() {
		SupportCard SupportCard = SupportCardHelper.getDataDefault();
		NtsAssert.invokeGetters(SupportCard);
	}
}
