package nts.uk.ctx.at.record.app.command.workrecord.daily;

import lombok.Data;
import nts.uk.ctx.at.record.app.find.workrecord.daily.dto.DailyWorkRecordDto;
import nts.uk.ctx.at.shared.app.util.attendanceitem.AttendanceItemCommand;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ConvertibleAttendanceItem;

@Data
public class DailyWorkRecordCommand implements AttendanceItemCommand {

	private DailyWorkRecordDto data;

	@Override
	public void setRecords(ConvertibleAttendanceItem item) {
		this.setData((DailyWorkRecordDto) item);
	}
}
