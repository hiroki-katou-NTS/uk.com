package nts.uk.ctx.exio.dom.exo.dataformat.init;

import java.math.BigDecimal;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.DecimalMantissaMaxLength;
import nts.arc.primitive.constraint.DecimalMaxValue;
import nts.arc.primitive.constraint.DecimalMinValue;

/**
 * 
 * @author TamNX
 * データ形式固定値演算
 *
 */

@DecimalMaxValue("9999999999.99")
@DecimalMinValue("0")
@DecimalMantissaMaxLength(2)
public class DataFormatFixedValueOperation extends DecimalPrimitiveValue<DataFormatFixedValueOperation>{

	public DataFormatFixedValueOperation(BigDecimal rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


}