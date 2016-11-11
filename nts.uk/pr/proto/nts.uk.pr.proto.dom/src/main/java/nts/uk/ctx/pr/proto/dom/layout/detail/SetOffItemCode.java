package nts.uk.ctx.pr.proto.dom.layout.detail;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLengh;

@StringMaxLengh(4)
public class SetOffItemCode extends StringPrimitiveValue<SetOffItemCode>{

	public SetOffItemCode(String rawValue) {
		super(rawValue);
	}

	private static final long serialVersionUID = 1L;

}
