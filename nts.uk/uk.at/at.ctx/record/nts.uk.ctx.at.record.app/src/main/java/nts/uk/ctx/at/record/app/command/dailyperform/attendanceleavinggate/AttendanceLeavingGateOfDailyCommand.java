package nts.uk.ctx.at.record.app.command.dailyperform.attendanceleavinggate;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.record.app.find.dailyperform.attendanceleavinggate.dto.AttendanceLeavingGateOfDailyDto;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.AttendanceLeavingGateOfDaily;
import nts.uk.ctx.at.shared.app.util.attendanceitem.DailyWorkCommonCommand;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ConvertibleAttendanceItem;

public class AttendanceLeavingGateOfDailyCommand extends DailyWorkCommonCommand {

	@Getter
	private Optional<AttendanceLeavingGateOfDaily> data;

	@Override
	public void setRecords(ConvertibleAttendanceItem item) {
		if (item == null) {
			this.data = Optional.empty();
			return;
		}
		AttendanceLeavingGateOfDaily attendanceLeavingGateOfDaily = new AttendanceLeavingGateOfDaily(getEmployeeId(), getWorkDate(), ((AttendanceLeavingGateOfDailyDto) item).toDomain(getEmployeeId(), getWorkDate()));
		this.data = item == null || !item.isHaveData() ? Optional.empty() 
				: Optional.of(attendanceLeavingGateOfDaily);
	}

	@Override
	public void updateData(Object item) {
		if(item == null){ return; }
		this.data = Optional.of((AttendanceLeavingGateOfDaily) item);
	}

	@Override
	public Optional<AttendanceLeavingGateOfDaily> toDomain() {
		return data;
	}

	@Override
	public Optional<AttendanceLeavingGateOfDailyDto> toDto() {
		return getData().map(b -> AttendanceLeavingGateOfDailyDto.getDto(b));
	}

	@Override
	@SuppressWarnings("unchecked")
	public void updateDataO(Optional<?> data) {
		this.data = (Optional<AttendanceLeavingGateOfDaily>) data;
	}
}
