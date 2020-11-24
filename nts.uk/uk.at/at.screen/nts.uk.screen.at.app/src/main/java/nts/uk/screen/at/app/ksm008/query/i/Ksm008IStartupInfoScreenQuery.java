package nts.uk.screen.at.app.ksm008.query.i;

import nts.uk.ctx.at.schedulealarm.app.query.alarmcheck.AlarmCheckConditionsQuery;
import nts.uk.ctx.at.schedulealarm.app.query.alarmcheck.AlarmCheckConditionsQueryDto;
import nts.uk.ctx.at.schedulealarm.dom.alarmcheck.AlarmCheckConditionSchedule;
import nts.uk.ctx.at.schedulealarm.dom.alarmcheck.AlarmCheckConditionScheduleCode;
import nts.uk.ctx.at.schedulealarm.dom.alarmcheck.AlarmCheckConditionScheduleRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

/**
 * Screen KSM008I : 初期起動
 *
 * @author Md Rafiqul Islam
 */
@Stateless
public class Ksm008IStartupInfoScreenQuery {
    @Inject
    private AlarmCheckConditionScheduleRepository alarmCheckConditionScheduleRepo;

    @Inject
    private AlarmCheckConditionsQuery alarmCheckConditionsQuery;

    /**
     * 初期起動の情報を取得する
     */
    public Ksm008IStartInfoDto getStartupInfoCom(String codeStr, List<MaxDaysOfContinuousWorkTimeDto> workTimeList) {
        AlarmCheckConditionsQueryDto alarmCheckConditionsQueryDto = alarmCheckConditionsQuery.getCodeNameDescription(codeStr);
        return new Ksm008IStartInfoDto(
                codeStr,
                alarmCheckConditionsQueryDto.getConditionName(),
                alarmCheckConditionsQueryDto.getExplanationList().stream().map(e -> e.toString()).reduce("\n", String::concat),
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
