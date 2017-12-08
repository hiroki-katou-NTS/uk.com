package nts.uk.ctx.at.record.dom.stamp;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
/**
 * 打刻カード
 * @author dudt
 *
 */
@StringMaxLength(20)
public class CardNumber extends StringPrimitiveValue<CardNumber> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public CardNumber(String rawValue) {
		super(rawValue);
	}
}
