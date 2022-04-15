package nts.uk.ctx.alarm.dom.fixedlogic;

import lombok.Value;
import nts.uk.ctx.alarm.dom.AlarmListAlarmMessage;
import nts.uk.ctx.alarm.dom.byemployee.result.AlarmRecordByEmployee;

import java.util.Collections;
import java.util.Optional;
import java.util.function.BiFunction;

/**
 * 固定のチェック条件の設定
 * @param <L> 固定ロジックのEnum型
 */
@Value
public class FixedLogicSetting<L> {

    /** ロジック */
    L logic;

    /** 使用するか */
    boolean enabled;

    /** メッセージ */
    AlarmListAlarmMessage message;

    public Iterable<AlarmRecordByEmployee> checkIfEnabled(BiFunction<L, AlarmListAlarmMessage, Iterable<AlarmRecordByEmployee>> checker) {
        if (enabled) {
            return checker.apply(logic, message);
        }

        return Collections.emptyList();
    }

    public Optional<AlarmRecordByEmployee> checkIfEnabledOpt(BiFunction<L, AlarmListAlarmMessage, Optional<AlarmRecordByEmployee>> checker) {
        if (enabled) {
            return checker.apply(logic, message);
        }

        return Optional.empty();
    }
}
