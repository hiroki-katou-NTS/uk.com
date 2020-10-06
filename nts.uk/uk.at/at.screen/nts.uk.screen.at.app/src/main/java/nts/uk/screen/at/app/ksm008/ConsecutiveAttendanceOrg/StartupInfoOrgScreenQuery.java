package nts.uk.screen.at.app.ksm008.ConsecutiveAttendanceOrg;


import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveattendance.MaxDaysOfConsAttOrgRepository;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveattendance.MaxDaysOfConsecutiveAttendanceOrganization;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.GetTargetIdentifiInforService;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

/**
 * Screen H : 初期起動
 */
@Stateless
public class StartupInfoOrgScreenQuery {

    @Inject
    private MaxDaysOfConsAttOrgRepository MaxDaysOfConsAttOrgRepo;

    /**
     * 初期起動の情報を取得する
     */
    public ConsecutiveAttendanceOrgDto getStartupInfoOrg() {


        TargetOrgIdenInfor targeOrg = GetTargetIdentifiInforService.get();
    }

    /**
     * 組織情報を取得する
     *
     * @return 対象組織情報, 組織の表示情報
     */
    public void getOrgInfo(){

    }


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
