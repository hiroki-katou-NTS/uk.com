package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.receive;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author ThanhNX
 *
 *         カード区分 NRTest
 */
public class CardCategoryTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testEnum() {
		assertEquals(CardCategory.ID_INPUT, CardCategory.valueStringOf("A"));

		assertNull(CardCategory.valueStringOf("400"));

		assertNull(CardCategory.valueStringOf(null));
	}

}
