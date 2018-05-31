package nts.uk.ctx.at.record.app.command.dailyperform.attendanceleavinggate;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.record.app.find.dailyperform.attendanceleavinggate.dto.AttendanceLeavingGateOfDailyDto;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.AttendanceLeavingGateOfDaily;
import nts.uk.ctx.at.shared.app.util.attendanceitem.DailyWorkCommonCommand;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ConvertibleAttendanceItem;

public class AttendanceLeavingGateOfDailyCommand extends DailyWorkCommonCommand {

	@Getter
	private Optional<AttendanceLeavingGateOfDailyDto> data;

	@Override
	public void setRecords(ConvertibleAttendanceItem item) {
		this.data = item == null || !item.isHaveData() ? Optional.empty() : Optional.of((AttendanceLeavingGateOfDailyDto) item);
	}

	@Override
	public void updateData(Object item) {
		if(data == null){ return; }
		setRecords(AttendanceLeavingGateOfDailyDto.getDto((AttendanceLeavingGateOfDaily) item));
	}

	@Override
	public Optional<AttendanceLeavingGateOfDaily> toDomain() {
		return data == null ? null : data.map(c -> c.toDomain(getEmployeeId(), getWorkDate()));
	}
}
