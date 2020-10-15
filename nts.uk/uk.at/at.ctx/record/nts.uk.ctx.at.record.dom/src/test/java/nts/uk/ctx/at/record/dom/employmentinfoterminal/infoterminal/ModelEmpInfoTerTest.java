package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author ThanhNX
 *
 *         就業情報端末の機種類Test
 */
public class ModelEmpInfoTerTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testEnum() {
		assertEquals(ModelEmpInfoTer.NRL_2, ModelEmpInfoTer.valueOf(8));

		assertNull(ModelEmpInfoTer.valueOf(4));

		assertNull(ModelEmpInfoTer.valueOf((Integer) null));
	}

}
