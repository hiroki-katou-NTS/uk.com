package nts.uk.ctx.at.record.app.command.dailyperform.workrecord;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.record.app.find.dailyperform.workrecord.dto.TimeLeavingOfDailyPerformanceDto;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.shared.app.util.attendanceitem.DailyWorkCommonCommand;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemCommon;

public class TimeLeavingOfDailyPerformanceCommand extends DailyWorkCommonCommand {

	@Getter
	private Optional<TimeLeavingOfDailyPerformanceDto> data;

	@Override
	public void setRecords(AttendanceItemCommon item) {
		this.data = item == null || !item.isHaveData() ? Optional.empty() : Optional.of((TimeLeavingOfDailyPerformanceDto) item);
	}

	@Override
	public void updateData(Object item) {
		if(data == null){ return; }
		setRecords(TimeLeavingOfDailyPerformanceDto.getDto((TimeLeavingOfDailyPerformance) item));
	}

	@Override
	public Optional<TimeLeavingOfDailyPerformance> toDomain() {
		return data == null ? null : data.map(c -> c.toDomain(getEmployeeId(), getWorkDate()));
	}
}
