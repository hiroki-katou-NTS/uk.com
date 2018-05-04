package nts.uk.ctx.at.function.dom.alarm.extractionrange.month;

import lombok.Getter;
import lombok.Setter;
/**
 * 固定月度
 * @author phongtq
 *
 */
@Getter
@Setter
public class FixedMonthly {

	/** 年の種類 */
	private YearSpecifiedType yearSpecifiedType;
	
	/** 指定月 */
	private int DesignatedMonth;
	
}
