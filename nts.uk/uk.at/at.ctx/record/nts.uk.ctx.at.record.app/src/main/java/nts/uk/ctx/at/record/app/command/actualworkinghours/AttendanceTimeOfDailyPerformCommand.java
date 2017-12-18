package nts.uk.ctx.at.record.app.command.actualworkinghours;

import lombok.Data;
import nts.uk.ctx.at.record.app.find.actualworkinghours.dto.AttendanceTimeDailyPerformDto;
import nts.uk.ctx.at.shared.app.util.attendanceitem.AttendanceItemCommand;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ConvertibleAttendanceItem;

@Data
public class AttendanceTimeOfDailyPerformCommand implements AttendanceItemCommand {

	private AttendanceTimeDailyPerformDto data;

	@Override
	public void setRecords(ConvertibleAttendanceItem item) {
		this.setData((AttendanceTimeDailyPerformDto) item);
	}
}
