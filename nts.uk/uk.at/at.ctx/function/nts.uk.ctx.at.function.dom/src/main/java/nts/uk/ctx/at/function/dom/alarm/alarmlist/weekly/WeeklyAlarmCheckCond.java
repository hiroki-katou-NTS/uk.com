package nts.uk.ctx.at.function.dom.alarm.alarmlist.weekly;

import nts.uk.ctx.at.function.dom.alarm.checkcondition.ExtractionCondition;

import java.util.List;

/**
 * 週次のアラームチェック条件
 */
public class WeeklyAlarmCheckCond extends ExtractionCondition {

    // 任意抽出条件
    private List<String> listOptionalItem;

    @Override
    public void changeState(ExtractionCondition extractionCondition) {

    }
}
