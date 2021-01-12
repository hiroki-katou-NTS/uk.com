package nts.uk.ctx.at.shared.app.find.workrule.shiftmaster;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TargetOrgIdenInforDto {
	private int unit;
	
	private String workplaceId;

	private String workplaceGroupId;
	
	public TargetOrgIdenInfor convertFromDomain() {
		if(this.unit == TargetOrganizationUnit.WORKPLACE.value) {
			return TargetOrgIdenInfor.creatIdentifiWorkplace(this.workplaceId);
		}
		return TargetOrgIdenInfor.creatIdentifiWorkplaceGroup(this.workplaceGroupId);
	}
}
