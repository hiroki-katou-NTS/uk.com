package nts.uk.ctx.at.function.dom.alarm.alarmlist.schedaily;

import nts.uk.ctx.at.function.dom.alarm.checkcondition.ExtractionCondition;

import java.util.List;

/**
 * スケジュール日次のアラームチェック条件
 */
public class ScheduleDailyAlarmCheckCond extends ExtractionCondition {

    // スケジュール日次の任意抽出条件
    private List<String> listOptionalItem;

    // スケジュール日次の固定抽出条件
    private List<String> listFixedItem;

    @Override
    public void changeState(ExtractionCondition extractionCondition) {

    }
}
