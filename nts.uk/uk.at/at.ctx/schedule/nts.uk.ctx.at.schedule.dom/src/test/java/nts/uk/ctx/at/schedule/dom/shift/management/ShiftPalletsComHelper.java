package nts.uk.ctx.at.schedule.dom.shift.management;

import java.util.Arrays;

import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletsComHelper.Pallet.PalletCombinations.Combination;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 
 * @author sonnh1
 *
 */
public class ShiftPalletsComHelper {

	public static ShiftPalletsCom DUMMY = new ShiftPalletsCom(
			"000000000000-0001", 
			1, 
			Pallet.DUMMY);
 
	public static class Pallet {

		public static ShiftPallet DUMMY = new ShiftPallet(
				PalletDisplayInfo.DUMMY,
				Arrays.asList(
						new ShiftPalletCombinations(
								2, 
								new ShiftCombinationName("combiName"),
								Arrays.asList(Combination.DUMMY))));

		public static class PalletDisplayInfo {
			
			public static ShiftPalletDisplayInfor DUMMY = new ShiftPalletDisplayInfor(
					new ShiftPalletName("shpaName"), 
					NotUseAtr.USE, 
					new ShiftRemarks("shRemar"));
		}

		public static class PalletCombinations {

			public static ShiftPalletCombinations DUMMY = new ShiftPalletCombinations(
					1,
					new ShiftCombinationName("ShiftComName"), 
					Arrays.asList(Combination.DUMMY));

			public static class Combination {
				
				public static Combinations DUMMY = new Combinations(
						1, 
						new ShiftPalletCode("01"));
			}
		}

	}
}
