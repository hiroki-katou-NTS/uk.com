package nts.uk.ctx.at.record.app.command.dailyperform.attendancetime;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.record.app.find.dailyperform.dto.AttendanceTimeDailyPerformDto;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.shared.app.util.attendanceitem.DailyWorkCommonCommand;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemCommon;

public class AttendanceTimeOfDailyPerformCommand extends DailyWorkCommonCommand {

	@Getter
	private Optional<AttendanceTimeDailyPerformDto> data;

	@Override
	public void setRecords(AttendanceItemCommon item) {
		this.data = item == null || !item.isHaveData() ? Optional.empty() : Optional.of((AttendanceTimeDailyPerformDto) item);
	}

	@Override
	public void updateData(Object data) {
		if(data == null){ return; }
		setRecords(AttendanceTimeDailyPerformDto.getDto((AttendanceTimeOfDailyPerformance) data));
	}

	@Override
	public Optional<AttendanceTimeOfDailyPerformance> toDomain() {
		return data == null ? null : data.map(c -> c.toDomain(getEmployeeId(), getWorkDate()));
	}
}
