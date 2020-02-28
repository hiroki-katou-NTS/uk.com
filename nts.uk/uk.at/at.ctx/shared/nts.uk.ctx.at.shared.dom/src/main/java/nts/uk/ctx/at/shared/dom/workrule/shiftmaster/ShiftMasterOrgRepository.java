package nts.uk.ctx.at.shared.dom.workrule.shiftmaster;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

public interface ShiftMasterOrgRepository {
	public void insert(ShiftMasterOrganization shiftMaterOrganization);

	public void update(ShiftMasterOrganization shiftMaterOrganization);

	public void delete(String companyId, TargetOrgIdenInfor targetOrg);

	public Optional<ShiftMasterOrganization> getByTargetOrg(String companyId, TargetOrgIdenInfor targetOrg);

	public List<ShiftMasterOrganization> getByListTargetOrg(String companyId, List<TargetOrgIdenInfor> targetOrg);

	public boolean exists(String companyId, TargetOrgIdenInfor targetOrg);

	public List<ShiftMasterOrganization> getByCid(String companyId);
	
	public List<String> getAlreadySettingWorkplace(String companyId);
}
