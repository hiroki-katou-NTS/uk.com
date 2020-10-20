package nts.uk.ctx.at.record.app.command.dailyperform.attendancetime;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.record.app.find.dailyperform.dto.AttendanceTimeDailyPerformDto;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.shared.app.util.attendanceitem.DailyWorkCommonCommand;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ConvertibleAttendanceItem;

public class AttendanceTimeOfDailyPerformCommand extends DailyWorkCommonCommand {

	@Getter
	private Optional<AttendanceTimeOfDailyPerformance> data;

	@Override
	public void setRecords(ConvertibleAttendanceItem item) {
		if (item == null) {
			this.data = Optional.empty();
			return;
		}
		AttendanceTimeOfDailyPerformance attendanceTimeOfDailyPerformance = (item !=null ? new AttendanceTimeOfDailyPerformance(
				getEmployeeId(), getWorkDate(),
				((AttendanceTimeDailyPerformDto) item).toDomain(getEmployeeId(), getWorkDate())):null);
		this.data = item == null || !item.isHaveData() ? Optional.empty() 
				: Optional.ofNullable(attendanceTimeOfDailyPerformance);
	}

	@Override
	public void updateData(Object data) {
		if(data == null){ return; }
		this.data = Optional.of((AttendanceTimeOfDailyPerformance) data);
	}

	@Override
	public Optional<AttendanceTimeOfDailyPerformance> toDomain() {
		return data;
	}

	@Override
	public Optional<AttendanceTimeDailyPerformDto> toDto() {
		return getData().map(b -> AttendanceTimeDailyPerformDto.getDto(b));
	}

	@Override
	@SuppressWarnings("unchecked")
	public void updateDataO(Optional<?> data) {
		this.data = (Optional<AttendanceTimeOfDailyPerformance>) data;
	}
}
