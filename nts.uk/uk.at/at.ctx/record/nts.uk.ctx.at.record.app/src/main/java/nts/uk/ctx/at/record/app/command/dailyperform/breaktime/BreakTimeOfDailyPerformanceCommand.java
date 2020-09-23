package nts.uk.ctx.at.record.app.command.dailyperform.breaktime;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import nts.uk.ctx.at.record.app.find.dailyperform.resttime.dto.BreakTimeDailyDto;
import nts.uk.ctx.at.record.dom.breakorgoout.BreakTimeOfDailyPerformance;
import nts.uk.ctx.at.shared.app.util.attendanceitem.DailyWorkCommonCommand;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ConvertibleAttendanceItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeOfDailyAttd;

public class BreakTimeOfDailyPerformanceCommand extends DailyWorkCommonCommand {

	@Getter
	private List<BreakTimeOfDailyPerformance> data = new ArrayList<>();

	@Override
	public void setRecords(ConvertibleAttendanceItem item) {
		if(item != null && item.isHaveData()){
			updateData(((BreakTimeDailyDto) item).toDomain(getEmployeeId(), getWorkDate()));
		}
	}
	
	@Override
	public void updateData(Object data) {
		if(data != null){
			BreakTimeOfDailyAttd d = (BreakTimeOfDailyAttd) data;
			this.data.removeIf(br -> br.getTimeZone().getBreakType() == d.getBreakType());
			this.data.add(new BreakTimeOfDailyPerformance(getEmployeeId(), getWorkDate(), d));
			this.data.sort((e1, e2) -> e1.getTimeZone().getBreakType().value - e2.getTimeZone().getBreakType().value);
		}
	}

	@Override
	public List<BreakTimeOfDailyPerformance> toDomain() {
		return data;
	}

	@Override
	public List<BreakTimeDailyDto> toDto() {
		return getData().stream().map(b -> BreakTimeDailyDto.getDto(b)).collect(Collectors.toList());
	}
}
