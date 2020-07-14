package nts.uk.ctx.at.record.dom.stamp.card.stampcard;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;

public class StampCardTest {

	@Test
	public void getter() {
		StampCard stampCard = new StampCard( new ContractCode("DUMMY"), new StampNumber("DUMMY"), "DUMMY", GeneralDate.today(), "DUMMY");
		NtsAssert.invokeGetters(stampCard);
	}

	@Test
	public void test_StampCard_C1() {
		StampCard stampCard = new StampCard("DUMMY", "DUMMY", "DUMMY");
		NtsAssert.invokeGetters(stampCard);
	}
}
