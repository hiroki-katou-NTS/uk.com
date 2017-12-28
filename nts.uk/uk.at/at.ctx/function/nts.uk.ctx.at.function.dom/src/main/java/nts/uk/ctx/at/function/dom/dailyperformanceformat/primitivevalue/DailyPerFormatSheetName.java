package nts.uk.ctx.at.function.dom.dailyperformanceformat.primitivevalue;

import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(10)
public class DailyPerFormatSheetName extends StringPrimitiveValue<PrimitiveValue<String>>{

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	public DailyPerFormatSheetName(String rawValue) {
		super(rawValue);
	}
}
