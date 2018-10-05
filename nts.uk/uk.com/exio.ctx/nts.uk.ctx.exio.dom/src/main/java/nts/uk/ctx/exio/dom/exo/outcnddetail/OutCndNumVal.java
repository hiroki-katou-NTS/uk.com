package nts.uk.ctx.exio.dom.exo.outcnddetail;

import java.math.BigDecimal;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.constraint.DecimalMaxValue;
import nts.arc.primitive.constraint.DecimalMinValue;

@DecimalMaxValue("9999999999.99")
@DecimalMinValue("-9999999999.99")
public class OutCndNumVal extends DecimalPrimitiveValue<PrimitiveValue<BigDecimal>>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public OutCndNumVal(BigDecimal rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

}
