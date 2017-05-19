package nts.uk.ctx.pr.report.dom.payment.comparing.confirm;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

@IntegerRange(max = 999912, min = 190001)
public class ProcessingYMLater extends IntegerPrimitiveValue<ProcessingYMLater> {
	private static final long serialVersionUID = 1L;

	public ProcessingYMLater(Integer rawValue) {
		super(rawValue);
	}
}
