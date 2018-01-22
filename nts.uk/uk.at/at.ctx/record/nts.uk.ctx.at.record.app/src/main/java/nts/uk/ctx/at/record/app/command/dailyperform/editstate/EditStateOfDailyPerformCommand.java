package nts.uk.ctx.at.record.app.command.dailyperform.editstate;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.record.app.find.dailyperform.editstate.EditStateOfDailyPerformanceDto;
import nts.uk.ctx.at.record.dom.editstate.EditStateOfDailyPerformance;
import nts.uk.ctx.at.record.dom.editstate.enums.EditStateSetting;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.app.util.attendanceitem.DailyWorkCommonCommand;
import nts.uk.ctx.at.shared.app.util.attendanceitem.item.ConvertibleAttendanceItem;

public class EditStateOfDailyPerformCommand extends DailyWorkCommonCommand {

	@Getter
	private List<EditStateOfDailyPerformanceDto> data = new ArrayList<>();

	@Override
	public void setRecords(ConvertibleAttendanceItem item) {
		if(item != null){
			this.data.add((EditStateOfDailyPerformanceDto) item);
		}
	}
	
	@Override
	public List<EditStateOfDailyPerformance> toDomain() {
		return ConvertHelper.mapTo(data, (c) -> new EditStateOfDailyPerformance(getEmployeeId(), c.getAttendanceItemId(),
				getWorkDate(), ConvertHelper.getEnum(c.getEditStateSetting(), EditStateSetting.class)));
	}
}
