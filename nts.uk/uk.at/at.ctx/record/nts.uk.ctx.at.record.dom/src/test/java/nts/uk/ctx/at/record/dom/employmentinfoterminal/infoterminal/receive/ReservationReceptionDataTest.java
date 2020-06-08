package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.receive;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.NRHelperCommonTest;

/**
 * @author ThanhNX
 * 
 * 予約受信データTest
 */
public class ReservationReceptionDataTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testEqualHash() {
		ReservationReceptionData target1 = new ReservationReceptionData("1", "A", "200303", "010101", "2");
		
		ReservationReceptionData target2 = new ReservationReceptionData("1", "A", "200303", "010101", "2");

		ReservationReceptionData target3 = new ReservationReceptionData("1", "A", "200303", "010101", "3");

		NRHelperCommonTest.testEqualHash(target1, target2, target3);
	}

}
