package nts.uk.ctx.at.function.dom.alarm.extractionrange.month;

import lombok.Getter;
/**
 * 固定月度
 * @author phongtq
 *
 */
@Getter
public class FixedMonthly {

	/** 年の種類 */
	private YearSpecifiedType yearSpecifiedType;
	
	/** 指定月 */
	private int designatedMonth;
	
	public FixedMonthly(YearSpecifiedType yearSpecifiedType, int designatedMonth) {
		this.yearSpecifiedType = yearSpecifiedType;
		this.designatedMonth = designatedMonth;		
	}
}
