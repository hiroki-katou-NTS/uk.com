package nts.uk.ctx.at.request.dom.overtimeinstruct.primitivevalue;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * @author loivt
 * 残業時間
 */
@IntegerRange(min = 0, max = 2880)
public class OvertimeHour extends IntegerPrimitiveValue<OvertimeHour> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OvertimeHour(Integer rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

}
