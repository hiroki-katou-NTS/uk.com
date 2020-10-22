package nts.uk.screen.at.app.ksm008.screenE;

import nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship.WorkMethodAttendance;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship.WorkMethodClassfication;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship.WorkMethodRelationshipOrgRepo;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship.WorkMethodRelationshipOrganization;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;
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
 * Screen KSM008E : 組織を選択時
 */
@Stateless
public class GetLstRelshipsBetweenOgrWorkProcessor {

    @Inject
    private StartupInfoOrgScreenQuery infoOrgScreenQuery;

    @Inject
    private WorkMethodRelationshipOrgRepo workMethodRelationshipOrgRepo;

    @Inject
    private WorkTimeSettingRepository workTimeSettingRepository;

    /**
     * 初期起動の情報を取得する
     */
    public List<RelationshipDetailDto> getLstRelshipsBetweenOgrWork(RequestRelshipPrams requestPrams) {
//
//        TargetOrgIdenInfor targetOrgIdenInfor = new TargetOrgIdenInfor(TargetOrganizationUnit.valueOf(requestPrams.getUnit()), Optional.of(requestPrams.getWorkplaceId()),Optional.of(requestPrams.getWorkplaceGroupId()));
//
//        //2:組織の勤務方法の関係性リストを取得する(会社ID, 対象組織情報): List<組織の勤務方法の関係性>
//        List<WorkMethodRelationshipOrganization> organizations = workMethodRelationshipOrgRepo.getAll(AppContexts.user().companyId(),targetOrgIdenInfor);
//        List<String> listCodes = organizations.stream().
//                filter(x -> x.getWorkMethodRelationship().getPrevWorkMethod().getWorkMethodClassification() == WorkMethodClassfication.ATTENDANCE).
//                map(x -> ((WorkMethodAttendance)x.getWorkMethodRelationship().getPrevWorkMethod()).getWorkTimeCode().v()).collect(Collectors.toList());
//        List<RelationshipDetailDto> detailDtos = new ArrayList<>();
//        List<WorkTimeSetting> workTimeSettings = workTimeSettingRepository.findByCodes(AppContexts.user().companyId(), listCodes);
//        workTimeSettings.forEach(x -> detailDtos.add(new RelationshipDetailDto(
//                x.getWorktimeCode().v(),
//                x.getWorkTimeDisplayName().getWorkTimeName().v()
//        )));

        return null;
    }

}
