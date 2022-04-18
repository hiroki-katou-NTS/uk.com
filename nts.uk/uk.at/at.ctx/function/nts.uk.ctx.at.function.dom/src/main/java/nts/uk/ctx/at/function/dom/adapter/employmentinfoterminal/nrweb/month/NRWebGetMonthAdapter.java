package nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.month;

import java.util.List;
import java.util.Optional;

import nts.arc.time.YearMonth;

public interface NRWebGetMonthAdapter {

	public Optional<NRWebMonthData> getDataMonthData(String cid, String employeeId, List<Integer> attendanceId,
			YearMonth ym);

}
