package nts.uk.ctx.at.schedule.dom.schedule.alarm.continuouswork.continuousattendance;

import java.util.Optional;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

/**
 * 組織の連続出勤できる上限日数Repository
 * @author hiroko_miura
 *
 */
public interface MaxNumberDaysOfContAttOrgRepository {

	void insert(MaxNumberDaysOfContinuousAttendanceOrg maxContAttOrg, String companyId);
	
	void update(MaxNumberDaysOfContinuousAttendanceOrg maxContAttOrg, String companyId);
	
	void delete(TargetOrgIdenInfor targeOrg, String companyId);
	
	boolean exists(TargetOrgIdenInfor targeOrg, String companyId);
	
	Optional<MaxNumberDaysOfContinuousAttendanceOrg> get(TargetOrgIdenInfor targeOrg, String companyId);
}
