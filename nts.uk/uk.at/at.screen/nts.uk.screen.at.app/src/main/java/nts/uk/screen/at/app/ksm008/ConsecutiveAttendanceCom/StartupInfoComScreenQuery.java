package nts.uk.screen.at.app.ksm008.ConsecutiveAttendanceCom;

import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveattendance.MaxDaysOfConsAttComRepository;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveattendance.MaxDaysOfConsecutiveAttendanceCompany;
import nts.uk.ctx.at.schedulealarm.dom.alarmcheck.AlarmCheckConditionSchedule;
import nts.uk.ctx.at.schedulealarm.dom.alarmcheck.AlarmCheckConditionScheduleCode;
import nts.uk.ctx.at.schedulealarm.dom.alarmcheck.AlarmCheckConditionScheduleRepository;
import nts.uk.ctx.at.schedulealarm.dom.alarmcheck.SubCondition;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

/**
 * Screen G : 初期起動
 */
@Stateless
public class StartupInfoComScreenQuery {

    @Inject
    private MaxDaysOfConsAttComRepository maxDaysOfConsAttComRepo;

    @Inject
    private AlarmCheckConditionScheduleRepository alarmCheckConditionScheduleRepo;

    /**
     * 初期起動の情報を取得する
     */
    public ConsecutiveAttendanceComDto getStartupInfoCom() {
        AlarmCheckConditionScheduleCode code = new AlarmCheckConditionScheduleCode("06");
        AlarmCheckConditionSchedule alarmCheckConditionSchedule = getComInfo(code);

        Optional<MaxDaysOfConsecutiveAttendanceCompany> maxConsDays = maxDaysOfConsAttComRepo.get(AppContexts.user().companyId());

        String conditionName = "";
        StringBuilder explanation = new StringBuilder();
        if (alarmCheckConditionSchedule != null) {
            conditionName = alarmCheckConditionSchedule.getConditionName();

            for (SubCondition subCondition : alarmCheckConditionSchedule.getSubConditions()) {
                explanation.append(subCondition.getExplanation());
            }
        }

        return new ConsecutiveAttendanceComDto(
                code.v(),
                conditionName,
                explanation.toString(),
                maxConsDays.isPresent() ? maxConsDays.get().getNumberOfDays().getNumberOfDays().v() : null
        );
    }

    /**
     * コードと名称と説明を取得する
     *
     * @return 予定のアラームチェック条件
     */
    public AlarmCheckConditionSchedule getComInfo(AlarmCheckConditionScheduleCode code) {
        AlarmCheckConditionSchedule alarmCheckConditionSchedule = alarmCheckConditionScheduleRepo.get(AppContexts.user().contractCode(), AppContexts.user().companyId(), code);

        return alarmCheckConditionSchedule;
    }
}
