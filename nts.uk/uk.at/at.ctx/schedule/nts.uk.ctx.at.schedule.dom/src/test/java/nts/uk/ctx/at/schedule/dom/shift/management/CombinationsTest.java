package nts.uk.ctx.at.schedule.dom.shift.management;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletsComHelper.Pallet.PalletCombinations.Combination;

/**
 * 
 * @author sonnh1
 *
 */
public class CombinationsTest {

	@Test
	public void create_combinations_0_fail() {
		NtsAssert.businessException("Msg_1627", () -> {
			new Combinations(
					0, 
					new ShiftPalletCode("01")); // dummy
		});
	}
	
	@Test
	public void create_combinations_32_fail() {
		NtsAssert.businessException("Msg_1627", () -> {
			new Combinations(
					32, 
					new ShiftPalletCode("01")); // dummy
		});
	}

	@Test
	public void create_combinations_success() {
		new Combinations(
				1, 
				new ShiftPalletCode("01"));
	}

	@Test
	public void getters() {
		Combinations target = Combination.DUMMY;
		NtsAssert.invokeGetters(target);
	}

}
