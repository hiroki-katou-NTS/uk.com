package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata;

import java.math.BigDecimal;

import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveUsedPercent;

//@DecimalRange(min = "0", max= "100")
//@DecimalMantissaMaxLength(1)
public class AnnualLeaveUsedPercent extends LeaveUsedPercent{

	private static final long serialVersionUID = -1481106324693174972L;

	public AnnualLeaveUsedPercent(BigDecimal rawValue) {
		super(rawValue);
	}

}
