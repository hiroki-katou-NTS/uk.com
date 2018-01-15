package nts.uk.ctx.at.shared.dom.worktime.commonsetting.primitive;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * 就業時間帯No
 * @author keisuke_hoshina
 *
 */
@IntegerRange(min = 1, max = 5)
public class WorkTimeNo extends IntegerPrimitiveValue<WorkTimeNo>{

	private static final long serialVersionUID = 1L;
	/**
	 * @param raw Value 
	 * @param rawValue the raw Value
	 */
	public WorkTimeNo(Integer rawValue) {
		super(rawValue);
	}

}
