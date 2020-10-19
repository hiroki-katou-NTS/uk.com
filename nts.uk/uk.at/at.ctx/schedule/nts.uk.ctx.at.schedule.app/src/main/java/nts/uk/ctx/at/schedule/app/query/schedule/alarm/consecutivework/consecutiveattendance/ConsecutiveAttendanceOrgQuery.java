package nts.uk.ctx.at.schedule.app.query.schedule.alarm.consecutivework.consecutiveattendance;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveattendance.MaxDaysOfConsAttOrgRepository;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveattendance.MaxDaysOfConsecutiveAttendanceOrganization;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;
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
     * @param unit             対象組織.単位
     * @param workplaceId      対象組織.職場ID
     * @param workplaceGroupId 対象組織.職場グループID
     * @return Optional<組織の連続出勤できる上限日数>
     */
    public Integer getMaxConsDays(int unit, String workplaceId, String workplaceGroupId) {
        TargetOrgIdenInfor targeOrg = new TargetOrgIdenInfor(
                EnumAdaptor.valueOf(unit, TargetOrganizationUnit.class),
                Optional.ofNullable(workplaceId),
                Optional.ofNullable(workplaceGroupId)
        );

        String companyId = AppContexts.user().companyId();

        Optional<MaxDaysOfConsecutiveAttendanceOrganization> maxDaysOfConsecutiveAttendanceOrg = MaxDaysOfConsAttOrgRepo.get(targeOrg, companyId);

        return maxDaysOfConsecutiveAttendanceOrg.isPresent() ? maxDaysOfConsecutiveAttendanceOrg.get().getNumberOfDays().getNumberOfDays().v() : null;
    }
}
