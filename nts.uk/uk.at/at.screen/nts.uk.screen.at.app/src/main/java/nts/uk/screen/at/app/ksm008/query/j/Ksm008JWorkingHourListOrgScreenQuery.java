package nts.uk.screen.at.app.ksm008.query.j;

import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveworktime.ConsecutiveWorkTimeCode;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveworktime.MaxDaysOfContinuousWorkTimeOrganization;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveworktime.MaxDaysOfContinuousWorkTimeOrganizationRepository;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.shr.com.context.AppContexts;
import sun.invoke.empty.Empty;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * KSM008 J : 会社の就業時間帯の連続勤務上限明細を取得する
 *
 * @author rafiqul.islam
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class Ksm008JWorkingHourListOrgScreenQuery {

    @Inject
    private WorkTimeSettingRepository workTimeRepo;

    @Inject
    MaxDaysOfContinuousWorkTimeOrganizationRepository maxDaysOfContinuousWorkTimeOrganizationRepository;


    public MaxDaysOfContinuousWorkTimeListOrgDto get(Ksm008GetWkDetaislRequestParam requestParam) {
        TargetOrgIdenInfor targetOrgIdenInfor = requestParam.getWorkPlaceUnit() == 0
                ? TargetOrgIdenInfor.creatIdentifiWorkplace(requestParam.getWorkPlaceId())
                : TargetOrgIdenInfor.creatIdentifiWorkplaceGroup(requestParam.getWorkPlaceGroup());
        /*就業時間帯情報リストを取得する*/
        Optional<MaxDaysOfContinuousWorkTimeOrganization> maxDaysOfContinuousWorkTimeCompany = maxDaysOfContinuousWorkTimeOrganizationRepository.get(AppContexts.user().companyId(),
                targetOrgIdenInfor,
                new ConsecutiveWorkTimeCode(requestParam.getCode()));
        /*就業時間帯コードリスト */
        if(!maxDaysOfContinuousWorkTimeCompany.isPresent()){
            return new MaxDaysOfContinuousWorkTimeListOrgDto();
        }
        List<String> workHourCodeList = new ArrayList<>();
        if (maxDaysOfContinuousWorkTimeCompany.isPresent() && !maxDaysOfContinuousWorkTimeCompany.get().getMaxDaysContiWorktime().getWorkTimeCodes().isEmpty()) {
            workHourCodeList = maxDaysOfContinuousWorkTimeCompany
                    .get()
                    .getMaxDaysContiWorktime()
                    .getWorkTimeCodes()
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

        MaxDaysOfContinuousWorkTimeListOrgDto dto = new MaxDaysOfContinuousWorkTimeListOrgDto(
                maxDaysOfContinuousWorkTimeCompany.get().getCode().v(),
                maxDaysOfContinuousWorkTimeCompany.get().getName().v().trim(),
                maxDaysOfContinuousWorkTimeCompany.get().getMaxDaysContiWorktime().getNumberOfDays().v(),
                workhourList
        );
        return dto;
    }
}
