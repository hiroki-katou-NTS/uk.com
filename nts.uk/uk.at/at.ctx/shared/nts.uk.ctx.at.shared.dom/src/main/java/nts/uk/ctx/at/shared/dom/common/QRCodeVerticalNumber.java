package nts.uk.ctx.at.shared.dom.common;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * QRコード縦個数
 * @author tutt
 *
 */
@IntegerRange(min = 1, max = 6)
public class QRCodeVerticalNumber extends IntegerPrimitiveValue<QRCodeVerticalNumber>  {
	private static final long serialVersionUID = 1L;

	public QRCodeVerticalNumber(int rawValue) {
		super(rawValue);
	}
}
