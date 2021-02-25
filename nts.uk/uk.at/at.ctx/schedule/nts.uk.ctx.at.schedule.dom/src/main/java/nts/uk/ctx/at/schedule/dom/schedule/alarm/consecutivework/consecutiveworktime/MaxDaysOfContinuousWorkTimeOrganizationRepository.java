package nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveworktime;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

/**
 * 組織の就業時間帯の連続勤務できる上限日数Repository
 * @author hiroko_miura
 *
 */
public interface MaxDaysOfContinuousWorkTimeOrganizationRepository {
	/**
	 * insert(会社ID, 組織の就業時間帯の連続勤務できる上限日数)
	 * @param companyId
	 * @param domain
	 */
	void insert (String companyId, MaxDaysOfContinuousWorkTimeOrganization domain);
	
	/**
	 * update(会社ID, 組織の就業時間帯の連続勤務できる上限日数)
	 * @param companyId
	 * @param domain
	 */
	void update (String companyId, MaxDaysOfContinuousWorkTimeOrganization domain);
	
	/**
	 * delete (会社ID, 対象組織識別情報, 就業時間帯連続コード)
	 * @param companyId
	 * @param targeOrg
	 * @param code
	 */
	void delete (String companyId, TargetOrgIdenInfor targeOrg, ConsecutiveWorkTimeCode code);
	
	/**
	 * exists(会社ID, 対象組織識別情報, 就業時間帯連続コード)
	 * @param companyId
	 * @param targeOrg
	 * @param code
	 * @return
	 */
	boolean exists (String companyId, TargetOrgIdenInfor targeOrg, ConsecutiveWorkTimeCode code);
	
	/**
	 * get
	 * @param companyId
	 * @param targeOrg
	 * @param code
	 * @return
	 */
	Optional<MaxDaysOfContinuousWorkTimeOrganization> get (String companyId, TargetOrgIdenInfor targeOrg, ConsecutiveWorkTimeCode code);
	
	/**
	 * get*
	 * @param companyId
	 * @param targeOrg
	 * @return
	 */
	List<MaxDaysOfContinuousWorkTimeOrganization> getAll (String companyId, TargetOrgIdenInfor targeOrg);
}
