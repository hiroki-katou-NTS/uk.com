package nts.uk.ctx.at.record.pubimp.employmentinfoterminal.nrweb.month;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrweb.master.GetMasterMonthProcess;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrweb.master.GetMasterMonthProcess.AllMasterAttItemMonth;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrweb.month.RCNRWebGetMonth;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrweb.month.algorithm.MonthlyRecordValues;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrweb.month.algorithm.RCGetMonthlyRecord;
import nts.uk.ctx.at.record.pub.employmentinfoterminal.nrweb.month.AttItemValueMonthExport;
import nts.uk.ctx.at.record.pub.employmentinfoterminal.nrweb.month.NRWebGetMonthPub;
import nts.uk.ctx.at.record.pub.employmentinfoterminal.nrweb.month.NRWebMonthDataExport;
import nts.uk.ctx.at.shared.dom.monthlyattditem.MonthlyAttendanceItem;
import nts.uk.ctx.at.shared.dom.monthlyattditem.MonthlyAttendanceItemRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.AtItemNameAdapter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.AttItemName;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.TypeOfItemImport;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.enums.PrimitiveValueOfAttendanceItem;

@Stateless
public class NRWebGetMonthPubImpl implements NRWebGetMonthPub {

	@Inject
	private RCGetMonthlyRecord rcGetMonthlyRecord;

	@Inject
	private AtItemNameAdapter atItemNameAdapter;

	@Inject
	private MonthlyAttendanceItemRepository monthlyAttendanceItemRepository;

	@Inject
	private GetMasterMonthProcess getMasterMonthProcess;

	@Override
	public Optional<NRWebMonthDataExport> getDataMonthData(String cid, String employeeId, List<Integer> attendanceId,
			YearMonth ym) {
		RequireImpl impl = new RequireImpl(rcGetMonthlyRecord, atItemNameAdapter, monthlyAttendanceItemRepository,
				getMasterMonthProcess);
		return RCNRWebGetMonth.getDataMonthData(impl, cid, employeeId, attendanceId, ym).map(v -> {
			return new NRWebMonthDataExport(v.getYm(), v.getValue().stream().map(
					x -> new AttItemValueMonthExport(x.getNo(), x.getAttendanceItemId(), x.getName(), x.getValue()))
					.collect(Collectors.toList()));
		});
	}

	@AllArgsConstructor
	public class RequireImpl implements RCNRWebGetMonth.Require {

		private RCGetMonthlyRecord rcGetMonthlyRecord;

		private AtItemNameAdapter atItemNameAdapter;

		private MonthlyAttendanceItemRepository monthlyAttendanceItemRepository;

		private GetMasterMonthProcess getMasterMonthProcess;

		@Override
		public Map<String, List<MonthlyRecordValues>> getRecordValues(List<String> employeeIds, YearMonthPeriod period,
				List<Integer> itemIds) {
			return rcGetMonthlyRecord.getRecordValues(employeeIds, period, itemIds);
		}

		@Override
		public List<AttItemName> getNameOfAttendanceItem(String cid, List<Integer> attendanceItemIds,
				TypeOfItemImport type) {
			return atItemNameAdapter.getNameOfAttendanceItem(cid, attendanceItemIds, type);
		}

		@Override
		public List<MonthlyAttendanceItem> findByAttendanceItemId(String companyId, List<Integer> attendanceItemIds) {
			return monthlyAttendanceItemRepository.findByAttendanceItemId(companyId, attendanceItemIds);
		}

		@Override
		public List<AllMasterAttItemMonth> getMasterCodeName(String companyId, GeneralDate baseDate,
				List<PrimitiveValueOfAttendanceItem> itemTypes) {
			return getMasterMonthProcess.getMasterCodeName(companyId, baseDate, itemTypes);
		}

	}
}
