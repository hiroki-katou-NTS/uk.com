package nts.uk.ctx.at.record.app.command.dailyperform.goout;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.record.app.find.dailyperform.goout.dto.OutingTimeOfDailyPerformanceDto;
import nts.uk.ctx.at.record.dom.breakorgoout.OutingTimeOfDailyPerformance;
import nts.uk.ctx.at.shared.app.util.attendanceitem.DailyWorkCommonCommand;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ConvertibleAttendanceItem;

public class OutingTimeOfDailyPerformanceCommand extends DailyWorkCommonCommand {

	@Getter
	private Optional<OutingTimeOfDailyPerformanceDto> data;

	@Override
	public void setRecords(ConvertibleAttendanceItem item) {
		this.data = item == null || !item.isHaveData() ? Optional.empty()
				: Optional.of((OutingTimeOfDailyPerformanceDto) item);
	}

	@Override
	public void updateData(Object data) {
		if(data == null){ return; }
		setRecords(OutingTimeOfDailyPerformanceDto.getDto((OutingTimeOfDailyPerformance) data));
	}

	@Override
	public Optional<OutingTimeOfDailyPerformance> toDomain() {
		return data == null ? null : data.map(c -> c.toDomain(getEmployeeId(), getWorkDate()));
	}
}
