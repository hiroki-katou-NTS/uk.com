package nts.uk.ctx.at.record.pub.employmentinfoterminal.nrweb.month;

import java.util.List;
import java.util.Optional;

import nts.arc.time.YearMonth;

public interface NRWebGetMonthPub {

	public Optional<NRWebMonthDataExport> getDataMonthData(String cid, String employeeId, List<Integer> attendanceId,
			YearMonth ym);

}
