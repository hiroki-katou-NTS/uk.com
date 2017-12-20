/**
 * 11:18:06 AM Nov 9, 2017
 */
package nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem;

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
	
	//値
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

	/**
	 * @return the value
	 */
	public V getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public CompareSingleValue<V> setValue(V value) {
		this.value = value;
		return this;
	}

}
