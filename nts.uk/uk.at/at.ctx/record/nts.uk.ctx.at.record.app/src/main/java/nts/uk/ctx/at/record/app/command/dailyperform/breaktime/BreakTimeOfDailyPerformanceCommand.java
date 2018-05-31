package nts.uk.ctx.at.record.app.command.dailyperform.breaktime;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import nts.uk.ctx.at.record.app.find.dailyperform.resttime.dto.BreakTimeDailyDto;
import nts.uk.ctx.at.record.dom.breakorgoout.BreakTimeOfDailyPerformance;
import nts.uk.ctx.at.shared.app.util.attendanceitem.DailyWorkCommonCommand;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ConvertibleAttendanceItem;

public class BreakTimeOfDailyPerformanceCommand extends DailyWorkCommonCommand {

	@Getter
	private List<BreakTimeDailyDto> data = new ArrayList<>();

	@Override
	public void setRecords(ConvertibleAttendanceItem item) {
		if(item != null && item.isHaveData()){
			BreakTimeDailyDto d = (BreakTimeDailyDto) item;
			this.data.removeIf(br -> br.getRestTimeType() == d.getRestTimeType());
			this.data.add(d);
		}
	}
	
	@Override
	public void updateData(Object data) {
		if(data != null){
			setRecords(BreakTimeDailyDto.getDto((BreakTimeOfDailyPerformance) data));
		}
	}

	@Override
	public List<BreakTimeOfDailyPerformance> toDomain() {
		return data == null ? null : data.stream().map(c -> c.toDomain(getEmployeeId(), getWorkDate()))
				.collect(Collectors.toList());
	}
}
