package nts.uk.ctx.at.function.ac.employmentinfoterminal.nrweb.month;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.month.AttendanceItemValueMonth;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.month.NRWebGetMonthAdapter;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.month.NRWebMonthData;
import nts.uk.ctx.at.record.pub.employmentinfoterminal.nrweb.month.NRWebGetMonthPub;

@Stateless
public class NRWebGetMonthAc implements NRWebGetMonthAdapter {

	@Inject
	private NRWebGetMonthPub nrWebGetMonth;

	@Override
	public Optional<NRWebMonthData> getDataMonthData(String cid, String employeeId, List<Integer> attendanceId,
			YearMonth ym) {
		return nrWebGetMonth.getDataMonthData(cid, employeeId, attendanceId, ym).map(v -> {
			return new NRWebMonthData(v.getYm(), v.getValue().stream().map(
					x -> new AttendanceItemValueMonth(x.getNo(), x.getAttendanceItemId(), x.getName(), x.getValue()))
					.collect(Collectors.toList()));
		});
	}

}
