package nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveGrantNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveNumberInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveRemainingDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveRemainingNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveUsedNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveUsedPercent;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveGrantDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveRemainingDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveUsedNumber;

@Getter
public class ReserveLeaveNumberInfo extends LeaveNumberInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	public ReserveLeaveNumberInfo(
			double grantDays,
			Integer grantMinutes,
			double usedDays,
			Integer usedMinutes,
			Double stowageDays,
			Double numberOverDays,
			Integer timeOver,
			double remainDays,
			Integer remainMinutes,
			double usedPercent) {

		super(grantDays, grantMinutes, usedDays, usedMinutes,
			stowageDays, numberOverDays, timeOver, remainDays, remainMinutes,usedPercent);
	}

	public ReserveLeaveNumberInfo(double grantDays, double usedDays,
			Double overLimitDays, double remainDays) {
		super(LeaveGrantNumber.createFromJavaType(grantDays, 0),
				LeaveUsedNumber.createFromJavaType(usedDays, 0, 0.0, overLimitDays),
				new LeaveRemainingNumber(remainDays, 0),
				new LeaveUsedPercent(new BigDecimal(0)));
	}


}