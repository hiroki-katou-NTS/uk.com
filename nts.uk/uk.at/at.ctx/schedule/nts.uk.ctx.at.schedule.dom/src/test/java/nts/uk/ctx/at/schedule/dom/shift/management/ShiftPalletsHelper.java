package nts.uk.ctx.at.schedule.dom.shift.management;

import java.util.Arrays;

import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletsHelper.ShiftPalletsComHelper.PalletHelper;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletsHelper.ShiftPalletsComHelper.PalletHelper.PalletCombinationsHelper.CombinationHelper;
import nts.uk.ctx.at.schedule.dom.shift.management.shiftPalette.Combinations;
import nts.uk.ctx.at.schedule.dom.shift.management.shiftPalette.ShiftCombinationName;
import nts.uk.ctx.at.schedule.dom.shift.management.shiftPalette.ShiftPalette;
import nts.uk.ctx.at.schedule.dom.shift.management.shiftPalette.ShiftPaletteCombinations;
import nts.uk.ctx.at.schedule.dom.shift.management.shiftPalette.ShiftPaletteDisplayInfor;
import nts.uk.ctx.at.schedule.dom.shift.management.shiftPalette.ShiftPaletteName;
import nts.uk.ctx.at.schedule.dom.shift.management.shiftPalette.ShiftPaletteCom;
import nts.uk.ctx.at.schedule.dom.shift.management.shiftPalette.ShiftPaletteOrg;
import nts.uk.ctx.at.schedule.dom.shift.management.shiftPalette.ShiftRemarks;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterCode;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 
 * @author sonnh1
 *
 */
public class ShiftPalletsHelper {
	public static class ShiftPalletsComHelper {
	
		public static ShiftPaletteCom DUMMY = new ShiftPaletteCom(
				"000000000000-0001", 
				1, 
				PalletHelper.DUMMY);
	 
		public static class PalletHelper {
	
			public static ShiftPalette DUMMY = new ShiftPalette(
					PalletDisplayInfoHelper.DUMMY,
					Arrays.asList( 
							new ShiftPaletteCombinations(
									2, 
									new ShiftCombinationName("combiName"),
									Arrays.asList(CombinationHelper.DUMMY))));
	
			public static class PalletDisplayInfoHelper {
				
				public static ShiftPaletteDisplayInfor DUMMY = new ShiftPaletteDisplayInfor(
						new ShiftPaletteName("shpaName"), 
						NotUseAtr.USE, 
						new ShiftRemarks("shRemar"));
			}
	
			public static class PalletCombinationsHelper {
	
				public static ShiftPaletteCombinations DUMMY = new ShiftPaletteCombinations(
						1,
						new ShiftCombinationName("ShiftComName"), 
						Arrays.asList(CombinationHelper.DUMMY));
	
				public static class CombinationHelper {
					
					public static Combinations DUMMY = new Combinations(
							1, 
							new ShiftMasterCode("0000001"));
				}
			}
	
		}
	}
	
	public static class ShiftPalletsOrgHelper {
		public static ShiftPaletteOrg DUMMY = new ShiftPaletteOrg(
				TargetOrgIdenInfor.creatIdentifiWorkplace("e34d86c4-1e32-463e-b86c-68551e0bbf18"),
				1,  
				PalletHelper.DUMMY);
	}
}
