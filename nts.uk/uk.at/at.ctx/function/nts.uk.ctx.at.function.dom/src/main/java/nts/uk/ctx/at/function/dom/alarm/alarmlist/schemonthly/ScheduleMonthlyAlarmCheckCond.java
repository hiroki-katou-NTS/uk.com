package nts.uk.ctx.at.function.dom.alarm.alarmlist.schemonthly;

import nts.uk.ctx.at.function.dom.alarm.checkcondition.ExtractionCondition;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * スケジュール月次のアラームチェック条件
 */
@Getter
@AllArgsConstructor
public class ScheduleMonthlyAlarmCheckCond extends ExtractionCondition {

    // スケジュール月次の任意抽出条件
    private String listOptionalItem;

    // スケジュール月次の固定抽出条件
    private String listFixedItem;

    @Override
    public void changeState(ExtractionCondition extractionCondition) {

    }
}
