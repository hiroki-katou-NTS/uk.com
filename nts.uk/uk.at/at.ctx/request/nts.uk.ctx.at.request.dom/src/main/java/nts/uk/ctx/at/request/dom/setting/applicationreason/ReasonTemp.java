package nts.uk.ctx.at.request.dom.setting.applicationreason;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
/**
 * 定型理由
 * @author yennth
 *
 */
@StringMaxLength(40)
public class ReasonTemp extends StringPrimitiveValue<ReasonTemp>{
	private static final long serialVersionUID = 1L;

	public ReasonTemp(String rawValue) {
		super(rawValue);
	}
}
