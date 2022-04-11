package nts.uk.ctx.at.record.pubimp.employmentinfoterminal.nrweb.daily;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrweb.daily.RCNRWebGetDaily;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrweb.master.AllMasterAttItem;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrweb.master.GetMasterAttendanceItem;
import nts.uk.ctx.at.record.pub.employmentinfoterminal.nrweb.daily.AttendanceItemAndValueExport;
import nts.uk.ctx.at.record.pub.employmentinfoterminal.nrweb.daily.NRWebDailyDataExport;
import nts.uk.ctx.at.record.pub.employmentinfoterminal.nrweb.daily.RCNRWebGetDailyPub;
import nts.uk.ctx.at.shared.dom.dailyattdcal.converter.DailyRecordShareFinder;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.service.AttendanceItemConvertFactory;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.DailyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.DailyAttendanceItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.AtItemNameAdapter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.AttItemName;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.TypeOfItemImport;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.repository.DailyAttendanceItemRepository;

@Stateless
public class RCNRWebGetDailyPubImpl implements RCNRWebGetDailyPub {

	@Inject
	private DailyRecordShareFinder dailyRecordShareFinder;

	@Inject
	private AttendanceItemConvertFactory factory;

	@Inject
	private AtItemNameAdapter atItemNameAdapter;

	@Inject
	private GetMasterAttendanceItem getMasterAttendanceItem;

	@Inject
	private DailyAttendanceItemRepository dailyAttendanceItemRepository;

	@Override
	public List<NRWebDailyDataExport> getRecord(String cid, String employeeId, DatePeriod period,
			List<Integer> attendanceId) {

		RequireImpl impl = new RequireImpl(dailyRecordShareFinder, factory, atItemNameAdapter, getMasterAttendanceItem,
				dailyAttendanceItemRepository);

		return RCNRWebGetDaily.getRecord(impl, cid, employeeId, period, attendanceId).stream().map(x -> {
			return new NRWebDailyDataExport(
					x.getEmployeeId(), x.getDate(), x
							.getValue().stream().map(y -> new AttendanceItemAndValueExport(y.getNo(),
									y.getAttendanceItemId(), y.getName(), y.getValue(), y.getState().value))
							.collect(Collectors.toList()));
		}).collect(Collectors.toList());
	}

	@AllArgsConstructor
	public class RequireImpl implements RCNRWebGetDaily.Require {

		private DailyRecordShareFinder dailyRecordShareFinder;

		private AttendanceItemConvertFactory factory;

		private AtItemNameAdapter atItemNameAdapter;

		private GetMasterAttendanceItem getMasterAttendanceItem;

		private DailyAttendanceItemRepository dailyAttendanceItemRepository;

		@Override
		public List<IntegrationOfDaily> get(String employeeID, DatePeriod period) {
			return dailyRecordShareFinder.find(employeeID, period);
		}

		@Override
		public DailyRecordToAttendanceItemConverter createDailyConverter() {
			return factory.createDailyConverter();
		}

		@Override
		public List<AttItemName> getNameOfAttendanceItem(String cid, List<Integer> attendanceItemIds,
				TypeOfItemImport type) {
			return atItemNameAdapter.getNameOfAttendanceItem(cid, attendanceItemIds, type);
		}

		@Override
		public List<AllMasterAttItem> getAllMaster(String companyId, List<Integer> lstAtt, GeneralDate baseDate) {
			return getMasterAttendanceItem.getAllMaster(companyId, lstAtt, baseDate);
		}

		@Override
		public List<DailyAttendanceItem> getListById(String companyId, List<Integer> dailyAttendanceItemIds) {
			return dailyAttendanceItemRepository.findByADailyAttendanceItems(dailyAttendanceItemIds, companyId);
		}

	}

}
