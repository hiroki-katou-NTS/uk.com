package nts.uk.ctx.at.schedule.dom.shift.management;

import java.util.Arrays;

/**
 * 
 * @author sonnh1
 *
 */
public class ShiftPalletsComHelper {

	public static final ShiftPalletsCom DUMMY = new ShiftPalletsCom(
			"000000000000-0001", 
			1, 
			Pallet.DUMMY);

	public static class Pallet {

		public static final ShiftPallet DUMMY = new ShiftPallet(
				PalletDisplayInfo.DUMMY,
				Arrays.asList(new ShiftPalletCombinations(
						2, 
						new ShiftCombinationName("combiName"),
						Arrays.asList(PalletCombinations.Combination.DUMMY))));

		public static class PalletDisplayInfo {
			public static final ShiftPalletDisplayInfor DUMMY = new ShiftPalletDisplayInfor(
					new ShiftPalletName("shpaName"), 
					true, 
					new ShiftRemarks("shRemar"));
		}

		public static class PalletCombinations {
			public static class Combination {
				public static final Combinations DUMMY = new Combinations(
						1, 
						new ShiftPalletCode("02"));
			}
		}

	}
}
