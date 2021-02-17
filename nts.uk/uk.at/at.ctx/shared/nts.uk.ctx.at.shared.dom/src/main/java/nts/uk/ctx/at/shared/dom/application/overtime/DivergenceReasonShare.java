package nts.uk.ctx.at.shared.dom.application.overtime;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * The Class DivergenceReason.
 */
//乖離理由
@StringMaxLength(60)
public class DivergenceReasonShare extends StringPrimitiveValue<DivergenceReasonShare> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new divergence reason.
	 *
	 * @param rawValue
	 *            the raw value
	 */
	public DivergenceReasonShare(String rawValue) {
		super(rawValue);
	}

}
