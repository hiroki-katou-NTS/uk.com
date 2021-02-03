package nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveattendance;

import java.util.Optional;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

/**
 * 組織の連続出勤できる上限日数Repository
 * @author hiroko_miura
 *
 */
public interface MaxDaysOfConsAttOrgRepository {

	/**
	 * insert(組織の連続出勤できる上限日数, 会社ID)
	 * @param maxContAttOrg
	 * @param companyId
	 */
	void insert(MaxDaysOfConsecutiveAttendanceOrganization maxContAttOrg, String companyId);
	
	/**
	 * update(組織の連続出勤できる上限日数, 会社ID)
	 * @param maxContAttOrg
	 * @param companyId
	 */
	void update(MaxDaysOfConsecutiveAttendanceOrganization maxContAttOrg, String companyId);
	
	/**
	 * delete(対象組織識別情報, 会社ID)
	 * @param targeOrg
	 * @param companyId
	 */
	void delete(TargetOrgIdenInfor targeOrg, String companyId);
	
	/**
	 * exists(対象組織識別情報, 会社ID)
	 * @param targeOrg
	 * @param companyId
	 * @return
	 */
	boolean exists(TargetOrgIdenInfor targeOrg, String companyId);
	
	/**
	 * get
	 * @param targeOrg
	 * @param companyId
	 * @return
	 */
	Optional<MaxDaysOfConsecutiveAttendanceOrganization> get(TargetOrgIdenInfor targeOrg, String companyId);
}
