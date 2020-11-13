package nts.uk.ctx.sys.gateway.dom.securitypolicy.password;

import java.math.BigDecimal;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.DecimalMaxValue;
import nts.arc.primitive.constraint.DecimalMinValue;

@DecimalMaxValue("8")
@DecimalMinValue("1")
public class PasswordLowestDigits extends DecimalPrimitiveValue<PasswordLowestDigits> {

	public PasswordLowestDigits(BigDecimal rawValue) {
		super(rawValue);
	}

	private static final long serialVersionUID = 1L;

}
