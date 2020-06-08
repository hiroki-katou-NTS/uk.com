package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.receive;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author ThanhNX
 *
 *         出退区分 NRTest
 */
public class LeaveCategoryTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testEnum() {
		assertEquals(LeaveCategory.EARLY, LeaveCategory.valueStringOf("S"));

		assertNull(LeaveCategory.valueStringOf("400"));

		assertNull(LeaveCategory.valueStringOf(null));
	}

}
