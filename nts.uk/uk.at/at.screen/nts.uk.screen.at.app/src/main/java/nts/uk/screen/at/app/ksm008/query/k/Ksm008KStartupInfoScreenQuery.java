package nts.uk.screen.at.app.ksm008.query.k;

import nts.uk.ctx.at.schedulealarm.dom.alarmcheck.AlarmCheckConditionSchedule;
import nts.uk.ctx.at.schedulealarm.dom.alarmcheck.AlarmCheckConditionScheduleCode;
import nts.uk.ctx.at.schedulealarm.dom.alarmcheck.AlarmCheckConditionScheduleRepository;
import nts.uk.ctx.at.schedulealarm.dom.alarmcheck.SubCondition;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

/**
 * Screen KSM008K : 初期起動
 *
 * @author Md Rafiqul Islam
 */
@Stateless
public class Ksm008KStartupInfoScreenQuery {

    @Inject
    private AlarmCheckConditionScheduleRepository alarmCheckConditionScheduleRepo;

    /**
     * 初期起動の情報を取得する
     *
     * @param codeStr
     * @param workTimeList
     * @return Ksm008KStartInfoDto
     * @author rafiqul.islam
     */
    public Ksm008KStartInfoDto getStartupInfoCom(String codeStr, List<MaxDayOfWorkTimeCompanyDto> workTimeList) {
        AlarmCheckConditionScheduleCode code = new AlarmCheckConditionScheduleCode(codeStr);
        AlarmCheckConditionSchedule alarmCheckConditionSchedule = getComInfo(code);
        String conditionName = "";
        StringBuilder explanation = new StringBuilder();
        if (alarmCheckConditionSchedule != null) {
            conditionName = alarmCheckConditionSchedule.getConditionName();
            for (SubCondition subCondition : alarmCheckConditionSchedule.getSubConditions()) {
                explanation.append(subCondition.getExplanation());
            }
        }
        return new Ksm008KStartInfoDto(
                code.v(),
                conditionName,
                explanation.toString(),
                workTimeList
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