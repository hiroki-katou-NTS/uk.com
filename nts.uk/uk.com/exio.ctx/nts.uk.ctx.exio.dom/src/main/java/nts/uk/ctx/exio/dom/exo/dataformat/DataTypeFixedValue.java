package nts.uk.ctx.exio.dom.exo.dataformat;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 
 * @author TamNX
 * データ形式固定値
 *
 */
@StringMaxLength(30)
@StringCharType(CharType.ANY_HALF_WIDTH)
public class DataTypeFixedValue extends StringPrimitiveValue<DataTypeFixedValue>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DataTypeFixedValue(String rawValue) {
		super(rawValue);
	}
	
}
