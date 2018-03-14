package nts.uk.ctx.at.record.dom.divergence.time.setting;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(60)
public class DivergenceReason extends StringPrimitiveValue<DivergenceReason>{
	
	private static final long serialVersionUID = 1L;

	public DivergenceReason(String rawValue) {
		super(rawValue);
	}

}
