package nts.uk.ctx.at.record.app.command.dailyperform.goout;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.record.app.find.dailyperform.goout.dto.OutingTimeOfDailyPerformanceDto;
import nts.uk.ctx.at.record.dom.breakorgoout.OutingTimeOfDailyPerformance;
import nts.uk.ctx.at.shared.app.util.attendanceitem.DailyWorkCommonCommand;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ConvertibleAttendanceItem;

public class OutingTimeOfDailyPerformanceCommand extends DailyWorkCommonCommand {

	@Getter
	private Optional<OutingTimeOfDailyPerformance> data;

	@Override
	public void setRecords(ConvertibleAttendanceItem item) {
		
		if (item == null) {
			this.data = Optional.empty();
			return;
		}
		OutingTimeOfDailyPerformance outingTimeOfDailyPerformance = new OutingTimeOfDailyPerformance(getEmployeeId(),
				getWorkDate(), ((OutingTimeOfDailyPerformanceDto) item).toDomain(getEmployeeId(), getWorkDate()));
		this.data = item == null || !item.isHaveData() ? Optional.empty()
				: Optional.of(outingTimeOfDailyPerformance);
	}

	@Override
	public void updateData(Object data) {
		if(data == null){ return; }
		this.data = Optional.of((OutingTimeOfDailyPerformance) data);
	}

	@Override
	public Optional<OutingTimeOfDailyPerformance> toDomain() {
		return data;
	}

	@Override
	public Optional<OutingTimeOfDailyPerformanceDto> toDto() {
		return getData().map(b -> OutingTimeOfDailyPerformanceDto.getDto(b));
	}

	@Override
	@SuppressWarnings("unchecked")
	public void updateDataO(Optional<?> data) {
		this.data = (Optional<OutingTimeOfDailyPerformance>) data;
	}
}
