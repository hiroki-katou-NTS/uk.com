package nts.uk.ctx.at.shared.app.command.workrule.shiftmaster;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;

/**
 * @author anhdt 職場で使うシフトマスタを登録する
 */
@Data
public class CopyShiftMasterOrgCommand {
	private Integer targetUnit;
	private String workplaceId;
	private String workplaceGroupId;	
	private List<RegisterShiftMasterOrgCommand> toWkps;
	
	public TargetOrgIdenInfor toTarget() {
		TargetOrganizationUnit unit = TargetOrganizationUnit.valueOf(targetUnit);
		TargetOrgIdenInfor target = new TargetOrgIdenInfor(unit, workplaceId, workplaceGroupId);		
		return target;
	}
}
