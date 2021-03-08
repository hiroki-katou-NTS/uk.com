package nts.uk.ctx.at.function.dom.alarm.alarmlist.schedaily;

import nts.uk.ctx.at.function.dom.alarm.checkcondition.ExtractionCondition;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * スケジュール日次のアラームチェック条件
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDailyAlarmCheckCond extends ExtractionCondition {

    // スケジュール日次の任意抽出条件
    private List<String> listOptionalItem;

    // スケジュール日次の固定抽出条件
    private List<String> listFixedItem;

    @Override
    public void changeState(ExtractionCondition extractionCondition) {

    }
}
