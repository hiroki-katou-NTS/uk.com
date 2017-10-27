package nts.uk.ctx.at.request.dom.setting.company.divergencereason;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
/**
 * @author loivt
 * 定型理由
 */
@StringMaxLength(40)
public class ReasonTempPrimitive extends StringPrimitiveValue<ReasonTempPrimitive> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ReasonTempPrimitive(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

}
