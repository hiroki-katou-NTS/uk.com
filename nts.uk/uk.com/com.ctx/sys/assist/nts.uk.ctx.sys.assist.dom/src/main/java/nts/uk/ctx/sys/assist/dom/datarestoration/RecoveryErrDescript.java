package nts.uk.ctx.sys.assist.dom.datarestoration;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(200)
public class RecoveryErrDescript extends StringPrimitiveValue<RecoveryErrDescript> {

	private static final long serialVersionUID = 1L;

	public RecoveryErrDescript(String rawValue) {
		super(rawValue);
	}
}
