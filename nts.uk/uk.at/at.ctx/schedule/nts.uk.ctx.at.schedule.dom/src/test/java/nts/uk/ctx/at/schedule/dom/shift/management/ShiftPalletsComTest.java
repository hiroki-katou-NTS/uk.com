package nts.uk.ctx.at.schedule.dom.shift.management;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletsComHelper.Pallet.PalletCombinations;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletsComHelper.Pallet.PalletDisplayInfo;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletsComHelper.Pallet;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletsComHelper.Pallet.PalletCombinations.Combination;

/**
 * 
 * @author sonnh1
 *
 */
public class ShiftPalletsComTest {

	@Test
	public void create_shiftPalletsCom11_fail() {

		NtsAssert.businessException("Msg_1615", () -> {
			new ShiftPalletsCom(
					"000000000000-0001", // dummy
					11, 
					Pallet.DUMMY);
		});
	}
	
	@Test
	public void create_shiftPalletsCom0_fail() {

		NtsAssert.businessException("Msg_1615", () -> {
			new ShiftPalletsCom(
					"000000000000-0001", // dummy
					0, 
					Pallet.DUMMY);
		});
	}
	
	@Test
	public void create_shiftPalletsCom_sort() {

		ShiftPalletsCom target = new ShiftPalletsCom(
					"000000000000-0001", // dummy
					1, // dummy
					new ShiftPallet(
							PalletDisplayInfo.DUMMY,
							Arrays.asList(
								new ShiftPalletCombinations(
										2, 
										new ShiftCombinationName("combiName"), // dummy
										Arrays.asList(PalletCombinations.Combination.DUMMY)),
								new ShiftPalletCombinations(
										3, 
										new ShiftCombinationName("combiName"), // dummy
										Arrays.asList(PalletCombinations.Combination.DUMMY)),
								new ShiftPalletCombinations(
										1, 
										new ShiftCombinationName("combiName"), // dummy
										Arrays.asList(PalletCombinations.Combination.DUMMY)))));
		
		assertThat(target.getShiftPallet().getCombinations())
			.extracting(d->d.getPositionNumber())
			.containsExactly(1,2,3);
	}

	@Test
	public void create_shiftPalletsCom_success() {

		new ShiftPalletsCom(
				"000000000000-0001", // dummy
				1, 
				Pallet.DUMMY);
	}
	
	@Test
	public void modifyShiftPallets_success() {

		ShiftPalletsCom shiftPalletsCom = ShiftPalletsComHelper.DUMMY;

		ShiftPallet shiftPallet = new ShiftPallet(
				PalletDisplayInfo.DUMMY, 
				Arrays.asList(
						new ShiftPalletCombinations(
								7, 
								new ShiftCombinationName("name07"), 
								Arrays.asList(Combination.DUMMY)),
						new ShiftPalletCombinations(
								4, 
								new ShiftCombinationName("name04"), 
								Arrays.asList(Combination.DUMMY)),
						new ShiftPalletCombinations(
								5, 
								new ShiftCombinationName("name05"), 
								Arrays.asList(Combination.DUMMY))));

		shiftPalletsCom.modifyShiftPallets(shiftPallet);

		assertThat(shiftPalletsCom.getShiftPallet().getCombinations())
			.extracting(d -> d.getPositionNumber())
			.containsExactly(4, 5, 7);
	}

	@Test
	public void getters() {
		ShiftPalletsCom target = ShiftPalletsComHelper.DUMMY;
		NtsAssert.invokeGetters(target);
	}

}
