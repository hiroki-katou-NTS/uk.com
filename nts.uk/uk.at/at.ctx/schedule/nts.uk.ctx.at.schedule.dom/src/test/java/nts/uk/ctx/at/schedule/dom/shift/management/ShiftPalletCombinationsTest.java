package nts.uk.ctx.at.schedule.dom.shift.management;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletsComHelper.Pallet.PalletCombinations;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletsComHelper.Pallet.PalletCombinations.Combination;

/**
 * 
 * @author sonnh1
 *
 */
public class ShiftPalletCombinationsTest {

	@Test
	public void create_shiftPalletsCombi_position0_fail() {
		NtsAssert.businessException("Msg_1616", () -> {
			new ShiftPalletCombinations(
					0, 
					new ShiftCombinationName("shiftComName"), // dummy
					Arrays.asList(Combination.DUMMY));
		});
	}
	
	@Test
	public void create_shiftPalletsCombi_position21_fail() {
		NtsAssert.businessException("Msg_1616", () -> {
			new ShiftPalletCombinations(
					21, 
					new ShiftCombinationName("shiftComName"), // dummy
					Arrays.asList(Combination.DUMMY));
		});
	}
	
	@Test
	public void create_shiftPalletsCombi_size0_fail() {
		NtsAssert.businessException("Msg_1627", () -> {
			new ShiftPalletCombinations(
					1, // dummy
					new ShiftCombinationName("shiftComName"), // dummy
					Collections.emptyList());
		});
	}
	
	@Test
	public void create_shiftPalletsCombi_size31_fail() {
		NtsAssert.businessException("Msg_1627", () -> {
			new ShiftPalletCombinations(
					1, // dummy
					new ShiftCombinationName("shiftComName"), // dummy
					Arrays.asList(
							Combination.DUMMY, 
							Combination.DUMMY, 
							Combination.DUMMY, 
							Combination.DUMMY, 
							Combination.DUMMY, 
							Combination.DUMMY, 
							Combination.DUMMY, 
							Combination.DUMMY, 
							Combination.DUMMY, 
							Combination.DUMMY, 
							Combination.DUMMY, 
							Combination.DUMMY, 
							Combination.DUMMY, 
							Combination.DUMMY, 
							Combination.DUMMY, 
							Combination.DUMMY, 
							Combination.DUMMY, 
							Combination.DUMMY, 
							Combination.DUMMY, 
							Combination.DUMMY, 
							Combination.DUMMY, 
							Combination.DUMMY, 
							Combination.DUMMY, 
							Combination.DUMMY, 
							Combination.DUMMY, 
							Combination.DUMMY, 
							Combination.DUMMY, 
							Combination.DUMMY, 
							Combination.DUMMY, 
							Combination.DUMMY, 
							Combination.DUMMY,
							Combination.DUMMY)); // 31 items
		});
	}
	
	@Test
	public void create_shiftPalletsCombi_duplicate() {
		NtsAssert.businessException("Msg_1627", () -> {
			new ShiftPalletCombinations(
					1, // dummy
					new ShiftCombinationName("shiftComName"), // dummy
					Arrays.asList(
							new Combinations(
									1, 
									new ShiftPalletCode("01")),
							new Combinations(
									1, 
									new ShiftPalletCode("02"))));
		});
	}

	@Test
	public void create_shiftPalletsCombi_success() {
		new ShiftPalletCombinations(
				1, 
				new ShiftCombinationName("shiftComName"),
				Arrays.asList(Combination.DUMMY));
	}
	
	@Test
	public void sortCombinations_success() {
		ShiftPalletCombinations target = new ShiftPalletCombinations(
				1, // dummy
				new ShiftCombinationName("shiftComName"),  // dummy
				Arrays.asList(
						new Combinations(
								1, 
								new ShiftPalletCode("01")), 
						new Combinations(
								20, 
								new ShiftPalletCode("20")), 
						new Combinations(
								30, 
								new ShiftPalletCode("30"))));
		
		target.sortCombinations();
		
		assertThat(target.getCombinations())
			.extracting(d -> d.getOrder(), d -> d.getShiftCode().v())
			.containsExactly(tuple(1, "01"), tuple(2, "20"), tuple(3, "30"));
	}
	
	@Test
	public void getters() {
		ShiftPalletCombinations target = PalletCombinations.DUMMY;
		NtsAssert.invokeGetters(target);
	}

}
