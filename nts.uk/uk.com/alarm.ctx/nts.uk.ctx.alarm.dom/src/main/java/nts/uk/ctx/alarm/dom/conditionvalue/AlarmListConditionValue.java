package nts.uk.ctx.alarm.dom.conditionvalue;

import lombok.Value;
import nts.uk.ctx.alarm.dom.AlarmListAlarmMessage;
import nts.uk.ctx.alarm.dom.byemployee.result.AlarmRecordByEmployee;

import java.util.Optional;

/**
 * アラームリスト条件値
 * @param <L> チェック対象値を返すロジック
 */
@Value
public class AlarmListConditionValue<L extends ConditionValueLogic<C>, C extends ConditionValueContext> {

    /** チェック対象値を返すロジック */
    L logic;

    /** 使用するか */
    boolean enabled;

    /** 条件式 */
    ConditionValueExpression expression;

    /** アラームメッセージ */
    AlarmListAlarmMessage alarmMessage;

    /**
     * 条件に該当するか
     * @param context コンテキスト情報
     * @return チェック対象値が条件に該当すればtrue
     */
    public Optional<AlarmRecordByEmployee> checkIfEnabled(C context) {

        if (!enabled) {
            return Optional.empty();
        }

        Double actualValue = logic.getValue(context);
        if (actualValue == null) {
            return Optional.empty();
        }

        if (!expression.matches(actualValue)) {
            return Optional.empty();
        }

        return Optional.of(createAlarmRecord(context, actualValue));
    }

    private AlarmRecordByEmployee createAlarmRecord(C context, Double actualValue) {

        return new AlarmRecordByEmployee(
                context.getEmployeeId(),
                context.getDateInfo(),
                context.getCategory(),
                logic.getName(),
                expression.toText(),
                String.format("実績: %.2f", actualValue),
                alarmMessage);
    }
}

