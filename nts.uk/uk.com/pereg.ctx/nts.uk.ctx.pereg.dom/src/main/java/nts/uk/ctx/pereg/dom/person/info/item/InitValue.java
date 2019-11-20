package nts.uk.ctx.pereg.dom.person.info.item;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
//初期値
@StringMaxLength(30)
public class InitValue extends StringPrimitiveValue<InitValue>{
	
	private static final long serialVersionUID = 1L;
	
	public InitValue(String rawValue) {
		super(rawValue);
	}


}
