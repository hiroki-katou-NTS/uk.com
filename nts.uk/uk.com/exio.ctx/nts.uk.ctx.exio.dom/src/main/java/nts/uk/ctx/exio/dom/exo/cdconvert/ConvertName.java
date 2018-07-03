package nts.uk.ctx.exio.dom.exo.cdconvert;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 
 * @author TamNX
 * データ形式固定値
 *
 */
@StringMaxLength(20)
public class ConvertName extends StringPrimitiveValue<ConvertName>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ConvertName(String rawValue) {
		super(rawValue);
	}
	
}
