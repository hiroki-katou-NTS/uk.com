package nts.uk.ctx.at.record.pub.actualsituation.confirmstatusmonthly;

import java.util.List;
import java.util.Optional;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.confirmstatusmonthly.MonthlyPerformaceLockStatus;

public interface ConfirmStatusMonthlyPub {
	public Optional<StatusConfirmMonthEx> getConfirmStatusMonthly(String companyId, List<String> listEmployeeId,
			YearMonth yearmonthInput, Integer clsId, Optional<MonthlyPerformaceLockStatus> lockData);
}
