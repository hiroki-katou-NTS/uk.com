package nts.uk.ctx.at.shared.dom.workrule.shiftmaster;

import java.util.Arrays;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;


public class ShiftMasterOrgHelper {
	public static ShiftMasterOrganization getShiftMasterOrgEmpty() {
		return new ShiftMasterOrganization(
				"companyId",
				getTargetOrgIdenInforEmpty(),
				Arrays.asList("123"));
	}
	
	public static TargetOrgIdenInfor getTargetOrgIdenInforEmpty() {
		
		return TargetOrgIdenInfor.creatIdentifiWorkplace("workplaceId");
	}
	
	public static boolean checkExist(boolean param){
		return param;
	}
}
