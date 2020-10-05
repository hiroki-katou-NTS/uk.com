package nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.limitworktime;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

public interface MaxDayOfWorkTimeOrganizationRepo {
	
	/**
	 * insert(組織の就業時間帯の期間内上限勤務)
	 * @param companyId
	 * @param domain
	 */
	public void insert(String companyId, MaxDayOfWorkTimeOrganization domain);
	
	/**
	 * update(組織の就業時間帯の期間内上限勤務)
	 * @param companyId
	 * @param domain
	 */
	public void update(String companyId, MaxDayOfWorkTimeOrganization domain);
	
	/**
	 * exists(対象組織識別情報, 就業時間帯上限コード)
	 * @param companyId
	 * @param targetOrg
	 * @param code
	 * @return
	 */
	public boolean exists(String companyId, TargetOrgIdenInfor targetOrg, MaxDayOfWorkTimeCode code);
	
	/**
	 * delete(対象組織識別情報, 就業時間帯上限コード)
	 * @param companyId
	 * @param targetOrg
	 * @param code
	 */
	public void delete(String companyId, TargetOrgIdenInfor targetOrg, MaxDayOfWorkTimeCode code);
	
	/**
	 * get
	 * @param companyId
	 * @param targetOrg
	 * @param code
	 * @return
	 */
	public Optional<MaxDayOfWorkTimeOrganization> getWithCode(String companyId, 
															TargetOrgIdenInfor targetOrg, 
															MaxDayOfWorkTimeCode code);
	
	/**
	 * *get
	 * @param companyId
	 * @param targetOrg
	 * @return
	 */
	public List<MaxDayOfWorkTimeOrganization> getAll(String companyId, TargetOrgIdenInfor targetOrg);

}
