package nts.uk.ctx.exio.dom.exo.datafomat;

import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;


/**
 * @author TamNX
 * データ形式文字桁
 */
@StringMaxLength(30)
public class DataFormatNullReplacement extends StringPrimitiveValue<PrimitiveValue<String>> {
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
