/**
 * 11:18:06 AM Nov 9, 2017
 */
package nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.ConditionType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.SingleValueCompareType;

/**
 * @author hungnm
 *
 */
// 単一値との比較
public class CompareSingleValue<V> extends CheckedCondition {

	// 値
	private V value;

	// 比較演算子
	@Getter
	private SingleValueCompareType compareOpertor;

	// 条件値の種別
	@Getter
	private ConditionType conditionType;

	public CompareSingleValue(int compareOpertor, int conditionType) {
		super();
		this.compareOpertor = EnumAdaptor.valueOf(compareOpertor, SingleValueCompareType.class);
		this.conditionType = EnumAdaptor.valueOf(conditionType, ConditionType.class);
	}

	public CompareSingleValue(SingleValueCompareType compareOpertor, ConditionType conditionType) {
		super();
		this.compareOpertor = compareOpertor;
		this.conditionType = conditionType;
	}

	/**
	 * @return the value
	 */
	public V getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public CompareSingleValue<V> setValue(V value) {
		this.value = value;
		return this;
	}

	public boolean check(Double targetValue, Function<List<Integer>, List<Double>> getItemValue,
			Function<V, Double> getValue) {
		if (targetValue == null) {
			return false;
		}
		if (isFixedValue()) {
			return this.check(targetValue, getValue.apply(value));
		} else {
			return this.checkWithAttendanceItem(targetValue, getItemValue, c -> getValue.apply(value));
		}
	}

	public boolean isFixedValue() {
		return this.conditionType == ConditionType.FIXED_VALUE;
	}

	public boolean isAttendanceItem() {
		return this.conditionType == ConditionType.ATTENDANCE_ITEM;
	}

	public boolean checkWithFixedValue(Double target, Function<V, Double> getValue) {
		return check(target, getValue.apply(value));
	}

	public boolean checkWithAttendanceItem(Double target, Function<List<Integer>, List<Double>> getItemValue,
			Function<V, Double> getVValue) {
		Double compareValue = getItemValue.apply(Arrays.asList(getVValue.apply(this.value).intValue())).get(0);
		return check(target, compareValue);
	}

	private boolean check(Double target, Double compare) {
		if(target == null || compare == null) {
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
}
