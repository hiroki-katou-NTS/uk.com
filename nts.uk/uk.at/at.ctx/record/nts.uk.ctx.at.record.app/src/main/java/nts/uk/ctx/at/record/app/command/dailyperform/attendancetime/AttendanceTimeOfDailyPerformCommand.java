package nts.uk.ctx.at.record.app.command.dailyperform.attendancetime;

import lombok.Getter;
import nts.uk.ctx.at.record.app.find.dailyperform.dto.AttendanceTimeDailyPerformDto;
import nts.uk.ctx.at.shared.app.util.attendanceitem.DailyWorkCommonCommand;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ConvertibleAttendanceItem;

public class AttendanceTimeOfDailyPerformCommand extends DailyWorkCommonCommand {

	@Getter
	private AttendanceTimeDailyPerformDto data;

	@Override
	public void setRecords(ConvertibleAttendanceItem item) {
		this.data = (AttendanceTimeDailyPerformDto) item;
	}
}
