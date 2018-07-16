package nts.uk.ctx.at.shared.dom.specialholidaynew.grantinformation;

import java.math.BigDecimal;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.DecimalMantissaMaxLength;
import nts.arc.primitive.constraint.DecimalRange;

/**
 * 特別休暇付与日数
 * 
 * @author tanlv
 *
 */
@DecimalRange(min = "0", max = "99.5")
@DecimalMantissaMaxLength(1)
public class GrantedDays extends DecimalPrimitiveValue<GrantedDays> {
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	public GrantedDays(BigDecimal rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}
}
