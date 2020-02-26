package nts.uk.ctx.at.shared.app.command.workrule.shiftmaster;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterOrganization;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author anhdt 職場で使うシフトマスタを削除する
 */
@Data
public class DeleteShiftMasterOrgCommand {
	private Integer targetUnit;
	private String workplaceId;
	private String workplaceGroupId;
	private List<String> shiftMasterCodes;
	
	public ShiftMasterOrganization toDomain() {
		String companyId = AppContexts.user().companyId();
		TargetOrganizationUnit unit = TargetOrganizationUnit.valueOf(targetUnit);
		TargetOrgIdenInfor target = new TargetOrgIdenInfor(unit, workplaceId, workplaceGroupId);
		
		return new ShiftMasterOrganization(companyId, target, shiftMasterCodes);
	}
}
