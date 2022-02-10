package nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(20)
public class MonPfmCorrectionFormatName extends StringPrimitiveValue<MonPfmCorrectionFormatName> {

	private static final long serialVersionUID = 1L;

	public MonPfmCorrectionFormatName(String rawValue) {
		super(rawValue);
	}



}
