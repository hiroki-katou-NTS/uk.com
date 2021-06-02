package nts.uk.ctx.exio.dom.exo.dataformat.init;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 
 * @author TamNX
 * データ形式固定値
 *
 */
@StringMaxLength(30)
public class DataTypeFixedValue extends StringPrimitiveValue<DataTypeFixedValue>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DataTypeFixedValue(String rawValue) {
		super(rawValue);
	}
	
}
