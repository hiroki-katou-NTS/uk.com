package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.grantremainingdata;

import java.math.BigDecimal;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.DecimalMantissaMaxLength;
import nts.arc.primitive.constraint.DecimalRange;

@DecimalRange(min = "0", max= "100")
@DecimalMantissaMaxLength(1)
public class AnnualLeaveUsedPercent extends DecimalPrimitiveValue<AnnualLeaveUsedPercent>{

	private static final long serialVersionUID = -1481106324693174972L;

	public AnnualLeaveUsedPercent(BigDecimal rawValue) {
		super(rawValue);
	}

}
