package nts.uk.ctx.at.shared.dom.common;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.constraint.IntegerMinValue;

/**
 * QRコード縦個数
 * @author tutt
 *
 */
@IntegerMinValue(1)
@IntegerMaxValue(8)
public class QRCodeVerticalNumber extends IntegerPrimitiveValue<QRCodeVerticalNumber>  {
	private static final long serialVersionUID = 1L;

	public QRCodeVerticalNumber(int rawValue) {
		super(rawValue);
	}
}
