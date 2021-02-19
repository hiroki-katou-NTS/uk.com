package nts.uk.ctx.at.schedule.dom.shift.management;

import java.util.Arrays;

import nts.uk.ctx.at.schedule.dom.shift.management.shiftPalette.Combinations;
import nts.uk.ctx.at.schedule.dom.shift.management.shiftPalette.ShiftCombinationName;
import nts.uk.ctx.at.schedule.dom.shift.management.shiftPalette.ShiftPalette;
import nts.uk.ctx.at.schedule.dom.shift.management.shiftPalette.ShiftPaletteCombinations;
import nts.uk.ctx.at.schedule.dom.shift.management.shiftPalette.ShiftPaletteDisplayInfor;
import nts.uk.ctx.at.schedule.dom.shift.management.shiftPalette.ShiftPaletteName;
import nts.uk.ctx.at.schedule.dom.shift.management.shiftPalette.ShiftPaletteOrg;
import nts.uk.ctx.at.schedule.dom.shift.management.shiftPalette.ShiftRemarks;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterCode;
import nts.uk.shr.com.enumcommon.NotUseAtr;

public class ShiftPalletsOrgHelper {
	public static ShiftPaletteOrg getShiftPalletsOrgDefault() {
		return new ShiftPaletteOrg(
				TargetOrgIdenInfor.creatIdentifiWorkplace("e34d86c4-1e32-463e-b86c-68551e0bbf18"),
				1, 
				new ShiftPalette(
						new ShiftPaletteDisplayInfor(
								new ShiftPaletteName("shpaName"), //dummy
								NotUseAtr.USE, //dummy
								new ShiftRemarks("shRemar")), //dummy
						Arrays.asList(
								new ShiftPaletteCombinations(
										1, //dummy
										new ShiftCombinationName("shComName1"), //dummy
										Arrays.asList(new Combinations(
												1, //dummy
												new ShiftMasterCode("0000001"))))))); //dummy
	}
	
	public static ShiftPaletteOrg getShiftPalletsOrgDefault_workplacegrp() {
		return new ShiftPaletteOrg(
				TargetOrgIdenInfor.creatIdentifiWorkplaceGroup("e34d86c4-1e32-463e-b86c-68551e0bbf18"),
				1, 
				new ShiftPalette(
						new ShiftPaletteDisplayInfor(
								new ShiftPaletteName("shpaName"), //dummy
								NotUseAtr.USE, //dummy
								new ShiftRemarks("shRemar")), //dummy
						Arrays.asList(
								new ShiftPaletteCombinations(
										1, //dummy
										new ShiftCombinationName("shComName1"), //dummy
										Arrays.asList(new Combinations(
												1, //dummy
												new ShiftMasterCode("0000001"))))))); //dummy
	}
}
