package nts.uk.ctx.exio.dom.exo.dataformat.init;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;


/**
 * @author TamNX
 * データ形式文字桁
 */
@StringMaxLength(30)
public class DataFormatNullReplacement extends StringPrimitiveValue<DataFormatNullReplacement> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Contructor
	 * 
	 * @param rawValue
	 */
	public DataFormatNullReplacement(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}
}
