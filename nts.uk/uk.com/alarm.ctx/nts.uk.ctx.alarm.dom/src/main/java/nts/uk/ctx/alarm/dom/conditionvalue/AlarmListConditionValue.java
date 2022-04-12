package nts.uk.ctx.alarm.dom.conditionvalue;

import lombok.Value;
import lombok.val;
import nts.uk.ctx.alarm.dom.byemployee.result.AlarmRecordByEmployee;

import java.util.Optional;

/**
 * アラームリストの条件値
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
    String message;

    /**
     * 条件に該当するか
     * @param context チェック対象値を返すロジックを実行するためのコンテキスト情報
     * @return チェック対象値が条件に該当すればtrue
     */
    public Optional<AlarmRecordByEmployee> checkIfEnabled(C context) {

        if (!enabled) {
            return Optional.empty();
        }

        Double actualValue = logic.getValue(context);

        if (!expression.matches(actualValue)) {
            return Optional.empty();
        }

        val alarm = new AlarmRecordByEmployee(
                context.getEmployeeId(),
                context.getDateInfo(),
                context.getCategory(),
                logic.getName(),
                expression.toText(),
                String.format("実績: %.2f", actualValue),
                message);

        return Optional.of(alarm);
    }
}

