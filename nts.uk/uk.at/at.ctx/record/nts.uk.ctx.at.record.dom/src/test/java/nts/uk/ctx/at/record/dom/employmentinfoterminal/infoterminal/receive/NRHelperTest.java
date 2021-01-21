package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.receive;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author ThanhNX
 * Test NRHelper
 */
public class NRHelperTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void toStringNR() {
		String helper = NRHelper.toStringNR(-60, 60);
		assertEquals("-01000100", helper);
	}

	@Test
	public void toStringNRPositive() {
		String helper = NRHelper.toStringNR(60, 60);
		assertEquals("+01000100", helper);
	}
	
}
