package nts.uk.ctx.at.record.dom.workrecord.errorsetting.algorithm;

import javax.ejb.Stateless;

import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.CompareRange;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.CompareSingleValue;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.SingleValueCompareType;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * 
 * 
 */
@Stateless
public class CompareValueRangeChecking<V> {
	
	/**
	 * 値を範囲と比較する
	 * @param compareRange CompareRange
	 * @return false if had error
	 */
	public boolean checkCompareRange(CompareRange<V> compareRange, Double targetValue) {
		if (compareRange == null) {
			return false;
		}
		
		boolean valid = compareRange.checkRange(targetValue, c -> getVValue(c));
		
		return valid;
	}
	
	/**
	 * 値を単一値と比較する
	 * @return false if had error
	 */
	public boolean checkCompareSingleRange(CompareSingleValue<V> compareSingleValue, Double targetValue) {
		if (compareSingleValue == null) {
			return false;
		}
		
		boolean valid =  compareSingleValue.checkWithFixedValue(targetValue, c -> getVValue(c));
		
		return valid;
	}
	
	/**
	 * TODO 値を集計した値と比較する
	 * @return
	 */
	public boolean checkCompareAggRange() {
		
		// 該当する勤怠項目を加減算する
		return true;
	}
	
	private Double getVValue(V target) {
		return Double.valueOf(((AttendanceTime)target).v());
	}
}
