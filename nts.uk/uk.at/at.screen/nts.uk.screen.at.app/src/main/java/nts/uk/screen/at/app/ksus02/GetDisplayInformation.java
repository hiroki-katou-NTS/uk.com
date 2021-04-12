package nts.uk.screen.at.app.ksus02;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.app.find.dailyperform.dto.TimeSpanForCalcDto;
import nts.uk.ctx.at.schedule.app.query.schedule.shift.management.shifttable.GetHolidaysByPeriod;
import nts.uk.ctx.at.schedule.app.query.workrequest.GetWorkRequestByEmpsAndPeriod;
import nts.uk.ctx.at.schedule.app.query.workrequest.WorkAvailabilityDisplayInfoOfOneDayDto;
import nts.uk.screen.at.app.kdl045.query.WorkAvailabilityDisplayInfoDto;
import nts.uk.screen.at.app.kdl045.query.WorkAvailabilityOfOneDayDto;

/**
 * ScreenQuery : 表示情報を取得する
 * 
 * @author tutk
 *
 */
@Stateless
public class GetDisplayInformation {

	@Inject
	private GetWorkRequestByEmpsAndPeriod getWorkRequestByEmpsAndPeriod;

	@Inject
	private GetHolidaysByPeriod getHolidaysByPeriod;

	public GetDisplayInforOuput get(List<String> listEmp, DatePeriod datePeriod) {
		List<WorkAvailabilityDisplayInfoOfOneDayDto> listWorkAvai = getWorkRequestByEmpsAndPeriod.get(listEmp,
				datePeriod);
		List<String> listDateHoliday = getHolidaysByPeriod.get(datePeriod).stream().map(c -> c.getDate().toString())
				.collect(Collectors.toList());
		return new GetDisplayInforOuput(listWorkAvai.stream()
				.map(c -> convertToWorkAvailabilityDisplayInfoOfOneDayDto(c)).collect(Collectors.toList()),
				listDateHoliday);
	}

	private WorkAvailabilityOfOneDayDto convertToWorkAvailabilityDisplayInfoOfOneDayDto(
			WorkAvailabilityDisplayInfoOfOneDayDto dto) {
		return new WorkAvailabilityOfOneDayDto(dto.getEmployeeId(), dto.getAvailabilityDate(), dto.getMemo(),
				new WorkAvailabilityDisplayInfoDto(dto.getDisplayInfo().getAssignmentMethod(),
						dto.getDisplayInfo().getNameList(),
						dto.getDisplayInfo().getTimeZoneList().stream()
								.map(c -> new TimeSpanForCalcDto(c.getStartTime(), c.getEndTime()))
								.collect(Collectors.toList())));
	}
}
