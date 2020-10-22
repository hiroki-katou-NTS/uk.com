package nts.uk.screen.at.app.ksm008.sceenD;

import nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship.*;
import nts.uk.ctx.at.schedulealarm.dom.alarmcheck.AlarmCheckConditionSchedule;
import nts.uk.ctx.at.schedulealarm.dom.alarmcheck.AlarmCheckConditionScheduleCode;
import nts.uk.ctx.at.schedulealarm.dom.alarmcheck.AlarmCheckConditionScheduleRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Screen KSM008D : 初期起動の情報取得する
 */
@Stateless
public class Ksm008DStartupInfoProcessor {


    @Inject
    private AlarmCheckConditionScheduleRepository repository;

    @Inject
    private WorkMethodRelationshipComRepo relationshipComRepo;

    @Inject
    private WorkTimeSettingRepository workTimeRepo;

    public Ksm008DStartInfoDto getStartupInfo(String code) {

        //1: コードと名称と説明を取得する(ログイン会社ID, コード) : 勤務予定のアラームチェック条件
        AlarmCheckConditionSchedule alarmCheckConditionSchedule = repository.get(AppContexts.user().contractCode(),AppContexts.user().companyId(),new AlarmCheckConditionScheduleCode(code));

        //2: 会社の勤務方法の関係性リストを取得する(会社ID) : List<会社の勤務方法の関係性>, List<就業時間帯の設定>

        List<WorkMethodRelationshipCompany> workMethodRelationships = relationshipComRepo.getAll(AppContexts.user().companyId());

        List<String> workHourCodeList = workMethodRelationships.stream().
                filter(x -> x.getWorkMethodRelationship().getPrevWorkMethod().getWorkMethodClassification() == WorkMethodClassfication.ATTENDANCE).
                map(x -> ((WorkMethodAttendance)x.getWorkMethodRelationship().getPrevWorkMethod()).getWorkTimeCode().v()).collect(Collectors.toList());

        //query : 就業時間帯情報を取得する
        List<WorkTimeSetting> workTimeSettingList = workTimeRepo
                .getListWorkTimeSetByListCode(AppContexts.user().companyId(), workHourCodeList);

        List<WorkingHoursDto> workingHoursDtos =
                workTimeSettingList.stream().map(i -> new WorkingHoursDto(i.getWorktimeCode().v(), i.getWorkTimeDisplayName().getWorkTimeName().v())).collect(Collectors.toList());

        return  new Ksm008DStartInfoDto(alarmCheckConditionSchedule,workingHoursDtos);
    }

}
