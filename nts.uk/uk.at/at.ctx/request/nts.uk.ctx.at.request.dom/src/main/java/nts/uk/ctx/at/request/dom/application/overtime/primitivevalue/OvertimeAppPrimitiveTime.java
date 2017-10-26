package nts.uk.ctx.at.request.dom.application.overtime.primitivevalue;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;
/**
 * @author loivt
 * 残業申請時間
 */
@IntegerRange(min = -1, max = 2880)
public class OvertimeAppPrimitiveTime extends IntegerPrimitiveValue<OvertimeAppPrimitiveTime> {

	
	private static final long serialVersionUID = 1L;

	public OvertimeAppPrimitiveTime(Integer rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

}
