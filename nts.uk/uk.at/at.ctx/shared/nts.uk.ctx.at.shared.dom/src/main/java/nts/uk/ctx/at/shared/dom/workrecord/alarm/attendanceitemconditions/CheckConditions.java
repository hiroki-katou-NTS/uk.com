package nts.uk.ctx.at.shared.dom.workrecord.alarm.attendanceitemconditions;

import java.util.function.Function;

/**
 * チェック条件
 *
 * @author Thanh.LNP
 */
public interface CheckConditions<V> {

    /**
     * @param targetV
     * @param value
     * @return boolean
     */
    boolean check(Double targetV, Function<V, Double> value);
}
