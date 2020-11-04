package nts.uk.ctx.at.schedulealarm.app.query.alarmcheck;

import nts.uk.ctx.at.schedulealarm.dom.alarmcheck.AlarmCheckConditionSchedule;
import nts.uk.ctx.at.schedulealarm.dom.alarmcheck.AlarmCheckConditionScheduleCode;
import nts.uk.ctx.at.schedulealarm.dom.alarmcheck.AlarmCheckConditionScheduleRepository;
import nts.uk.ctx.at.schedulealarm.dom.alarmcheck.SubCondition;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
    public AlarmCheckConditionsQueryDto getCodeNameDescription(String code) {
        //1. 取得する
        AlarmCheckConditionSchedule alarmCheckConditionSchedule = alarmCheckConditionScheduleRepo.get(
                AppContexts.user().contractCode(),
                AppContexts.user().companyId(),
                new AlarmCheckConditionScheduleCode(code)
        );

        String conditionName = "";
        List<String> explanationList = Collections.emptyList();
        if (alarmCheckConditionSchedule != null) {
            conditionName = alarmCheckConditionSchedule.getConditionName();

            if (alarmCheckConditionSchedule.getSubConditions() != null && !alarmCheckConditionSchedule.getSubConditions().isEmpty()) {
                explanationList = alarmCheckConditionSchedule.getSubConditions().stream().map(item -> {
                    return item.getExplanation();
                }).collect(Collectors.toList());
            }
        }

        return new AlarmCheckConditionsQueryDto(conditionName, explanationList);
    }
}
