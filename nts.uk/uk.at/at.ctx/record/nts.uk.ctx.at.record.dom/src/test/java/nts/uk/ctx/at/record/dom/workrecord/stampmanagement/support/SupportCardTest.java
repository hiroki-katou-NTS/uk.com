package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;

public class SupportCardTest {

	@Test
	public void getters() {
		SupportCard SupportCard = SupportCardHelper.getDataDefault1();
		NtsAssert.invokeGetters(SupportCard);
	}

	@Test
	public void test_constructor() {
		SupportCard SupportCard = SupportCardHelper.getDataDefault2();
		NtsAssert.invokeGetters(SupportCard);
	}

	@Test
	public void test_func_create() {
		SupportCard supportCard = SupportCard.create("cid", 9999, "wId");
		assertThat(supportCard.getCid()).isEqualTo("cid");
		assertThat(supportCard.getWorkplaceId()).isEqualTo("wId");
		assertThat(supportCard.getSupportCardNumber().v()).isEqualTo(9999);
	}
}
