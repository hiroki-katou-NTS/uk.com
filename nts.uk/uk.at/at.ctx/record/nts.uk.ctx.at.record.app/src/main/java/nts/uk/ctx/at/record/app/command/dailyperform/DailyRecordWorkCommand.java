package nts.uk.ctx.at.record.app.command.dailyperform;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.command.dailyperform.attendancetime.AttendanceTimeOfDailyPerformCommand;
import nts.uk.ctx.at.record.app.find.dailyperform.DailyRecordDto;
import nts.uk.ctx.at.record.app.find.dailyperform.dto.AttendanceTimeDailyPerformDto;
import nts.uk.ctx.at.shared.app.util.attendanceitem.DailyWorkCommonCommand;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ConvertibleAttendanceItem;

public class DailyRecordWorkCommand extends DailyWorkCommonCommand {

	@Getter
	private AttendanceTimeOfDailyPerformCommand attendanceTimeCommand = new AttendanceTimeOfDailyPerformCommand();

	@Override
	public void setRecords(ConvertibleAttendanceItem item) {
		DailyRecordDto fullDto = (DailyRecordDto) item;
		attendanceTimeCommand.setRecords(fullDto.getAttendanceTime().orElse(new AttendanceTimeDailyPerformDto()));
	}

	@Override
	public void forEmployee(String employeId) {
		super.forEmployee(employeId);
		this.attendanceTimeCommand.forEmployee(employeId);
	}

	@Override
	public void withDate(GeneralDate date) {
		super.withDate(date);
		this.attendanceTimeCommand.withDate(date);
	}

}
