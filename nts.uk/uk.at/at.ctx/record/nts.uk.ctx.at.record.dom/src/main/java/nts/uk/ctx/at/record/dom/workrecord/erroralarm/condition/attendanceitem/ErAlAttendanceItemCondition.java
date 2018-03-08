/**
 * 4:47:15 PM Nov 8, 2017
 */
package nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.ConditionAtr;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.ConditionType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.CheckedAmountValue;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.CheckedTimeDuration;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.CheckedTimesValue;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ErrorAlarmWorkRecordCode;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * @author hungnm
 * @param <V>
 * @param <V>
 *
 */
// 勤怠項目のエラーアラーム条件
public class ErAlAttendanceItemCondition<V> extends AggregateRoot {

	// 会社ID
	@Getter
	private String companyId;

	// エラーアラームコード
	@Getter
	private ErrorAlarmWorkRecordCode errorAlarmCode;

	// NO
	@Getter
	private int targetNO;

	// 条件式の属性
	@Getter
	private ConditionAtr conditionAtr;

	// 使用する
	@Getter
	private Boolean useAtr;

	// チェック対象（可算）
	@Getter
	private CountableTarget countableTarget;

	// チェック対象（不可算）
	@Getter
	private UncountableTarget uncountableTarget;

	// 単一値との比較
	@Getter
	private CompareSingleValue<V> compareSingleValue;

	// 範囲との比較
	@Getter
	private CompareRange<V> compareRange;

	public ErAlAttendanceItemCondition(String companyId, String errorAlarmCode, int targetNO, int conditionAtr,
			Boolean useAtr) {
		super();
		this.companyId = companyId;
		this.errorAlarmCode = new ErrorAlarmWorkRecordCode(errorAlarmCode);
		this.targetNO = targetNO;
		this.conditionAtr = EnumAdaptor.valueOf(conditionAtr, ConditionAtr.class);
		this.useAtr = useAtr;
	}

	/**
	 * Set CountableTarget
	 * 
	 * @param additionAttendanceItems
	 * @param substractionAttendanceItems
	 * @return itself
	 */
	public ErAlAttendanceItemCondition<V> setCountableTarget(List<Integer> additionAttendanceItems,
			List<Integer> substractionAttendanceItems) {
		this.countableTarget = new CountableTarget(
				new AddSubAttendanceItems(additionAttendanceItems, substractionAttendanceItems));
		return this;
	}

	/**
	 * Set UnCountableTarget
	 * 
	 * @param attendanceItem
	 * @return itself
	 */
	public ErAlAttendanceItemCondition<V> setUncountableTarget(int attendanceItem) {
		this.uncountableTarget = new UncountableTarget(attendanceItem);
		return this;
	}

	/**
	 * 
	 * @param compareOpertor
	 * @param conditionType
	 * @param conditionValue
	 * @return
	 */
	public ErAlAttendanceItemCondition<V> setCompareSingleValue(int compareOpertor, int conditionType, V value) {
		this.compareSingleValue = new CompareSingleValue<V>(compareOpertor, conditionType);
		this.compareSingleValue.setValue(value);
		return this;
	}

	/**
	 * 
	 * @param compareOperator
	 * @param conditionValue
	 * @return
	 */
	public ErAlAttendanceItemCondition<V> setCompareRange(int compareOperator, V startValue, V endValue) {
		this.compareRange = new CompareRange<V>(compareOperator);
		this.compareRange.setStartValue(startValue);
		this.compareRange.setEndValue(endValue);
		return this;
	}

	public boolean checkTarget(Function<List<Integer>, List<Integer>> getItemValue) {
		Integer target = 0;
		if (this.uncountableTarget != null) {
			target = getItemValue.apply(Arrays.asList(this.uncountableTarget.getAttendanceItem())).get(0);
		} else {
			int plus = getItemValue.apply(this.countableTarget.getAddSubAttendanceItems().getAdditionAttendanceItems())
					.stream().mapToInt(c -> c).sum();
			int minus = getItemValue
					.apply(this.countableTarget.getAddSubAttendanceItems().getSubstractionAttendanceItems()).stream()
					.mapToInt(c -> c).sum();
			target = plus - minus;
		}

		if (this.compareRange != null) {
			Integer startV = getRangeStartValue();
			Integer endV = getRangeEndValue();
			switch (this.compareRange.getCompareOperator()) {
			case BETWEEN_RANGE_CLOSED:
				return target.compareTo(startV) > 0 && target.compareTo(startV) < 0;
			case BETWEEN_RANGE_OPEN:
				return target.compareTo(startV) >= 0 && target.compareTo(endV) <= 0;
			case OUTSIDE_RANGE_CLOSED:
				return target.compareTo(startV) < 0 || target.compareTo(endV) > 0;
			case OUTSIDE_RANGE_OPEN:
				return target.compareTo(startV) <= 0 || target.compareTo(endV) >= 0;
			default:
				return false;
			}
		} else {
			Integer compareValue = 0;
			if (this.compareSingleValue.getConditionType() == ConditionType.FIXED_VALUE) {
				compareValue = getSingleFixValue();
			} else {
				compareValue = getItemValue.apply(Arrays.asList((int) this.compareSingleValue.getValue())).get(0);
			}
			switch (this.compareSingleValue.getCompareOpertor()) {
			case EQUAL:
				return target.compareTo(compareValue) == 0;
			case GREATER_OR_EQUAL:
				return target.compareTo(compareValue) >= 0;
			case GREATER_THAN:
				return target.compareTo(compareValue) > 0;
			case LESS_OR_EQUAL:
				return target.compareTo(compareValue) <= 0;
			case LESS_THAN:
				return target.compareTo(compareValue) < 0;
			case NOT_EQUAL:
				return target.compareTo(compareValue) != 0;
			default:
				return false;
			}
		}
	}

	private Integer getSingleFixValue() {
		switch (this.conditionAtr) {
		case AMOUNT_VALUE:
			return ((CheckedAmountValue) this.compareSingleValue.getValue()).v();
		case TIME_DURATION:
			return ((CheckedTimeDuration) this.compareSingleValue.getValue()).valueAsMinutes();
		case TIME_WITH_DAY:
			return ((TimeWithDayAttr) this.compareSingleValue.getValue()).valueAsMinutes();
		case TIMES:
			return ((CheckedTimesValue) this.compareSingleValue.getValue()).v();
		default:
			return 0;
		}
	}

	private Integer getRangeStartValue() {
		switch (this.conditionAtr) {
		case AMOUNT_VALUE:
			return ((CheckedAmountValue) this.compareRange.getStartValue()).v();
		case TIME_DURATION:
			return ((CheckedTimeDuration) this.compareRange.getStartValue()).valueAsMinutes();
		case TIME_WITH_DAY:
			return ((TimeWithDayAttr) this.compareRange.getStartValue()).valueAsMinutes();
		case TIMES:
			return ((CheckedTimesValue) this.compareRange.getStartValue()).v();
		default:
			return 0;
		}
	}

	private Integer getRangeEndValue() {
		switch (this.conditionAtr) {
		case AMOUNT_VALUE:
			return ((CheckedAmountValue) this.compareRange.getEndValue()).v();
		case TIME_DURATION:
			return ((CheckedTimeDuration) this.compareRange.getEndValue()).valueAsMinutes();
		case TIME_WITH_DAY:
			return ((TimeWithDayAttr) this.compareRange.getEndValue()).valueAsMinutes();
		case TIMES:
			return ((CheckedTimesValue) this.compareRange.getEndValue()).v();
		default:
			return 0;
		}
	}
}
