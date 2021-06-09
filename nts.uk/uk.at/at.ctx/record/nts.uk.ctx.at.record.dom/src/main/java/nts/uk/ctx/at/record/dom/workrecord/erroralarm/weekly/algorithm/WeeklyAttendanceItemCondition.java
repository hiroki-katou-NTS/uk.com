package nts.uk.ctx.at.record.dom.workrecord.erroralarm.weekly.algorithm;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import lombok.Data;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.AddSubAttendanceItems;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.CompareRange;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.CompareSingleValue;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.CountableTarget;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.UncountableTarget;

@Data
public class WeeklyAttendanceItemCondition<V> {
	// チェック対象（可算）
	private CountableTarget countableTarget;

	// チェック対象（不可算）
	private UncountableTarget uncountableTarget;
		
	// 単一値との比較
	private CompareSingleValue<V> compareSingleValue;

	// 範囲との比較
	private CompareRange<V> compareRange;
		
	public WeeklyAttendanceItemCondition() {
	}
	
	public boolean checkTarget(Function<List<Integer>, List<Double>> getItemValue) {
		
		Double targetValue = calculateTargetValue(getItemValue);

		if (this.compareRange != null) {
			return this.compareRange.checkRange(targetValue, c -> getVValue(c));
		} else {
			return this.compareSingleValue.check(targetValue, getItemValue, c -> getVValue(c));
		}
	}
	
	public Double calculateTargetValue(Function<List<Integer>, List<Double>> getItemValue) {
		if (this.uncountableTarget != null) {
			List<Integer> items = Arrays.asList(this.uncountableTarget.getAttendanceItem());
			if(items.isEmpty()){
				throw new RuntimeException("チェック対象（不可算）の項目が不正です。");
			}
			List<Double> values = getItemValue.apply(items);
			if(values.isEmpty()){
				throw new RuntimeException("チェック対象（不可算）の項目の値が不正です。");
			}
			return values.get(0);
		} else {
			return this.countableTarget.getAddSubAttendanceItems().calculate(getItemValue);
		}
	}
	
	private Double getVValue(V target) {		
		return (Double)target;
	}
	
	/**
	 * Set CountableTarget
	 *
	 * @param additionAttendanceItems
	 * @param substractionAttendanceItems
	 * @return itself
	 */
	public WeeklyAttendanceItemCondition<V> setCountableTarget(List<Integer> additionAttendanceItems,
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
	public WeeklyAttendanceItemCondition<V> setUncountableTarget(int attendanceItem) {
		this.uncountableTarget = new UncountableTarget(attendanceItem);
		return this;
	}
}
