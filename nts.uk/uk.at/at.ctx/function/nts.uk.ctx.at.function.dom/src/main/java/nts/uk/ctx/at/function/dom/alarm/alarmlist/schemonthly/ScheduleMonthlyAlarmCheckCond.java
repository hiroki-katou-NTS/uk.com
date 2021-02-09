package nts.uk.ctx.at.function.dom.alarm.alarmlist.schemonthly;

import nts.uk.ctx.at.function.dom.alarm.checkcondition.ExtractionCondition;

import java.util.List;

/**
 * スケジュール月次のアラームチェック条件
 */
public class ScheduleMonthlyAlarmCheckCond extends ExtractionCondition {

    // スケジュール月次の任意抽出条件
    private List<String> listOptionalItem;

    // スケジュール月次の固定抽出条件
    private List<String> listFixedItem;

    @Override
    public void changeState(ExtractionCondition extractionCondition) {

    }
}
