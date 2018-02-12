package nts.uk.ctx.at.record.app.command.dailyperform.attendanceleavinggate;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.record.app.find.dailyperform.attendanceleavinggate.dto.AttendanceLeavingGateOfDailyDto;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.AttendanceLeavingGateOfDaily;
import nts.uk.ctx.at.shared.app.util.attendanceitem.DailyWorkCommonCommand;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ConvertibleAttendanceItem;

public class AttendanceLeavingGateOfDailyCommand extends DailyWorkCommonCommand {

	@Getter
	private Optional<AttendanceLeavingGateOfDaily> data;

	@Override
	public void setRecords(ConvertibleAttendanceItem item) {
		this.data = item == null ? Optional.empty() : Optional.of(((AttendanceLeavingGateOfDailyDto) item).toDomain(getEmployeeId(), getWorkDate()));
	}

	@Override
	public void updateData(Object item) {
		this.data = item == null ? Optional.empty() : Optional.of((AttendanceLeavingGateOfDaily) item);
	}
}
