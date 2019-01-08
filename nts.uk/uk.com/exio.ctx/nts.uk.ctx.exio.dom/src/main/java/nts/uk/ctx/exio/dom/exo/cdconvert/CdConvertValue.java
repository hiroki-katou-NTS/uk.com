package nts.uk.ctx.exio.dom.exo.cdconvert;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * @author PhucTC 
 * コード変換値
 *
 */

@StringMaxLength(10)
public class CdConvertValue extends StringPrimitiveValue<CdConvertValue> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * 
	 * @param rawValue
	 */
	public CdConvertValue(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

}
