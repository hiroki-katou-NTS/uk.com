package nts.uk.screen.at.app.ksm008.query.l;

import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.limitworktime.MaxDayOfWorkTimeCode;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.limitworktime.MaxDayOfWorkTimeOrganization;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.limitworktime.MaxDayOfWorkTimeOrganizationRepo;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.screen.at.app.ksm008.query.j.Ksm008GetWkDetaislRequestParam;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * KSM008 L : 組織の就業時間帯の上限一覧を選択する
 *
 * @author rafiqul.islam
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class Ksm008LWorkingHourListOrgScreenQuery {

    @Inject
    private WorkTimeSettingRepository workTimeRepo;

    @Inject
    MaxDayOfWorkTimeOrganizationRepo maxDayOfWorkTimeOrganizationRepo;

    public MaxDaysOfWorkTimeListOrgDto get(Ksm008GetWkDetaislRequestParam requestParam) {
        TargetOrgIdenInfor targetOrgIdenInfor = requestParam.getWorkPlaceUnit() == 0
                ? TargetOrgIdenInfor.creatIdentifiWorkplace(requestParam.getWorkPlaceId())
                : TargetOrgIdenInfor.creatIdentifiWorkplaceGroup(requestParam.getWorkPlaceGroup());
        /*就業時間帯情報リストを取得する*/
        Optional<MaxDayOfWorkTimeOrganization> maxDayOfWorkTimeOrganization = maxDayOfWorkTimeOrganizationRepo.getWithCode(AppContexts.user().companyId(),
                targetOrgIdenInfor,
                new MaxDayOfWorkTimeCode(requestParam.getCode()));        /*就業時間帯コードリスト */
        if(!maxDayOfWorkTimeOrganization.isPresent()){
            return new MaxDaysOfWorkTimeListOrgDto();
        }
        List<String> workHourCodeList = new ArrayList<>();
        if (maxDayOfWorkTimeOrganization.isPresent() && !maxDayOfWorkTimeOrganization.get().getMaxDayOfWorkTime().getWorkTimeCodeList().isEmpty()) {
            workHourCodeList = maxDayOfWorkTimeOrganization
                    .get()
                    .getMaxDayOfWorkTime()
                    .getWorkTimeCodeList()
                    .stream()
                    .map(item -> item.v())
                    .collect(Collectors.toList());
        }
        //就業時間帯情報を取得する
        List<WorkTimeSetting> workTimeSettingList = workTimeRepo
                .getListWorkTimeSetByListCode(AppContexts.user().companyId(), workHourCodeList);
        // working hours list
        List<WorkingHoursOrgDTO> workhourList = workTimeSettingList
                .stream()
                .map(item -> new WorkingHoursOrgDTO(item.getWorktimeCode().v(), item.getWorkTimeDisplayName().getWorkTimeName().v()))
                .collect(Collectors.toList());
        MaxDaysOfWorkTimeListOrgDto dto = new MaxDaysOfWorkTimeListOrgDto(
                maxDayOfWorkTimeOrganization.get().getCode().v(),
                maxDayOfWorkTimeOrganization.get().getName().v().trim(),
                maxDayOfWorkTimeOrganization.get().getMaxDayOfWorkTime().getMaxDay().v(),
                workhourList
        );
        return dto;
    }
}