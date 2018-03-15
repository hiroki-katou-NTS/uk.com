package nts.uk.ctx.at.record.app.command.dailyperform.editstate;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.record.app.find.dailyperform.editstate.EditStateOfDailyPerformanceDto;
import nts.uk.ctx.at.record.dom.editstate.EditStateOfDailyPerformance;
import nts.uk.ctx.at.shared.app.util.attendanceitem.DailyWorkCommonCommand;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemCommon;

public class EditStateOfDailyPerformCommand extends DailyWorkCommonCommand {

	@Getter
	private List<EditStateOfDailyPerformance> data = new ArrayList<>();

	@Override
	public void setRecords(AttendanceItemCommon item) {
		if(item != null && item.isHaveData()){
			this.data.add(((EditStateOfDailyPerformanceDto) item).toDomain(getEmployeeId(), getWorkDate()));
		}
	}

	@Override
	public void updateData(Object data) {
		if(data != null){
			EditStateOfDailyPerformance d = (EditStateOfDailyPerformance) data;
			this.data.removeIf(es -> es.getAttendanceItemId() == d.getAttendanceItemId());
			this.data.add(d);
		}
	}
	
	public void updateDatas( List<EditStateOfDailyPerformance> datas){
		if(!datas.isEmpty()){
			datas.forEach(data -> {
				this.data.removeIf(es -> es.getAttendanceItemId() == data.getAttendanceItemId());
				this.data.add(data);
			});
		}
	}
}
