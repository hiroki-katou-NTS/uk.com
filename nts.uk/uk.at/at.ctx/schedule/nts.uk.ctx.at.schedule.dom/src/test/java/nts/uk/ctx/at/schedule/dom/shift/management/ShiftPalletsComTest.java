package nts.uk.ctx.at.schedule.dom.shift.management;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;

/**
 * 
 * @author sonnh1
 *
 */
public class ShiftPalletsComTest {

	@Test
	public void create_shiftPalletsCom_empty() {

		NtsAssert.businessException("Msg_1615", () -> {
			new ShiftPalletsCom("000000000000-0001", // dummy
					11, ShiftPalletsComHelper.Pallet.DUMMY); // dummy
		});
	}

	@Test
	public void create_shiftPalletsCom_success() {

		new ShiftPalletsCom("000000000000-0001", // dummy
				11, null); // dummy
	}

	@Test
	public void getters() {
		ShiftPalletsCom target = ShiftPalletsComHelper.DUMMY;
		NtsAssert.invokeGetters(target);
	}

}
