package nts.uk.ctx.at.schedule.dom.shift.management;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletsComHelper.Pallet;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletsComHelper.Pallet.PalletCombinations;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletsComHelper.Pallet.PalletCombinations.Combination;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletsComHelper.Pallet.PalletDisplayInfo;

/**
 * 
 * @author sonnh1
 *
 */
public class ShiftPalletTest {

	@Test
	public void create_shiftPallet_size0_fail() {

		NtsAssert.businessException("Msg_1616", () -> {
			new ShiftPallet(
					PalletDisplayInfo.DUMMY,
					Collections.emptyList());
		});
	}
	
	@Test
	public void create_shiftPallet_size21_fail() {

		NtsAssert.businessException("Msg_1616", () -> {
			new ShiftPallet(
					PalletDisplayInfo.DUMMY,
					Arrays.asList(
							PalletCombinations.DUMMY,
							PalletCombinations.DUMMY,
							PalletCombinations.DUMMY,
							PalletCombinations.DUMMY,
							PalletCombinations.DUMMY,
							PalletCombinations.DUMMY,
							PalletCombinations.DUMMY,
							PalletCombinations.DUMMY,
							PalletCombinations.DUMMY,
							PalletCombinations.DUMMY,
							PalletCombinations.DUMMY,
							PalletCombinations.DUMMY,
							PalletCombinations.DUMMY,
							PalletCombinations.DUMMY,
							PalletCombinations.DUMMY,
							PalletCombinations.DUMMY,
							PalletCombinations.DUMMY,
							PalletCombinations.DUMMY,
							PalletCombinations.DUMMY,
							PalletCombinations.DUMMY,
							PalletCombinations.DUMMY)); // 21 items
		});
	}

	@Test
	public void create_shiftPallet_duplicate() {

		NtsAssert.businessException("Msg_1616", () -> {
			new ShiftPallet(
					PalletDisplayInfo.DUMMY,
					Arrays.asList(
							new ShiftPalletCombinations(
									1, 
									new ShiftCombinationName("shComName1"), // dummy
									Arrays.asList(Combination.DUMMY)),
							new ShiftPalletCombinations(
									1, 
									new ShiftCombinationName("shComName2"), // dummy
									Arrays.asList(Combination.DUMMY))));
		});
	}
	
	@Test
	public void create_shiftPallet_sort() {

		ShiftPallet target = new ShiftPallet(
				PalletDisplayInfo.DUMMY,
				Arrays.asList(
					new ShiftPalletCombinations(
							2, 
							new ShiftCombinationName("combiName"), // dummy
							Arrays.asList(Combination.DUMMY)),
					new ShiftPalletCombinations(
							3, 
							new ShiftCombinationName("combiName"), // dummy
							Arrays.asList(Combination.DUMMY)),
					new ShiftPalletCombinations(
							1, 
							new ShiftCombinationName("combiName"), // dummy
							Arrays.asList(Combination.DUMMY))));
		
		assertThat(target.getCombinations())
			.extracting(d->d.getPositionNumber())
			.containsExactly(1,2,3);
	}
	
	@Test
	public void create_shiftPallet_success() {

			new ShiftPallet(
					PalletDisplayInfo.DUMMY,
					Arrays.asList(
							new ShiftPalletCombinations(
									1, 
									new ShiftCombinationName("shComName1"), // dummy
									Arrays.asList(Combination.DUMMY))
							));
	}

	@Test
	public void getters() {
		ShiftPallet target = Pallet.DUMMY;
		NtsAssert.invokeGetters(target);
	}
}
