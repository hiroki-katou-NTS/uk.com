package nts.uk.ctx.at.record.dom.stamp.card.stamcardedit;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;

/**
 * 
 * @author chungnt
 *
 */

public class StampCardEditMethodTest {

	@Test
	public void getter() {
		StampCardEditMethod stampCardEditMethod = StampCardEditMethod.AfterSpace;
		NtsAssert.invokeGetters(stampCardEditMethod);
	}
	
	
	@Test
	public void testPreviousSpace() {
		StampCardEditMethod stampCardEditMethod = StampCardEditMethod.PreviousSpace;
		
		String string = stampCardEditMethod.editCardNumber("10","DUMMY");
		assertThat(string).isEqualTo("     DUMMY");
	}
	
	@Test
	public void testAfterSpace() {
		StampCardEditMethod stampCardEditMethod = StampCardEditMethod.AfterSpace;
		
		String string = stampCardEditMethod.editCardNumber("10","DUMMY");
		assertThat(string).isEqualTo("DUMMY     ");
	}

	@Test
	public void testPreviousZero() {
		StampCardEditMethod stampCardEditMethod = StampCardEditMethod.PreviousZero;
		
		String string = stampCardEditMethod.editCardNumber("6","DUMMY");
		assertThat(string).isEqualTo("0DUMMY");
	}
	
	@Test
	public void testAfterZero() {
		StampCardEditMethod stampCardEditMethod = StampCardEditMethod.AfterZero;
		
		String string = stampCardEditMethod.editCardNumber("10","DUMMY");
		assertThat(string).isEqualTo("DUMMY00000");
	}

}
