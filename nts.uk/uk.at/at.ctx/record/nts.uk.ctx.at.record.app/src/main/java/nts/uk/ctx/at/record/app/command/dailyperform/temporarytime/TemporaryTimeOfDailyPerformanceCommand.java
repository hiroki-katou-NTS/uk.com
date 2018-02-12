package nts.uk.ctx.at.record.app.command.dailyperform.temporarytime;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.record.app.find.dailyperform.temporarytime.dto.TemporaryTimeOfDailyPerformanceDto;
import nts.uk.ctx.at.record.dom.worktime.TemporaryTimeOfDailyPerformance;
import nts.uk.ctx.at.shared.app.util.attendanceitem.DailyWorkCommonCommand;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ConvertibleAttendanceItem;

public class TemporaryTimeOfDailyPerformanceCommand extends DailyWorkCommonCommand {

	@Getter
	private Optional<TemporaryTimeOfDailyPerformance> data;

	@Override
	public void setRecords(ConvertibleAttendanceItem item) {
		this.data = item == null ? Optional.empty() : Optional.of(((TemporaryTimeOfDailyPerformanceDto) item).toDomain(getEmployeeId(), getWorkDate()));
	}

	@Override
	public void updateData(Object data) {
		this.data = data == null ? Optional.empty() : Optional.of((TemporaryTimeOfDailyPerformance) data);
	}
}
