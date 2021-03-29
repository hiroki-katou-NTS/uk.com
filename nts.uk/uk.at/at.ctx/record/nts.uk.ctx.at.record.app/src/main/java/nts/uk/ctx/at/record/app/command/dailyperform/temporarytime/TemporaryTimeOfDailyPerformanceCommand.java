package nts.uk.ctx.at.record.app.command.dailyperform.temporarytime;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.record.app.find.dailyperform.temporarytime.dto.TemporaryTimeOfDailyPerformanceDto;
import nts.uk.ctx.at.record.dom.worktime.TemporaryTimeOfDailyPerformance;
import nts.uk.ctx.at.shared.app.util.attendanceitem.DailyWorkCommonCommand;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ConvertibleAttendanceItem;

public class TemporaryTimeOfDailyPerformanceCommand extends DailyWorkCommonCommand {

	@Getter
	private Optional<TemporaryTimeOfDailyPerformance> data;

	@Override
	public void setRecords(ConvertibleAttendanceItem item) {
		
		if (item == null) {
			this.data = Optional.empty();
			return;
		}
		TemporaryTimeOfDailyPerformance temporaryTimeOfDailyPerformance = new TemporaryTimeOfDailyPerformance(
				getEmployeeId(), getWorkDate(),
				((TemporaryTimeOfDailyPerformanceDto) item).toDomain(getEmployeeId(), getWorkDate()));
		this.data = item == null || !item.isHaveData() ? Optional.empty() 
				: Optional.of(temporaryTimeOfDailyPerformance);
	}

	@Override
	public void updateData(Object data) {
		if(data == null){ return; }
		this.data = Optional.of((TemporaryTimeOfDailyPerformance) data);
	}
	
	@Override
	public Optional<TemporaryTimeOfDailyPerformance> toDomain() {
		return this.data;
	}

	@Override
	public Optional<TemporaryTimeOfDailyPerformanceDto> toDto() {
		return getData().map(b -> TemporaryTimeOfDailyPerformanceDto.getDto(b));
	}

	@Override
	@SuppressWarnings("unchecked")
	public void updateDataO(Optional<?> data) {
		this.data = (Optional<TemporaryTimeOfDailyPerformance>) data;
	}
}
