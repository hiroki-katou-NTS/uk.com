package nts.uk.screen.at.app.ksm008.screenE;

import nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship.*;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.screen.at.app.ksm008.ConsecutiveAttendanceOrg.OrgInfoDto;
import nts.uk.screen.at.app.ksm008.ConsecutiveAttendanceOrg.StartupInfoOrgScreenQuery;
import nts.uk.screen.at.app.ksm008.sceenD.WorkingHoursDto;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Screen KSM008E : 初期起動の情報取得する
 */
@Stateless
public class Ksm008EStartupInfoProcessor {

    @Inject
    private StartupInfoOrgScreenQuery infoOrgScreenQuery;

    @Inject
    private WorkMethodRelationshipOrgRepo workMethodRelationshipOrgRepo;

    @Inject
    private WorkTimeSettingRepository workTimeSettingRepository;

    /**
     * 初期起動の情報を取得する
     */
    public Ksm008EStartInfoDto getStartupInfo() {

        //1:組織情報を取得する(): ＜対象組織情報, 組織の表示情報＞
        OrgInfoDto infoDto = infoOrgScreenQuery.getOrgInfo();

        TargetOrgIdenInfor targetOrgIdenInfor = new TargetOrgIdenInfor(TargetOrganizationUnit.valueOf(
                infoDto.getUnit()),
                infoDto.getWorkplaceId() == null ? Optional.empty() : Optional.of(infoDto.getWorkplaceId()),
                infoDto.getWorkplaceGroupId() == null ? Optional.empty() :Optional.of(infoDto.getWorkplaceGroupId()));

        //2:組織の勤務方法の関係性リストを取得する(会社ID, 対象組織情報): List<組織の勤務方法の関係性>
        List<WorkMethodRelationshipOrganization> organizations = workMethodRelationshipOrgRepo.getAll(AppContexts.user().companyId(),targetOrgIdenInfor);

        List<String> listCodes = organizations.stream().
                filter(x -> x.getWorkMethodRelationship().getPrevWorkMethod().getWorkMethodClassification() == WorkMethodClassfication.ATTENDANCE).
                map(x -> ((WorkMethodAttendance)x.getWorkMethodRelationship().getPrevWorkMethod()).getWorkTimeCode().v()).collect(Collectors.toList());

        List<WorkTimeSetting> workTimeSettings = workTimeSettingRepository.findByCodes(AppContexts.user().companyId(), listCodes);
        List<WorkingHoursDto> workingHoursDtos =
                workTimeSettings.stream().map(i -> new WorkingHoursDto(i.getWorktimeCode().v(), i.getWorkTimeDisplayName().getWorkTimeName().v())).collect(Collectors.toList());

        return new Ksm008EStartInfoDto(infoDto,workingHoursDtos);

    }

}
