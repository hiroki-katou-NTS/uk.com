package nts.uk.ctx.pr.proto.dom.layoutmasterdetail;

import java.math.BigDecimal;

import nts.arc.primitive.DecimalPrimitiveValue;

public class ColumnPosition extends DecimalPrimitiveValue<ColumnPosition>{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Constructor
	 * @param rawValue
	 */
	public ColumnPosition(BigDecimal rawValue) {
		super(rawValue);	
	}

}
