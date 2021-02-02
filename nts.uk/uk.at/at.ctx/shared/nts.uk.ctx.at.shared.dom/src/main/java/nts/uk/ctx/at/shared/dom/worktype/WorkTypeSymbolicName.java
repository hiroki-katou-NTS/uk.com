package nts.uk.ctx.at.shared.dom.worktype;

import java.io.Serializable;

import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
@StringMaxLength(2)
public class WorkTypeSymbolicName extends StringPrimitiveValue<PrimitiveValue<String>> implements Serializable{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	public WorkTypeSymbolicName(String rawValue) {
		super(rawValue);
	}
}
