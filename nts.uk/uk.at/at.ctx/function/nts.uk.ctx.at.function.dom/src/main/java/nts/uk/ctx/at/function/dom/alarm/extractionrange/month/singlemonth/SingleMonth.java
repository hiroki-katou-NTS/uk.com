package nts.uk.ctx.at.function.dom.alarm.extractionrange.month.singlemonth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.ExtractionRangeBase;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.month.MonthNo;

/**
 * 単月
 *
 * @author phongtq
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SingleMonth extends ExtractionRangeBase {

	/** 月数指定 */
	private MonthNo monthNo;

	/**
	 * Checks constraint month no.<br>
	 * 月数＜＝6でなければならない
	 *
	 * @return {@code true} if 0 < the number of months <= 6, otherwise {@code false}
	 */
	public boolean checkConstraintMonthNo() {
		return this.monthNo != null && this.monthNo.getMonthNo() > 0 && this.monthNo.getMonthNo() <= 6;
	}

}
