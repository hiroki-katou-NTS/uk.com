package nts.uk.ctx.at.record.dom.reservation.bentomenu;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 弁当名
 * @author Doan Duy Hung
 *
 */
@StringMaxLength(16)
public class BentoName extends StringPrimitiveValue<BentoName>{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public BentoName(String rawValue) {
		super(rawValue);
	}

}
