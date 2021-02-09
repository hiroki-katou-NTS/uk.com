package nts.uk.ctx.at.function.dom.alarm.alarmlist.annual;

import nts.uk.ctx.at.function.dom.alarm.checkcondition.ExtractionCondition;

import java.util.List;

/**
 * スケジュール年間のアラームチェック条件
 */
public class ScheduleAnnualAlarmCheckCond extends ExtractionCondition {

    // スケジュール年間の任意抽出条件
    private List<String> listOptionalItem;

    @Override
    public void changeState(ExtractionCondition extractionCondition) {

    }
}
