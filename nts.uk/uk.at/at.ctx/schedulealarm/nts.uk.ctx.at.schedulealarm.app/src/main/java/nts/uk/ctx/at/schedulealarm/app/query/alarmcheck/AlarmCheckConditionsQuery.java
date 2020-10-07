package nts.uk.ctx.at.schedulealarm.app.query.alarmcheck;

import nts.uk.ctx.at.schedulealarm.dom.alarmcheck.AlarmCheckConditionSchedule;
import nts.uk.ctx.at.schedulealarm.dom.alarmcheck.AlarmCheckConditionScheduleCode;
import nts.uk.ctx.at.schedulealarm.dom.alarmcheck.AlarmCheckConditionScheduleRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class AlarmCheckConditionsQuery {
    @Inject
    private AlarmCheckConditionScheduleRepository alarmCheckConditionScheduleRepo;

    /**
     * コードと名称と説明を取得する
     *
     * @param code コード
     * @return 予定のアラームチェック条件
     */
    public AlarmCheckConditionSchedule getCodeNameDescription(AlarmCheckConditionScheduleCode code) {
        AlarmCheckConditionSchedule alarmCheckConditionSchedule = alarmCheckConditionScheduleRepo.get(AppContexts.user().contractCode(), AppContexts.user().companyId(), code);

        return alarmCheckConditionSchedule;
    }
}
