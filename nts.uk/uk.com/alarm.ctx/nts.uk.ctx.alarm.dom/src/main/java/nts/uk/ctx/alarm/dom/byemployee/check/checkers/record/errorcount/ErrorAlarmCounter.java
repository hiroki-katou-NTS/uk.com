package nts.uk.ctx.alarm.dom.byemployee.check.checkers.record.errorcount;

import lombok.Getter;
import lombok.Value;
import lombok.val;
import nts.arc.time.calendar.period.GeneralPeriod;
import nts.gul.collection.IteratorUtil;
import nts.gul.util.value.DiscreteValue;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCategoryByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.AlarmRecordByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.DateInfo;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * エラーアラームの発生カウント
 * @param <K> チェック対象を指定するキー値の型
 * @param <D> 発生タイミングを表す型
 */
@Value
public class ErrorAlarmCounter<K, D extends Comparable<D> & DiscreteValue<D>> {

    K targetItemKey;

    /** アラームとする閾値 */
    ErrorAlarmCounterCondition condition;

    /** 連続発生でチェックするか */
    boolean isConsecutive;

    String message;

    /**
     * チェックする
     * @param employeeId 社員ID
     * @param checkingTargetPeriod チェック対象期間
     * @param category カテゴリ
     * @param getErrorAlarmName エラーアラーム名称を取得する関数
     * @param periodToDateInfo D型の期間開始・終了からDateInfoに変換する関数
     * @param errorAlarmChecker 指定キーのエラーアラームの発生タイミングを返す関数
     * @param <P>
     * @return
     */
    public <P extends GeneralPeriod<P, D>> Iterable<AlarmRecordByEmployee> check(
            String employeeId,
            P checkingTargetPeriod,
            AlarmListCategoryByEmployee category,
            Function<K, Optional<String>> getErrorAlarmName,
            BiFunction<D, D, DateInfo> periodToDateInfo,
            Function<K, Iterable<D>> errorAlarmChecker) {

        val errorTimings = errorAlarmChecker.apply(targetItemKey);
        String errorItemName = getErrorAlarmName.apply(targetItemKey).orElseGet(() -> targetItemKey + " 未登録");

        if (!isConsecutive) {
            int count = count(errorTimings);

            if (condition.matches(count)) {
                // 該当数チェックの場合、日付情報はチェック対象期間の全域とする
                val dateInfo = periodToDateInfo.apply(checkingTargetPeriod.start(), checkingTargetPeriod.end());

                val counterAlarm = new AlarmRecordByEmployee(
                        employeeId,
                        dateInfo,
                        category,
                        errorItemName,
                        String.format("%d回（%s)", count, condition.getConditionText()),
                        message);

                return Arrays.asList(counterAlarm);
            }

            return Collections.emptyList();
        }

        int consecutiveCount = 0;
        D current = null;
        for (val errorTiming : errorTimings) {
            if (current == null) {
                current = errorTiming;
                consecutiveCount = 1;
                continue;
            }

        }
    }


    private static int count(Iterable<?> iterable) {
        int count = 0;
        for (Object any : iterable) {
            count++;
        }
        return count;
    }
}
