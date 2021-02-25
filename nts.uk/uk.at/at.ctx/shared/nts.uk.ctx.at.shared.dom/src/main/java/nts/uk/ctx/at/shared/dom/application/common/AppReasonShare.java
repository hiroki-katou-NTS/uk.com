package nts.uk.ctx.at.shared.dom.application.common;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 申請理由
 * 
 * @author thanh_nx
 *
 */
@StringMaxLength(400)
public class AppReasonShare extends StringPrimitiveValue<AppReasonShare> {

	private static final long serialVersionUID = 1L;

	public AppReasonShare(String rawValue) {
		super(rawValue);
	}

}
