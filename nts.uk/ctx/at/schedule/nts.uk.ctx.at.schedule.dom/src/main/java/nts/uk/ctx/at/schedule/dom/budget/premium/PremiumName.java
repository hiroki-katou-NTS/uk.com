package nts.uk.ctx.at.schedule.dom.budget.premium;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(20)
public class PremiumName extends StringPrimitiveValue<PremiumName>{

	public PremiumName(String rawValue) {
		super(rawValue);
	}

}
