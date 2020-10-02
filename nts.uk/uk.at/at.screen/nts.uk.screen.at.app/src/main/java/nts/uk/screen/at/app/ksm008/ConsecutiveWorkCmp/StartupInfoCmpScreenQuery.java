package nts.uk.screen.at.app.ksm008.ConsecutiveWorkCmp;

import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveattendance.MaxDaysOfConsAttComRepository;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveattendance.MaxDaysOfConsecutiveAttendanceCompany;
import nts.uk.ctx.at.schedulealarm.dom.alarmcheck.AlarmCheckConditionSchedule;
import nts.uk.ctx.at.schedulealarm.dom.alarmcheck.AlarmCheckConditionScheduleCode;
import nts.uk.ctx.at.schedulealarm.dom.alarmcheck.SubCondition;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

/**
 * Screen G : 初期起動の情報を取得する
 */
@Stateless
public class StartupInfoCmpScreenQuery {
    @Inject
    private AlarmCheckQuery alarmCheckQuery;

    @Inject
    private MaxDaysOfConsAttComRepository maxDaysOfConsAttComRepository;

    /**
     * 取得する
     */
    public ConsecutiveWorkCmpDto get() {
        AlarmCheckConditionScheduleCode code = new AlarmCheckConditionScheduleCode("06");
        AlarmCheckConditionSchedule alarmCheckConditionSchedule = alarmCheckQuery.get(code);

        Optional<MaxDaysOfConsecutiveAttendanceCompany> maxConsDays = maxDaysOfConsAttComRepository.get(AppContexts.user().companyId());

        String conditionName = "";
        StringBuilder explanation = new StringBuilder();
        if (alarmCheckConditionSchedule != null) {
            conditionName = alarmCheckConditionSchedule.getConditionName();

            for (SubCondition subCondition : alarmCheckConditionSchedule.getSubConditions()) {
                explanation.append(subCondition.getExplanation());
            }
        }

        return new ConsecutiveWorkCmpDto(
                code.v(),
                conditionName,
                explanation.toString(),
                maxConsDays.isPresent() ? maxConsDays.get().getNumberOfDays().getNumberOfDays().v() : null
        );
    }
}
