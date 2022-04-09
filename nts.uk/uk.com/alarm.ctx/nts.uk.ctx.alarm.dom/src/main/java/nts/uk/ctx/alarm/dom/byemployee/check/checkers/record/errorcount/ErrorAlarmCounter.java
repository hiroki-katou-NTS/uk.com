package nts.uk.ctx.alarm.dom.byemployee.check.checkers.record.errorcount;

import lombok.Value;
import lombok.val;
import nts.arc.time.calendar.period.GeneralPeriod;
import nts.gul.util.value.DiscreteValue;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCategoryByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.AlarmRecordByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.DateInfo;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * エラーアラーム発生カウント
 * @param <C> チェック対象を指定するキー値の型
 * @param <D> 発生タイミングを表す型
 */
@Value
public class ErrorAlarmCounter<C, D extends Comparable<D> & DiscreteValue<D>> {

    /** エラーアラームコード */
    C errorAlarmCode;

    /** アラーム条件 */
    ErrorAlarmCounterCondition condition;

    /** 連続発生でチェックするか */
    boolean isConsecutive;

    /** メッセージ */
    String message;

    /**
     * 発生数をチェックする
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
            Function<C, Optional<String>> getErrorAlarmName,
            BiFunction<D, D, DateInfo> periodToDateInfo,
            Function<C, Iterable<D>> errorAlarmChecker) {

        BiFunction<DateInfo, String, AlarmRecordByEmployee> createRecord = (dateInfo, alarmCondition) -> new AlarmRecordByEmployee(
                employeeId,
                dateInfo,
                category,
                getErrorAlarmName.apply(errorAlarmCode).orElseGet(() -> errorAlarmCode + " 未登録"),
                alarmCondition,
                message);

        val errorTimings = errorAlarmChecker.apply(errorAlarmCode);

        if (isConsecutive) {
            return checkConsecutive(periodToDateInfo, createRecord, errorTimings);
        } else {
            return checkErrorCount(checkingTargetPeriod, periodToDateInfo, createRecord, errorTimings);
        }
    }

    /**
     * 連続発生をチェックする
     * @param periodToDateInfo
     * @param createRecord
     * @param errorTimings
     * @return
     */
    private Iterable<AlarmRecordByEmployee> checkConsecutive(
            BiFunction<D, D, DateInfo> periodToDateInfo,
            BiFunction<DateInfo, String, AlarmRecordByEmployee> createRecord,
            Iterable<D> errorTimings) {

        val results = new ArrayList<AlarmRecordByEmployee>();

        // 連続カウント
        int count = 0;

        // 連続発生の先頭タイミング
        D start = null;

        // １つ前のタイミング
        D prev = null;

        for (val current : errorTimings) {

            // ループ初回
            if (prev == null) {
                start = prev = current;
                count = 1;
                continue;
            }

            // 連続中
            if (prev.nextValue(true).equals(current)) {
                count++;
                continue;
            }

            // 連続が途絶えたのでリセット
            val dateInfo = periodToDateInfo.apply(start, current);
            String alarmCondition = String.format("連続%d回（%s)", count, condition.getConditionText());
            results.add(createRecord.apply(dateInfo, alarmCondition));

            count = 1;
            start = current;
        }

        return results;
    }

    /**
     * 該当数をチェックする（連続発生ではない）
     * @param checkingTargetPeriod
     * @param periodToDateInfo
     * @param createRecord
     * @param errorTimings
     * @param <P>
     * @return
     */
    private <P extends GeneralPeriod<P, D>> List<AlarmRecordByEmployee> checkErrorCount(
            P checkingTargetPeriod,
            BiFunction<D, D, DateInfo> periodToDateInfo,
            BiFunction<DateInfo, String, AlarmRecordByEmployee> createRecord,
            Iterable<D> errorTimings) {

        int count = count(errorTimings);

        if (condition.matches(count)) {
            // 該当数チェックの場合、日付情報はチェック対象期間の全域とする
            val dateInfo = periodToDateInfo.apply(checkingTargetPeriod.start(), checkingTargetPeriod.end());
            String alarmCondition = String.format("%d回（%s)", count, condition.getConditionText());
            return Arrays.asList(createRecord.apply(dateInfo, alarmCondition));
        }

        return Collections.emptyList();
    }

    private static int count(Iterable<?> iterable) {
        int count = 0;
        for (Object any : iterable) {
            count++;
        }
        return count;
    }
}
