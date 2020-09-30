package nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

public interface WorkMethodRelationshipOrgRepo {
	
	public void insert(String companyId, WorkMethodRelationshipOrganization domain);
	
	public void update(String companyId, WorkMethodRelationshipOrganization domain);
	
	/**
	 * delete ( 会社ID, 対象組織 )
	 * @param companyId
	 * @param targetOrg
	 */
	public void deleteAll(String companyId, TargetOrgIdenInfor targetOrg);
	
	/**
	 * delete ( 会社ID, 対象組織, 勤務方法 )	
	 * @param companyId
	 * @param targetOrg
	 * @param prevWorkMethod
	 */
	public void deleteWorkMethod(String companyId, TargetOrgIdenInfor targetOrg, WorkMethod prevWorkMethod);
	
	/**
	 * getAll ( 会社ID, 対象組織 )	
	 * @param companyId
	 * @param targetOrg
	 * @return
	 */
	public List<WorkMethodRelationshipOrganization> getAll(String companyId, TargetOrgIdenInfor targetOrg);
	
	/**
	 * get ( 会社ID, 対象組織, 勤務方法 )	
	 * @param companyId
	 * @param targetOrg
	 * @param prevWorkMethod
	 * @return
	 */
	public Optional<WorkMethodRelationshipOrganization> getWithWorkMethod(String companyId, TargetOrgIdenInfor targetOrg, WorkMethod prevWorkMethod);
	
	/**
	 * *get ( 会社ID, 対象組織, List<勤務方法> )
	 * @param companyId
	 * @param targetOrg
	 * @param prevWorkMethodList
	 * @return
	 */
	public List<WorkMethodRelationshipOrganization> getWithWorkMethodList(String companyId, TargetOrgIdenInfor targetOrg, List<WorkMethod> prevWorkMethodList);
	
	/**
	 * exists ( 会社ID, 対象組織, 勤務方法 )
	 * @param companyId
	 * @param targetOrg
	 * @param prevWorkMethod
	 * @return
	 */
	public boolean exists(String companyId, TargetOrgIdenInfor targetOrg, WorkMethod prevWorkMethod);

}
