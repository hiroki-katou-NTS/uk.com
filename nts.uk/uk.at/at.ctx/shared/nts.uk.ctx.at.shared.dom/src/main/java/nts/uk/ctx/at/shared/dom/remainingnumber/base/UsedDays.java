package nts.uk.ctx.at.shared.dom.remainingnumber.base;

import java.math.BigDecimal;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.DecimalMaxValue;
import nts.arc.primitive.constraint.DecimalMinValue;

@DecimalMinValue("0.0")
@DecimalMaxValue("1.0")
/**
 * 使用日数
 * @author HopNT
 *
 */
public class UsedDays extends DecimalPrimitiveValue<UsedDays>{

	private static final long serialVersionUID = 1L;

	public UsedDays(BigDecimal rawValue) {
		super(rawValue);
	}

}
