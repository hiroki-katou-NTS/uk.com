package nts.uk.ctx.sys.gateway.dom.securitypolicy;

import java.math.BigDecimal;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.DecimalMaxValue;
import nts.arc.primitive.constraint.DecimalMinValue;
@DecimalMaxValue("8")
@DecimalMinValue("0")
public class SymbolCharacters extends DecimalPrimitiveValue<SymbolCharacters> {

	public SymbolCharacters(BigDecimal rawValue) {
		super(rawValue);
	}
	private static final long serialVersionUID = 1L;
	
}
