package nts.uk.ctx.at.request.ac.record.dailyattendanceitem;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.pub.dailyattendanceitem.AttendanceItemService;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.dailyattendanceitem.AttendanceResultImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.dailyattendanceitem.DailyAttendanceItemAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.dailyattendanceitem.AttendanceItemValueImport;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 
 * @author Anh.BD
 *
 */
@Stateless
public class DailyAttendanceItemImpl implements DailyAttendanceItemAdapter {

	@Inject
	private AttendanceItemService attendService;

	@Override
	public List<AttendanceResultImport> getValueOf(List<String> employeeId, DatePeriod workingDate,
			List<Integer> itemIds) {
		List<AttendanceResultImport> listAttendanceImport = attendService.getValueOf(employeeId, workingDate, itemIds)
				.stream()
				.map(x -> new AttendanceResultImport(x.getEmployeeId(), x.getWorkingDate(),
						x.getAttendanceItems().stream()
								.map(y -> new AttendanceItemValueImport(y.getValue(), y.getValueType(), y.getItemId()))
								.collect(Collectors.toList())))
				.collect(Collectors.toList());
		return listAttendanceImport;
	}

}
