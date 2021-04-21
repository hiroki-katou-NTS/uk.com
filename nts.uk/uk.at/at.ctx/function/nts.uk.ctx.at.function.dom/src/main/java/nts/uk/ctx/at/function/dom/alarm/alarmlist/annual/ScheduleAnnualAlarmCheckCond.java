package nts.uk.ctx.at.function.dom.alarm.alarmlist.annual;

import nts.uk.ctx.at.function.dom.alarm.checkcondition.ExtractionCondition;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * スケジュール年間のアラームチェック条件
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ScheduleAnnualAlarmCheckCond extends ExtractionCondition {

    // スケジュール年間の任意抽出条件
    private String listOptionalItem;

    @Override
    public void changeState(ExtractionCondition extractionCondition) {

    }
}
