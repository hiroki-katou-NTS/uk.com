package nts.uk.ctx.at.record.app.command.dailyperform.breaktime;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.record.app.find.dailyperform.resttime.dto.BreakTimeDailyDto;
import nts.uk.ctx.at.record.dom.breakorgoout.BreakTimeOfDailyPerformance;
import nts.uk.ctx.at.shared.app.util.attendanceitem.DailyWorkCommonCommand;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ConvertibleAttendanceItem;

public class BreakTimeOfDailyPerformanceCommand extends DailyWorkCommonCommand {

	@Getter
	private Optional<BreakTimeOfDailyPerformance> data = Optional.empty();

	@Override
	public void setRecords(ConvertibleAttendanceItem item) {
		if(item != null && item.isHaveData()){
			updateData(((BreakTimeDailyDto) item).toDomain(getEmployeeId(), getWorkDate()));
		}
	}
	
	@Override
	public void updateData(Object data) {
		if(data != null) {
			return;
//			BreakTimeOfDailyAttd d = (BreakTimeOfDailyAttd) data;
//			this.data.removeIf(br -> br.getTimeZone().getBreakType() == d.getBreakType());
//			this.data.add(new BreakTimeOfDailyPerformance(getEmployeeId(), getWorkDate(), d));
//			this.data.sort((e1, e2) -> e1.getTimeZone().getBreakType().value - e2.getTimeZone().getBreakType().value);
		}
		this.data = Optional.of((BreakTimeOfDailyPerformance) data);
	}

	@Override
	public Optional<BreakTimeOfDailyPerformance> toDomain() {
		return data;
	}

	@Override
	public Optional<BreakTimeDailyDto> toDto() {
		return getData().map(b -> BreakTimeDailyDto.getDto(b));
	}

	@Override
	@SuppressWarnings("unchecked")
	public void updateDataO(Optional<?> data) {
		this.data = (Optional<BreakTimeOfDailyPerformance>) data;
	}
}
