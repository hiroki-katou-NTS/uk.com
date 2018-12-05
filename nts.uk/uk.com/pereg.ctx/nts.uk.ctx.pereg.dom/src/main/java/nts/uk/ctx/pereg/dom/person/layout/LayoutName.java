package nts.uk.ctx.pereg.dom.person.layout;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(20)
public class LayoutName extends StringPrimitiveValue<LayoutName>{
	
	private static final long serialVersionUID = 1L;
	
	public LayoutName(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

}
