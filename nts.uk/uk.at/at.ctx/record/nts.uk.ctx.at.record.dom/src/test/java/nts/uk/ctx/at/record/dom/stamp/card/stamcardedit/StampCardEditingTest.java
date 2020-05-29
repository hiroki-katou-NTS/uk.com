package nts.uk.ctx.at.record.dom.stamp.card.stamcardedit;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;

/**
 * 
 * @author chungnt
 *
 */

public class StampCardEditingTest {

	@Test
	public void test() {
		StampCardEditing cardEditing = StampCardEditingHelper.getDefault();
		NtsAssert.invokeGetters(cardEditing);
	}
	
	@Test
	public void testMaxLength() {
		StampCardEditing cardEditing = StampCardEditingHelper.getDefault();
		Optional<String> optional = cardEditing.createStampCard("DUMMY", "000-0000000001");
		
		assertThat(optional).isEmpty();
	}
	
	@Test
	public void testNotMaxLength() {
		StampCardEditing cardEditing = StampCardEditingHelper.getDefault();
		Optional<String> optional = cardEditing.createStampCard("DY", "1");
		
		assertThat(optional.get()).isEqualTo("  DY1");
	}
	
	@Test
	public void testeditCardNumber() {
		StampCardEditing cardEditing = StampCardEditingHelper.getDefault();
		String result = cardEditing.editCardNumber("DUMMY");
		assertThat(result).isEqualTo("DUMMY");
	}

}
