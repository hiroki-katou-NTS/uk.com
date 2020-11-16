package nts.uk.ctx.at.shared.dom.workrecord.alarm.attendanceitemconditions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import nts.arc.enums.EnumAdaptor;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * 単一値との比較演算の種別
 *
 * @author Thanh.LNP
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class CompareSingleValue<V> implements CheckConditions<V> {
    // 値
    private V value;

    // 比較演算子
    private final SingleValueCompareType compareOpertor;

    // 条件値の種別
    private final ConditionType conditionType;

    public CompareSingleValue(int compareOpertor, int conditionType) {
        super();
        this.compareOpertor = EnumAdaptor.valueOf(compareOpertor, SingleValueCompareType.class);
        this.conditionType = EnumAdaptor.valueOf(conditionType, ConditionType.class);
    }

    @Override
    public boolean check(Double targetV, Function<V, Double> value) {
        return checkWithFixedValue(targetV, value.apply(this.value));
    }

    private boolean checkWithFixedValue(Double target, Double compare) {
        if (target == null || compare == null) {
            return false;
        }
        switch (this.compareOpertor) {
            case EQUAL:
                return target.compareTo(compare) == 0;
            case GREATER_OR_EQUAL:
                return target.compareTo(compare) >= 0;
            case GREATER_THAN:
                return target.compareTo(compare) > 0;
            case LESS_OR_EQUAL:
                return target.compareTo(compare) <= 0;
            case LESS_THAN:
                return target.compareTo(compare) < 0;
            case NOT_EQUAL:
                return target.compareTo(compare) != 0;
            default:
                throw new RuntimeException("invalid compareOpertor: " + compareOpertor);
        }
    }

    private boolean checkWithAttendanceItem(Double target, Function<List<Integer>, List<Double>> getItemValue,
                                            Function<V, Double> getValue) {
        Double compareValue = getItemValue.apply(Arrays.asList(getValue.apply(this.value).intValue())).get(0);
        return checkWithFixedValue(target, compareValue);
    }

    public boolean check(Double targetValue, Function<List<Integer>, List<Double>> getItemValue,
                         Function<V, Double> getValue) {
        if (targetValue == null) {
            return false;
        }
        if (this.conditionType == ConditionType.FIXED_VALUE) {
            return this.checkWithFixedValue(targetValue, getValue.apply(value));
        } else {
            return this.checkWithAttendanceItem(targetValue, getItemValue, c -> getValue.apply(value));
        }
    }
}
