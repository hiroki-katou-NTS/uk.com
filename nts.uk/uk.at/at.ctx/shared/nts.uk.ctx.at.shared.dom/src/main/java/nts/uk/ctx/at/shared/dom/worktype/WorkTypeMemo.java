package nts.uk.ctx.at.shared.dom.worktype;

import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
@StringMaxLength(40)
public class WorkTypeMemo extends StringPrimitiveValue<PrimitiveValue<String>> implements Serializable{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	public WorkTypeMemo(String rawValue) {
		super(rawValue);
	}

}
