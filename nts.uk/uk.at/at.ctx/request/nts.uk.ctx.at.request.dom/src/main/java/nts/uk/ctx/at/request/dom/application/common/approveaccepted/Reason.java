package nts.uk.ctx.at.request.dom.application.common.approveaccepted;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
/**
 * 
 * @author hieult
 *
 */
@StringMaxLength(400)
public class Reason extends  StringPrimitiveValue<Reason> {

	private static final long serialVersionUID = 1L;

	public Reason(String rawValue) {
		super(rawValue);
	}

}

