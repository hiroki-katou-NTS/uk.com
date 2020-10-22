package nts.uk.screen.at.app.ksm008.ConsecutiveAttendanceCom;

import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveattendance.MaxDaysOfConsAttComRepository;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveattendance.MaxDaysOfConsecutiveAttendanceCompany;
import nts.uk.ctx.at.schedulealarm.app.query.alarmcheck.AlarmCheckConditionsQuery;
import nts.uk.ctx.at.schedulealarm.app.query.alarmcheck.AlarmCheckConditionsQueryDto;
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
    private AlarmCheckConditionsQuery alarmCheckConditionsQuery;

    /**
     * 初期起動の情報を取得する
     */
    public ConsecutiveAttendanceComDto getStartupInfoCom(String code) {
        AlarmCheckConditionsQueryDto codeNameDescription = alarmCheckConditionsQuery.getCodeNameDescription(code);

        Optional<MaxDaysOfConsecutiveAttendanceCompany> maxConsDays = maxDaysOfConsAttComRepo.get(AppContexts.user().companyId());

        return new ConsecutiveAttendanceComDto(
                code,
                codeNameDescription.getConditionName(),
                codeNameDescription.getExplanationList(),
                maxConsDays.isPresent() ? maxConsDays.get().getNumberOfDays().getNumberOfDays().v() : null
        );
    }
}
