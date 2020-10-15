package nts.uk.ctx.at.schedule.dom.shift.management;

import java.util.Arrays;

import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletsHelper.ShiftPalletsComHelper.PalletHelper;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletsHelper.ShiftPalletsComHelper.PalletHelper.PalletCombinationsHelper.CombinationHelper;
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
	
		public static ShiftPalletsCom DUMMY = new ShiftPalletsCom(
				"000000000000-0001", 
				1, 
				PalletHelper.DUMMY);
	 
		public static class PalletHelper {
	
			public static ShiftPallet DUMMY = new ShiftPallet(
					PalletDisplayInfoHelper.DUMMY,
					Arrays.asList( 
							new ShiftPalletCombinations(
									2, 
									new ShiftCombinationName("combiName"),
									Arrays.asList(CombinationHelper.DUMMY))));
	
			public static class PalletDisplayInfoHelper {
				
				public static ShiftPalletDisplayInfor DUMMY = new ShiftPalletDisplayInfor(
						new ShiftPalletName("shpaName"), 
						NotUseAtr.USE, 
						new ShiftRemarks("shRemar"));
			}
	
			public static class PalletCombinationsHelper {
	
				public static ShiftPalletCombinations DUMMY = new ShiftPalletCombinations(
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
		public static ShiftPalletsOrg DUMMY = new ShiftPalletsOrg(
				TargetOrgIdenInfor.creatIdentifiWorkplace("e34d86c4-1e32-463e-b86c-68551e0bbf18"),
				1,  
				PalletHelper.DUMMY);
	}
}
