package nts.uk.ctx.at.schedule.dom.shift.management.shifttable;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

public interface ShiftTableRuleForOrganizationRepo {
	
	/**
	 * insert
	 * @param domain 組織別のシフト表のルール
	 */
	public void insert(ShiftTableRuleForOrganization domain);
	
	/**
	 * update
	 * @param domain 組織別のシフト表のルール
	 */
	public void update(ShiftTableRuleForOrganization domain);
	
	/**
	 * delete
	 * @param companyId
	 */
	public void delete(String companyId);
	
	/**
	 * get
	 * @param companyId
	 * @param targetOrg
	 * @return 組織別のシフト表のルール
	 */
	public Optional<ShiftTableRuleForOrganization> get(String companyId, TargetOrgIdenInfor targetOrg);
	
	/**
	 * get*
	 * @param companyId
	 * @param targetOrgList
	 * @return 組織別のシフト表のルール一覧
	 */
	public List<ShiftTableRuleForOrganization> getList(String companyId, List<TargetOrgIdenInfor> targetOrgList);

}
