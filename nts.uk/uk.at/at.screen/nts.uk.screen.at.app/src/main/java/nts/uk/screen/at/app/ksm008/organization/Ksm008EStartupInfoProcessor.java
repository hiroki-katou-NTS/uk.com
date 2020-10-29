package nts.uk.screen.at.app.ksm008.organization;

import lombok.val;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship.*;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.screen.at.app.ksm008.ConsecutiveAttendanceOrg.OrgInfoDto;
import nts.uk.screen.at.app.ksm008.ConsecutiveAttendanceOrg.StartupInfoOrgScreenQuery;
import nts.uk.screen.at.app.ksm008.company.WorkingHoursAndWorkMethodDto;
import nts.uk.screen.at.app.ksm008.company.WorkingHoursDto;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;
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

        HashMap<String, Integer> workHourCodeAndMethods = new HashMap<>();

        organizations.stream().
            filter(x -> x.getWorkMethodRelationship().getPrevWorkMethod().getWorkMethodClassification() == WorkMethodClassfication.ATTENDANCE).
            map(x -> workHourCodeAndMethods.put(((WorkMethodAttendance) x.getWorkMethodRelationship().getPrevWorkMethod()).getWorkTimeCode().v(),
                x.getWorkMethodRelationship().getPrevWorkMethod().getWorkMethodClassification().value)).collect(Collectors.toList());

        List<WorkingHoursAndWorkMethodDto> workingHoursDtos = new ArrayList<>();

        if (workHourCodeAndMethods.entrySet().size() > 0){
            List<WorkTimeSetting> workTimeSettingList = workTimeSettingRepository.findByCodes(AppContexts.user().companyId(),
                workHourCodeAndMethods.entrySet().stream().map(x -> x.getKey()).collect(Collectors.toList()));
            workTimeSettingList.forEach(x -> {
                workingHoursDtos.add(new WorkingHoursAndWorkMethodDto(
                    x.getWorktimeCode().v(),
                    x.getWorkTimeDisplayName().getWorkTimeName().v(),
                    workHourCodeAndMethods.getOrDefault(x.getWorktimeCode().v(), 0)
                ));
            });
        }

        val holiday = organizations.stream().filter(x -> x.getWorkMethodRelationship().getPrevWorkMethod().
            getWorkMethodClassification() == WorkMethodClassfication.HOLIDAY).collect(Collectors.toList());
        if (holiday.size() != 0){
            workingHoursDtos.add(new WorkingHoursAndWorkMethodDto("000", "000", holiday.get(0).getWorkMethodRelationship().getPrevWorkMethod().getWorkMethodClassification().value));
        }
        return new Ksm008EStartInfoDto(infoDto,workingHoursDtos.stream().sorted(Comparator.comparing(WorkingHoursAndWorkMethodDto::getCode)).collect(Collectors.toList()));

    }

}
