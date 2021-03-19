package nts.uk.ctx.at.function.dom.alarm.alarmlist.weekly;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.ExtractionCondition;

/**
 * 週次のアラームチェック条件
 */
@Getter
@AllArgsConstructor
public class WeeklyAlarmCheckCond extends ExtractionCondition {

    // 任意抽出条件
    private String listOptionalItem;

    @Override
    public void changeState(ExtractionCondition extractionCondition) {

    }
}
