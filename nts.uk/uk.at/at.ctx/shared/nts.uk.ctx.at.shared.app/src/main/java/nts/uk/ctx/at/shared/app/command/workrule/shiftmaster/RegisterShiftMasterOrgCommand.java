package nts.uk.ctx.at.shared.app.command.workrule.shiftmaster;

import java.util.List;
import java.util.Optional;

import lombok.Data;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterOrganization;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author anhdt 職場で使うシフトマスタを登録する
 */
@Data
public class RegisterShiftMasterOrgCommand {
	private Integer targetUnit;
	private String workplaceId;
	private String workplaceGroupId;
	private List<String> shiftMasterCodes;
	
	public ShiftMasterOrganization toDomain() {
		String companyId = AppContexts.user().companyId();
		TargetOrganizationUnit unit = TargetOrganizationUnit.valueOf(targetUnit);
		TargetOrgIdenInfor target = new TargetOrgIdenInfor(unit, Optional.ofNullable(workplaceId), Optional.ofNullable(workplaceGroupId));
		
		return new ShiftMasterOrganization(companyId, target, shiftMasterCodes);
	}
	
	public TargetOrgIdenInfor toTarget() {
		TargetOrganizationUnit unit = TargetOrganizationUnit.valueOf(targetUnit);
		TargetOrgIdenInfor target = new TargetOrgIdenInfor(unit, Optional.ofNullable(workplaceId), Optional.ofNullable(workplaceGroupId));
		
		return target;
	}
}
