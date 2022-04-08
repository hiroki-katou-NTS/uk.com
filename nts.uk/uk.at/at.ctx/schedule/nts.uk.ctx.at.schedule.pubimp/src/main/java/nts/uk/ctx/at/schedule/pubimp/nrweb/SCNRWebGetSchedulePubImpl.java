package nts.uk.ctx.at.schedule.pubimp.nrweb;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.adapter.dailymonthlyprocessing.master.AllMasterAttItemImport;
import nts.uk.ctx.at.schedule.dom.adapter.dailymonthlyprocessing.master.GetMasterAttendanceItemAdapter;
import nts.uk.ctx.at.schedule.dom.employmentinfoterminal.nrweb.SCNRWebGetSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkScheduleRepository;
import nts.uk.ctx.at.schedule.pub.nrweb.AttendanceItemAndValueExport;
import nts.uk.ctx.at.schedule.pub.nrweb.NRWebScheduleDataExport;
import nts.uk.ctx.at.schedule.pub.nrweb.SCNRWebGetSchedulePub;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.service.AttendanceItemConvertFactory;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.DailyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.DailyAttendanceItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.AtItemNameAdapter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.AttItemName;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.TypeOfItemImport;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.repository.DailyAttendanceItemRepository;

@Stateless
public class SCNRWebGetSchedulePubImpl implements SCNRWebGetSchedulePub {

	@Inject
	private WorkScheduleRepository workScheduleRepository;

	@Inject
	private AttendanceItemConvertFactory factory;

	@Inject
	private AtItemNameAdapter atItemNameAdapter;

	@Inject
	private GetMasterAttendanceItemAdapter getMasterAttendanceItemAdapter;

	@Inject
	private DailyAttendanceItemRepository dailyAttendanceItemRepository;

	@Override
	public List<NRWebScheduleDataExport> getSchedule(String cid, String employeeId, DatePeriod period,
			List<Integer> attendanceId) {
		RequireImpl impl = new RequireImpl(workScheduleRepository, factory, atItemNameAdapter,
				getMasterAttendanceItemAdapter, dailyAttendanceItemRepository);
		
		return SCNRWebGetSchedule.getSchedule(impl, cid, employeeId, period, attendanceId).stream().map(x -> {
			return new NRWebScheduleDataExport(
					x.getEmployeeId(), x.getDate(), x
							.getValue().stream().map(y -> new AttendanceItemAndValueExport(y.getNo(),
									y.getAttendanceItemId(), y.getName(), y.getValue(), y.getState().value))
							.collect(Collectors.toList()));
		}).collect(Collectors.toList());
	}

	@AllArgsConstructor
	public class RequireImpl implements SCNRWebGetSchedule.Require {

		private WorkScheduleRepository workScheduleRepository;

		private AttendanceItemConvertFactory factory;

		private AtItemNameAdapter atItemNameAdapter;

		private GetMasterAttendanceItemAdapter getMasterAttendanceItemAdapter;

		private DailyAttendanceItemRepository dailyAttendanceItemRepository;

		@Override
		public List<WorkSchedule> get(String employeeID, DatePeriod period) {
			return workScheduleRepository.getList(Arrays.asList(employeeID), period);
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
		public List<AllMasterAttItemImport> getAllMaster(String companyId, List<Integer> lstAtt, GeneralDate baseDate) {
			return getMasterAttendanceItemAdapter.getAllMaster(companyId, lstAtt, baseDate);
		}

		@Override
		public List<DailyAttendanceItem> getListById(String companyId, List<Integer> dailyAttendanceItemIds) {
			return dailyAttendanceItemRepository.findByADailyAttendanceItems(dailyAttendanceItemIds, companyId);
		}

	}
}
