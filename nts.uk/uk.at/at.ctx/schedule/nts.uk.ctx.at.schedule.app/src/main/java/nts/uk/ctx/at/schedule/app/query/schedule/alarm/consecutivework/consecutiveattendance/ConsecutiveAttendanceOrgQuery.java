package nts.uk.ctx.at.schedule.app.query.schedule.alarm.consecutivework.consecutiveattendance;

import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveattendance.MaxDaysOfConsAttOrgRepository;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveattendance.MaxDaysOfConsecutiveAttendanceOrganization;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
public class ConsecutiveAttendanceOrgQuery {
    @Inject
    private MaxDaysOfConsAttOrgRepository MaxDaysOfConsAttOrgRepo;

    /**
     * 組織の連続出勤できる上限日数を取得する
     *
     * @param targeOrg 対象組織
     * @return Optional<組織の連続出勤できる上限日数>
     */
    public Optional<MaxDaysOfConsecutiveAttendanceOrganization> getMaxConsDays(TargetOrgIdenInfor targeOrg) {
        String companyId = AppContexts.user().companyId();

        Optional<MaxDaysOfConsecutiveAttendanceOrganization> maxDaysOfConsecutiveAttendanceOrg = MaxDaysOfConsAttOrgRepo.get(targeOrg, companyId);

        return maxDaysOfConsecutiveAttendanceOrg;
    }
}
