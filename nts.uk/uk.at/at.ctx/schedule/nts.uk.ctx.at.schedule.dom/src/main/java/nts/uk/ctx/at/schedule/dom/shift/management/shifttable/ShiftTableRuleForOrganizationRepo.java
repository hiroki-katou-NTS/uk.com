package nts.uk.ctx.at.schedule.dom.shift.management.shifttable;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

public interface ShiftTableRuleForOrganizationRepo {
	
	public void insert(ShiftTableRuleForOrganization domain);
	
	public void update(ShiftTableRuleForOrganization domain);
	
	public void delete(String companyId);
	
	public Optional<ShiftTableRuleForOrganization> get(String companyId, TargetOrgIdenInfor targetOrg);
	
	public List<ShiftTableRuleForOrganization> getList(String companyId, List<TargetOrgIdenInfor> targetOrgList);

}
