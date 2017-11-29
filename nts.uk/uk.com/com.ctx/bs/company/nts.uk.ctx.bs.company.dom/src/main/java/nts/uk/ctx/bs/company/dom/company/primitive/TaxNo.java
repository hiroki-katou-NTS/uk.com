package nts.uk.ctx.bs.company.dom.company.primitive;

import java.math.BigDecimal;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.DecimalMaxValue;
@DecimalMaxValue(value = "9999999999999")
public class TaxNo extends DecimalPrimitiveValue<TaxNo>{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	/** 法人マイナンバー */
	public TaxNo(BigDecimal rawValue) {
		super(rawValue);
	}
}
