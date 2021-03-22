package nts.uk.screen.at.app.ksm008.organization;

import nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship.*;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.screen.at.app.ksm008.company.WorkingHoursDto;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Screen KSM008E : 組織の勤務方法の関係性明細を表示する
 */
@Stateless
public class GetRelationshipDetailsProcessor {

    @Inject
    private WorkMethodRelationshipOrgRepo workMethodRelationshipOrgRepo;

    @Inject
    private WorkTimeSettingRepository workTimeSettingRepository;

    /**
     * 初期起動の情報を取得する
     */
    public RelationshipDetailDto getRelationshipDetails(RequestPrams requestPrams) {

        //1: get(ログイン会社ID, 対象組織, 対象勤務方法 ):Optional<組織の勤務方法の関係性>
        TargetOrgIdenInfor targetOrgIdenInfor = new TargetOrgIdenInfor(TargetOrganizationUnit.valueOf(
            requestPrams.getUnit()),
            Optional.ofNullable(requestPrams.getWorkplaceId()),
            Optional.ofNullable(requestPrams.getWorkplaceGroupId()));
        WorkMethodAttendance workMethodAttendance = new WorkMethodAttendance(new WorkTimeCode(requestPrams.getWorkTimeCode()));
        WorkMethodHoliday workMethodHoliday = new WorkMethodHoliday();

        Optional<WorkMethodRelationshipOrganization> organization =
            workMethodRelationshipOrgRepo.getWithWorkMethod(AppContexts.user().companyId(), targetOrgIdenInfor,
                requestPrams.getTypeWorkMethod() == WorkMethodClassfication.ATTENDANCE.value ? workMethodAttendance : workMethodHoliday);

        List<String> workHourCodeList = new ArrayList<>();

        if (organization.isPresent()) {
            if (organization.get().getWorkMethodRelationship().getCurrentWorkMethodList().get(0).getWorkMethodClassification().value == WorkMethodClassfication.ATTENDANCE.value) {
                workHourCodeList.addAll(organization.get().getWorkMethodRelationship().getCurrentWorkMethodList().stream().map(x -> ((WorkMethodAttendance) x).getWorkTimeCode().v()).collect(Collectors.toList()));
            }
        }

        List<WorkingHoursDto> workingHoursDtos = new ArrayList<>();
        if (workHourCodeList.size() > 0) {
            List<WorkTimeSetting> workTimeSettingList = workTimeSettingRepository.getListWorkTimeSetByListCode(AppContexts.user().companyId(), workHourCodeList);
            workingHoursDtos = workTimeSettingList.stream().map(i -> new WorkingHoursDto(i.getWorktimeCode().v(), i.getWorkTimeDisplayName().getWorkTimeName().v())).collect(Collectors.toList());
        }

        return new RelationshipDetailDto(organization.map(x -> x.getWorkMethodRelationship().getPrevWorkMethod().getWorkMethodClassification().value).orElse(0),
            organization.map(x -> x.getWorkMethodRelationship().getSpecifiedMethod().value).orElse(0),
            organization.map(x -> x.getWorkMethodRelationship().getCurrentWorkMethodList().get(0).getWorkMethodClassification().value).orElse(0),
            workingHoursDtos);
    }

}
