package nts.uk.ctx.basic.dom.system.era;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
@StringMaxLength(1)
public class EraMark extends StringPrimitiveValue<EraMark> {
	
	public EraMark(String arg0) {
		// TODO Auto-generated constructor stub
		super(arg0);
	}

}
