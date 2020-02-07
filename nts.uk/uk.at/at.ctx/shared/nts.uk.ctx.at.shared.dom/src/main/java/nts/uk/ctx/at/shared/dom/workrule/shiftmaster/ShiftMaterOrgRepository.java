package nts.uk.ctx.at.shared.dom.workrule.shiftmaster;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

public interface ShiftMaterOrgRepository {
	public void insert(ShiftMaterOrganization shiftMaterOrganization);

	public void update(ShiftMaterOrganization shiftMaterOrganization);

	public void delete(String companyId, TargetOrgIdenInfor targetOrg);

	public Optional<ShiftMaterOrganization> getByTargetOrg(String companyId, TargetOrgIdenInfor targetOrg);

	public List<ShiftMaterOrganization> getByListTargetOrg(String companyId, List<TargetOrgIdenInfor> targetOrg);

	public boolean exists(String companyId, TargetOrgIdenInfor targetOrg);

	public List<ShiftMaterOrganization> getByCid(String companyId);
}
