package nts.uk.screen.at.app.ksm008.sceenD;

import nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship.WorkMethodAttendance;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship.WorkMethodClassfication;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship.WorkMethodRelationshipComRepo;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship.WorkMethodRelationshipCompany;
import nts.uk.ctx.at.schedulealarm.dom.alarmcheck.AlarmCheckConditionScheduleRepository;
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
    public DetailDto getRelationshipDetails(RequestDetailPrams requestPrams) {

        //1: get(ログイン会社ID,対象勤務方法) : Optional<会社の勤務方法の関係性>
        //TODO Q&A not method in repo get(cid,code)
        Optional<WorkMethodRelationshipCompany> relationshipCompany = relationshipComRepo.getByCode(AppContexts.user().companyId(),requestPrams.getWorkTimeCode());

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
