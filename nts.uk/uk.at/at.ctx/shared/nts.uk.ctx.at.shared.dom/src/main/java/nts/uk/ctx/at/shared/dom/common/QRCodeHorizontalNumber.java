package nts.uk.ctx.at.shared.dom.common;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.constraint.IntegerMinValue;

/**
 * QRコード横個数
 * @author tutt 
 * 
 */
@IntegerMinValue(1)
@IntegerMaxValue(5)
public class QRCodeHorizontalNumber extends IntegerPrimitiveValue<QRCodeHorizontalNumber> {
	private static final long serialVersionUID = 1L;

	public QRCodeHorizontalNumber(int rawValue) {
		super(rawValue);
	}
}
