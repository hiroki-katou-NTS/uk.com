package nts.uk.screen.at.app.ksm008.ConsecutiveWorkCmp;

import nts.uk.ctx.at.schedulealarm.dom.alarmcheck.AlarmCheckConditionSchedule;
import nts.uk.ctx.at.schedulealarm.dom.alarmcheck.AlarmCheckConditionScheduleCode;
import nts.uk.ctx.at.schedulealarm.dom.alarmcheck.AlarmCheckConditionScheduleRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Screen G : コードと名称と説明を取得する
 */
@Stateless
public class AlarmCheckQuery {

    @Inject
    private AlarmCheckConditionScheduleRepository alarmCheckConditionScheduleRepo;

    /**
     * コードと名称と説明を取得する
     */
    public AlarmCheckConditionSchedule get(AlarmCheckConditionScheduleCode code) {
        AlarmCheckConditionSchedule alarmCheckConditionSchedule = alarmCheckConditionScheduleRepo.get(AppContexts.user().contractCode(), AppContexts.user().companyId(), code);

        return alarmCheckConditionSchedule;
    }
}
