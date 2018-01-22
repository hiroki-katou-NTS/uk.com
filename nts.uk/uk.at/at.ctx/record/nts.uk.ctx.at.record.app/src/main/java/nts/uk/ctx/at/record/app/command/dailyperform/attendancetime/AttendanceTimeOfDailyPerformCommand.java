package nts.uk.ctx.at.record.app.command.dailyperform.attendancetime;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.record.app.find.dailyperform.dto.AttendanceTimeDailyPerformDto;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.shared.app.util.attendanceitem.DailyWorkCommonCommand;
import nts.uk.ctx.at.shared.app.util.attendanceitem.item.ConvertibleAttendanceItem;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

public class AttendanceTimeOfDailyPerformCommand extends DailyWorkCommonCommand {

	@Getter
	private Optional<AttendanceTimeDailyPerformDto> data;

	@Override
	public void setRecords(ConvertibleAttendanceItem item) {
		this.data = item == null ? Optional.empty() : Optional.of((AttendanceTimeDailyPerformDto) item);
	}

	@Override
	public AttendanceTimeOfDailyPerformance toDomain() {
		return !data.isPresent() ? null : new AttendanceTimeOfDailyPerformance(this.getEmployeeId(), this.getWorkDate(),
				data.get().getScheduleTime() == null ? null : data.get().getScheduleTime().toDomain(), 
				data.get().getActualWorkTime() == null ? null : data.get().getActualWorkTime().toDomain(),
				data.get().getStayingTime() == null ? null : data.get().getStayingTime().toDomain(), 
				data.get().getBudgetTimeVariance() == null ? null : new AttendanceTime(data.get().getBudgetTimeVariance()),
				data.get().getUnemployedTime() == null ? null : new AttendanceTime(data.get().getUnemployedTime()));
	}
}
