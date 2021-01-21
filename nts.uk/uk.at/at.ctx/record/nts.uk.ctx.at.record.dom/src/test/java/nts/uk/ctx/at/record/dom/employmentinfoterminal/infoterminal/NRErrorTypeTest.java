package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author ThanhNX
 *
 *         NR-種類Test
 */
public class NRErrorTypeTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testEnum() {
		assertEquals(NRErrorType.RESERVATION, NRErrorType.valueOf(1));

		assertNull(NRErrorType.valueOf(400));

		assertNull(NRErrorType.valueOf((Integer) null));
	}

}
