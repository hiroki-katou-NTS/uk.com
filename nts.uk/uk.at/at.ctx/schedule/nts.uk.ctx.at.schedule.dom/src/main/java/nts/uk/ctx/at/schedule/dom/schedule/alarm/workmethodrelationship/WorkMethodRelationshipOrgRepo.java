package nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

public interface WorkMethodRelationshipOrgRepo {
	
	public void insert(WorkMethodRelationshipOrg domain);
	
	public void update(WorkMethodRelationshipOrg domain);
	
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
	 * @param workMethod
	 */
	public void deleteWorkMethod(String companyId, TargetOrgIdenInfor targetOrg, WorkMethod workMethod);
	
	/**
	 * getAll ( 会社ID, 対象組織 )	
	 * @param companyId
	 * @param targetOrg
	 * @return
	 */
	public List<WorkMethodRelationshipOrg> getAll(String companyId, TargetOrgIdenInfor targetOrg);
	
	/**
	 * get ( 会社ID, 対象組織, 勤務方法 )	
	 * @param companyId
	 * @param targetOrg
	 * @param workMethod
	 * @return
	 */
	public Optional<WorkMethodRelationshipOrg> getWithWorkMethod(String companyId, TargetOrgIdenInfor targetOrg, WorkMethod workMethod);
	
	/**
	 * *get ( 会社ID, 対象組織, List<勤務方法> )
	 * @param companyId
	 * @param targetOrg
	 * @param workMethodList
	 * @return
	 */
	public List<WorkMethodRelationshipOrg> getWithWorkMethodList(String companyId, TargetOrgIdenInfor targetOrg, List<WorkMethod> workMethodList);
	
	/**
	 * exists ( 会社ID, 対象組織, 勤務方法 )
	 * @param companyId
	 * @param targetOrg
	 * @param workMethod
	 * @return
	 */
	public boolean exists(String companyId, TargetOrgIdenInfor targetOrg, WorkMethod workMethod);

}
