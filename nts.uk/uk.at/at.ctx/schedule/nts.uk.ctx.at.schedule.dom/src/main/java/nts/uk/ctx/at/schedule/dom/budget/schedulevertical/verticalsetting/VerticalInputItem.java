package nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting;

import java.math.BigDecimal;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.DecimalMaxValue;

/**
 * TanLV
 *
 */
@DecimalMaxValue("99999")
public class VerticalInputItem extends DecimalPrimitiveValue<VerticalInputItem> {
	private static final long serialVersionUID = 1L;

	public VerticalInputItem(BigDecimal rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}
}
