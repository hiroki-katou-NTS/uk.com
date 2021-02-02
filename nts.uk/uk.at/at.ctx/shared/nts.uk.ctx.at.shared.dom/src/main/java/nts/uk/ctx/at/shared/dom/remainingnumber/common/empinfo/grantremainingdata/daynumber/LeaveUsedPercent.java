package nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber;

/** 
 * 休暇使用率
 */
import java.math.BigDecimal;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.DecimalMantissaMaxLength;
import nts.arc.primitive.constraint.DecimalRange;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveUsedPercent;

@DecimalRange(min = "0", max= "100")
@DecimalMantissaMaxLength(1)
public class LeaveUsedPercent extends DecimalPrimitiveValue<LeaveUsedPercent>{

	private static final long serialVersionUID = -1481106324693174972L;

	public LeaveUsedPercent(BigDecimal rawValue) {
		super(rawValue);
	}
	
}
