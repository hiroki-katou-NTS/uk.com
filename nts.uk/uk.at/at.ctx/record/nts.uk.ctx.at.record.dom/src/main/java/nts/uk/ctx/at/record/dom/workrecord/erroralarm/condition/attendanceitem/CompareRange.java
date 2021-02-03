/**
 * 11:18:22 AM Nov 9, 2017
 */
package nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem;

import java.util.function.Function;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.RangeCompareType;

/**
 * @author hungnm
 *
 */
// 範囲との比較
public class CompareRange<V> extends CheckedCondition {

    // 開始値
    private V startValue;

    // 終了値
    private V endValue;

    // 比較演算子
    @Getter
    private RangeCompareType compareOperator;

    public CompareRange(int compareOperator) {
        super();
        this.compareOperator = EnumAdaptor.valueOf(compareOperator, RangeCompareType.class);
    }
    
    public CompareRange(RangeCompareType compareOperator) {
        super();
        this.compareOperator = compareOperator;
    }

    /**
     * @return the startValue
     */
    public V getStartValue() {
        return startValue;
    }

    /**
     * @param startValue the startValue to set
     */
    public CompareRange<V> setStartValue(V startValue) {
        this.startValue = startValue;
        return this;
    }

    /**
     * @return the endValue
     */
    public V getEndValue() {
        return endValue;
    }

    /**
     * @param endValue the endValue to set
     */
    public CompareRange<V> setEndValue(V endValue) {
        this.endValue = endValue;
        return this;
    }

    public boolean checkRange(Double targetV, Function<V, Double> value) {
    	Double startV = value.apply(startValue);
    	Double endV = value.apply(endValue);
    	if(targetV == null || startV == null || endV == null){
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
