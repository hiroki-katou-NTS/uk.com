package nts.uk.screen.at.app.ksm008.company;

import nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship.*;
import nts.uk.ctx.at.schedulealarm.dom.alarmcheck.AlarmCheckConditionScheduleRepository;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Screen KSM008D : 会社の勤務方法の関係性明細を表示する
 */
@Stateless
public class GetDetailsProcessor {

    @Inject
    private AlarmCheckConditionScheduleRepository repository;

    @Inject
    private WorkMethodRelationshipComRepo relationshipComRepo;

    @Inject
    private WorkTimeSettingRepository workTimeRepo;

    /**
     * 初期起動の情報を取得する
     */
    public DetailDto getDetails(RequestDetailPrams requestPrams) {
        WorkMethodAttendance workMethodAttendance = new WorkMethodAttendance(new WorkTimeCode(requestPrams.getWorkTimeCode()));
        WorkMethodHoliday workMethodHoliday = new WorkMethodHoliday();

        //1: get(ログイン会社ID,対象勤務方法) : Optional<会社の勤務方法の関係性>
        Optional<WorkMethodRelationshipCompany> relationshipCompany = relationshipComRepo.getWithWorkMethod(AppContexts.user().companyId(),requestPrams.getWorkTimeCode().equals("000") ? workMethodHoliday : workMethodAttendance);

        List<String> workHourCodeList = new ArrayList<>();

        if (relationshipCompany.isPresent()){
            if (!requestPrams.getWorkTimeCode().equals("000")) {
                workHourCodeList.addAll(relationshipCompany.get().getWorkMethodRelationship().getCurrentWorkMethodList().stream().map(x -> ((WorkMethodAttendance)x).getWorkTimeCode().v()).collect(Collectors.toList()));
            }
        }

        //就業時間帯情報を取得する
        List<WorkTimeSetting> workTimeSettingList = workTimeRepo.getListWorkTimeSetByListCode(AppContexts.user().companyId(), workHourCodeList);
        List<WorkingHoursDto> workingHoursDtos =
                workTimeSettingList.stream().map(i -> new WorkingHoursDto(i.getWorktimeCode().v(), i.getWorkTimeDisplayName().getWorkTimeName().v())).collect(Collectors.toList());

        return new DetailDto(requestPrams.getWorkTimeCode().equals("000") ? WorkMethodClassfication.HOLIDAY.value : WorkMethodClassfication.ATTENDANCE.value,
                relationshipCompany.map(workMethodRelationshipCompany -> workMethodRelationshipCompany.getWorkMethodRelationship().getSpecifiedMethod().value).orElse(0),
                relationshipCompany.map(workMethodRelationshipCompany1 -> workMethodRelationshipCompany1.getWorkMethodRelationship().getCurrentWorkMethodList().get(0).getWorkMethodClassification().value).orElse(0),
                workingHoursDtos);
    }

}
