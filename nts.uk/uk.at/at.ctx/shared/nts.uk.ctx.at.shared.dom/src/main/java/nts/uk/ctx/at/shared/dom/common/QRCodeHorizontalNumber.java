package nts.uk.ctx.at.shared.dom.common;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * QRコード横個数
 * @author tutt 
 * 
 */
@IntegerRange(min = 1, max = 5)
public class QRCodeHorizontalNumber extends IntegerPrimitiveValue<QRCodeHorizontalNumber> {
	private static final long serialVersionUID = 1L;

	public QRCodeHorizontalNumber(int rawValue) {
		super(rawValue);
	}
}
