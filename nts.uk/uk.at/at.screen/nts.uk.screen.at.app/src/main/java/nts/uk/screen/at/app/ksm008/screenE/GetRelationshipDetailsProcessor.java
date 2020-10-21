package nts.uk.screen.at.app.ksm008.screenE;

import nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship.*;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.screen.at.app.ksm008.ConsecutiveAttendanceOrg.StartupInfoOrgScreenQuery;
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
    private StartupInfoOrgScreenQuery infoOrgScreenQuery;

    @Inject
    private WorkMethodRelationshipOrgRepo workMethodRelationshipOrgRepo;

    @Inject
    private WorkTimeSettingRepository workTimeSettingRepository;

    /**
     * 初期起動の情報を取得する
     */
    public List<RelationshipDetailDto> getRelationshipDetails(RequestPrams requestPrams) {

        //1: get(ログイン会社ID, 対象組織, 対象勤務方法 ):Optional<組織の勤務方法の関係性>
        TargetOrgIdenInfor targetOrgIdenInfor = new TargetOrgIdenInfor(TargetOrganizationUnit.valueOf(requestPrams.getUnit()),
                Optional.of(requestPrams.getWorkplaceId()),Optional.of(requestPrams.getWorkplaceGroupId()));
        WorkMethodHoliday workMethodHoliday = new WorkMethodHoliday();
        WorkMethodContinuousWork methodContinuousWork = new WorkMethodContinuousWork();
        WorkMethodAttendance workMethodAttendance1 = new WorkMethodAttendance(new WorkTimeCode(requestPrams.getWorkTimeCode()));
        Optional<WorkMethodRelationshipOrganization> organization =
                workMethodRelationshipOrgRepo.getWithWorkMethod(AppContexts.user().companyId(),targetOrgIdenInfor,
                        requestPrams.getWorkMethodClassfication() == 1 ? workMethodAttendance1 : requestPrams.getWorkMethodClassfication() == 2 ? workMethodHoliday : methodContinuousWork);

        List<String> listCodes = new ArrayList<>();

        if (organization.isPresent()){
            if (requestPrams.getWorkMethodClassfication() == 1) {
                listCodes.addAll(organization.get().getWorkMethodRelationship().getCurrentWorkMethodList().stream().map(x -> ((WorkMethodAttendance)x).getWorkTimeCode().v()).collect(Collectors.toList()));
            }
//            else if (requestPrams.getWorkMethodClassfication() == 2){
//                listCode.addAll(organization.get().getWorkMethodRelationship().getCurrentWorkMethodList().stream().map(x -> ((WorkMethodHoliday)x).).collect(Collectors.toList()));
//
//                listCode.add(((WorkMethodHoliday) organization.get().getWorkMethodRelationship().getCurrentWorkMethodList()));
//            }else {
//                listCode.add(((WorkMethodAttendance) organization.get().getWorkMethodRelationship().getPrevWorkMethod()).getWorkTimeCode().v());
//            }
        }

        List<RelationshipDetailDto> detailDtos = new ArrayList<>();
        List<WorkTimeSetting> workTimeSettings = workTimeSettingRepository.findByCodes(AppContexts.user().companyId(), listCodes);
        workTimeSettings.forEach(x -> {
            detailDtos.add(new RelationshipDetailDto(x.getWorktimeCode().v(),x.getWorkTimeDisplayName().getWorkTimeName().v()));
        });

        return detailDtos;
    }

}
