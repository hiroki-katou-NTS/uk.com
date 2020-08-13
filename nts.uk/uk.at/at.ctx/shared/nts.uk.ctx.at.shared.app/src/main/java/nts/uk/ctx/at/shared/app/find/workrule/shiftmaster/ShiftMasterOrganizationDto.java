package nts.uk.ctx.at.shared.app.find.workrule.shiftmaster;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterOrganization;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShiftMasterOrganizationDto {
	private String companyId;
	private TargetOrgIdenInforDto targetOrg;
	private List<String> listShiftMaterCode;
	
	public static ShiftMasterOrganizationDto toDto(ShiftMasterOrganization masterOrganization) {
		return new ShiftMasterOrganizationDto(masterOrganization.getCompanyId(), new TargetOrgIdenInforDto(masterOrganization.getTargetOrg().getUnit().value, 
				masterOrganization.getTargetOrg().getWorkplaceId().get(), masterOrganization.getTargetOrg().getWorkplaceGroupId().get()), masterOrganization.getListShiftMaterCode());
	}
}
