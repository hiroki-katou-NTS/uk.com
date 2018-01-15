package nts.uk.ctx.at.request.dom.overtimeinstruct.primitivevalue;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * @author loivt
 * 残業指示理由
 */
@StringMaxLength(200)
public class OvertimeInstructReason extends StringPrimitiveValue<OvertimeInstructReason>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OvertimeInstructReason(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

}
