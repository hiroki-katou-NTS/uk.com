package nts.uk.ctx.at.record.app.command.dailyperform;

import lombok.Data;
import nts.uk.ctx.at.record.app.find.dailyperform.DailyRecordDto;
import nts.uk.ctx.at.record.app.find.dailyperform.dto.AttendanceTimeDailyPerformDto;
import nts.uk.ctx.at.shared.app.util.attendanceitem.AttendanceItemCommand;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ConvertibleAttendanceItem;

@Data
public class DailyRecordWorkCommand implements AttendanceItemCommand {

	private AttendanceTimeOfDailyPerformCommand attendanceTimeCommand = new AttendanceTimeOfDailyPerformCommand();

	@Override
	public void setRecords(ConvertibleAttendanceItem item) {
		DailyRecordDto fullDto = (DailyRecordDto) item;
		attendanceTimeCommand.setRecords(fullDto.getAttendanceTime().orElse(new AttendanceTimeDailyPerformDto()));
	}

}
