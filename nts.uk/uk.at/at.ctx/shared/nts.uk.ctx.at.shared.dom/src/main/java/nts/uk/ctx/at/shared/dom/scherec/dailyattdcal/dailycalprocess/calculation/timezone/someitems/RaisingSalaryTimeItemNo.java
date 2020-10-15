package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.someitems;

import java.math.BigDecimal;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.DecimalMaxValue;
import nts.arc.primitive.constraint.DecimalMinValue;

@DecimalMaxValue("10")
@DecimalMinValue("1")
public class RaisingSalaryTimeItemNo extends DecimalPrimitiveValue<RaisingSalaryTimeItemNo>{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	public RaisingSalaryTimeItemNo(BigDecimal rawValue) {
		super(rawValue);
	}
}
