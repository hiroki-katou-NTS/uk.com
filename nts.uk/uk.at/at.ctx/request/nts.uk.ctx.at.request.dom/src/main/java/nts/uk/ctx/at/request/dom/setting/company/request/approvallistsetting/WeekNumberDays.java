package nts.uk.ctx.at.request.dom.setting.company.request.approvallistsetting;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;
/**
 * 週間日数（警告前日数）
 * @author Doan Duy Hung
 *
 */
@IntegerRange(max = 7, min = 0)
public class WeekNumberDays extends IntegerPrimitiveValue<WeekNumberDays>{

	public WeekNumberDays(Integer rawValue) {
		super(rawValue);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -6636589118328505947L;

}
