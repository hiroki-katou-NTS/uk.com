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
	
	/**
	 * if (cardNumber.length() > this.digitsNumber.v()) {

			return Optional.empty();
		}
	 */
	@Test
	public void testMaxLength() {
		StampCardEditing cardEditing = StampCardEditingHelper.getDefault();
		Optional<String> optional = cardEditing.createStampCard("DUMMY", "0");
		
		assertThat(optional).isEmpty();
	}
	
	/**
	 * if (cardNumber.length() <= this.digitsNumber.v()) {

			Optional.ofNullable(this.method.editCardNumber(String.valueOf(this.digitsNumber.v()), cardNumber));
		}
	 */
	@Test
	public void testNotMaxLength() {
		StampCardEditing cardEditing = StampCardEditingHelper.getDefault();
		Optional<String> optional = cardEditing.createStampCard("DY", "01");
		
		assertThat(optional.get()).isEqualTo(" DY01");
	}
	
	@Test
	public void testeditCardNumber() {
		StampCardEditing cardEditing = StampCardEditingHelper.getDefault();
		String result = cardEditing.editCardNumber("DUMMY");
		assertThat(result).isEqualTo("DUMMY");
	}

}
