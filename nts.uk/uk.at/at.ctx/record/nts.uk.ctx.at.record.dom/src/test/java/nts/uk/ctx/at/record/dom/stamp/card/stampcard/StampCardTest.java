package nts.uk.ctx.at.record.dom.stamp.card.stampcard;

import static org.assertj.core.api.Assertions.assertThat;

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
		StampCard stampCard = new StampCard("Chungnt", "DUMMY", "DUMMY");
		assertThat(stampCard.getEmployeeId()).isEqualTo("DUMMY");
		assertThat(stampCard.getStampNumber().v()).isEqualTo("DUMMY");
	}
}
