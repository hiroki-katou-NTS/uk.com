package nts.uk.ctx.at.record.app.command.dailyperform.workrecord;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.app.find.dailyperform.workrecord.dto.TimeLeavingOfDailyPerformanceDto;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.shared.app.util.attendanceitem.DailyWorkCommonCommand;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ConvertibleAttendanceItem;

public class TimeLeavingOfDailyPerformanceCommand extends DailyWorkCommonCommand {

	@Getter
	private Optional<TimeLeavingOfDailyPerformance> data;

	@Getter
	@Setter
	private boolean isTriggerEvent = false;
	
	@Override
	public void setRecords(ConvertibleAttendanceItem item) {
		
		if (item == null) {
			this.data = Optional.empty();
			return;
		}
		TimeLeavingOfDailyPerformance timeLeavingOfDailyPerformance = new TimeLeavingOfDailyPerformance(getEmployeeId(),
				getWorkDate(), ((TimeLeavingOfDailyPerformanceDto) item).toDomain(getEmployeeId(), getWorkDate()));
		this.data = item == null || !item.isHaveData() ? Optional.empty() 
				: Optional.of(timeLeavingOfDailyPerformance);
	}

	@Override
	public void updateData(Object item) {
		if(item == null){ return; }
		this.data = Optional.of((TimeLeavingOfDailyPerformance) item);
	}

	@Override
	public Optional<TimeLeavingOfDailyPerformance> toDomain() {
		return data;
	}

	@Override
	public Optional<TimeLeavingOfDailyPerformanceDto> toDto() {
		return getData().map(b -> TimeLeavingOfDailyPerformanceDto.getDto(b));
	}

	@Override
	@SuppressWarnings("unchecked")
	public void updateDataO(Optional<?> data) {
		this.data = (Optional<TimeLeavingOfDailyPerformance>) data;
	}
}
