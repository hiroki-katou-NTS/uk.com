package nts.uk.ctx.at.record.app.command.dailyperform.breaktime;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.record.app.find.dailyperform.resttime.dto.BreakTimeDailyDto;
import nts.uk.ctx.at.record.dom.breakorgoout.BreakTimeOfDailyPerformance;
import nts.uk.ctx.at.shared.app.util.attendanceitem.DailyWorkCommonCommand;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ConvertibleAttendanceItem;

public class BreakTimeOfDailyPerformanceCommand extends DailyWorkCommonCommand {

	@Getter
	private List<BreakTimeOfDailyPerformance> data = new ArrayList<>();

	@Override
	public void setRecords(ConvertibleAttendanceItem item) {
		if(item != null){
			this.data.add(((BreakTimeDailyDto) item).toDomain());
		}
	}
	
	@Override
	public void updateData(Object data) {
		if(data != null){
			BreakTimeOfDailyPerformance d = (BreakTimeOfDailyPerformance) data;
			this.data.removeIf(br -> br.getBreakType().value == d.getBreakType().value);
			this.data.add(d);
		}
	}
}
