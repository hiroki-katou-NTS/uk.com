package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.send;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author ThanhNX
 *
 *         勤務区分番号Test
 */
public class NRWorkTypeTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		assertEquals(NRWorkType.ATTENDANCE, NRWorkType.valueStringOf("0"));

		assertNull(NRWorkType.valueStringOf("400"));

		assertNull(NRWorkType.valueStringOf(null));
	}

}
