package nts.uk.ctx.at.shared.dom.workrecord.alarm.attendanceitemconditions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import nts.arc.enums.EnumAdaptor;

import java.util.function.Function;

/**
 * 範囲との比較
 *
 * @author Thanh.LNP
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class CompareRange<V> implements CheckConditions<V> {
    // 開始値
    private V startValue;

    // 終了値
    private V endValue;

    // 比較演算子
    private final RangeCompareType compareOperator;

    public CompareRange(int compareOperator) {
        super();
        this.compareOperator = EnumAdaptor.valueOf(compareOperator, RangeCompareType.class);
    }

    public void setValue(V startValue, V endValue) {
        this.startValue = startValue;
        this.endValue = endValue;
    }

    @Override
    public boolean check(Double targetV, Function<V, Double> value) {
        Double startV = value.apply(startValue);
        Double endV = value.apply(endValue);

        if (targetV == null || startV == null || endV == null) {
            return false;
        }

        switch (this.compareOperator) {
            case BETWEEN_RANGE_CLOSED:
                return targetV.compareTo(startV) >= 0 && targetV.compareTo(endV) <= 0;
            case BETWEEN_RANGE_OPEN:
                return targetV.compareTo(startV) > 0 && targetV.compareTo(endV) < 0;
            case OUTSIDE_RANGE_CLOSED:
                return targetV.compareTo(startV) <= 0 || targetV.compareTo(endV) >= 0;
            case OUTSIDE_RANGE_OPEN:
                return targetV.compareTo(startV) < 0 || targetV.compareTo(endV) > 0;
            default:
                return false;
        }
    }
}
