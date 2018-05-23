package nts.uk.ctx.at.record.app.command.dailyperform.editstate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import nts.uk.ctx.at.record.app.find.dailyperform.editstate.EditStateOfDailyPerformanceDto;
import nts.uk.ctx.at.record.dom.editstate.EditStateOfDailyPerformance;
import nts.uk.ctx.at.shared.app.util.attendanceitem.DailyWorkCommonCommand;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemCommon;

public class EditStateOfDailyPerformCommand extends DailyWorkCommonCommand {

	@Getter
	private List<EditStateOfDailyPerformanceDto> data = new ArrayList<>();

	@Override
	public void setRecords(AttendanceItemCommon item) {
		if(item != null && item.isHaveData()){
			EditStateOfDailyPerformanceDto d = (EditStateOfDailyPerformanceDto) item;
			this.data.removeIf(es -> es.getAttendanceItemId() == d.getAttendanceItemId());
			this.data.add(d);
		}
	}

	@Override
	public void updateData(Object data) {
		if(data != null){
			setRecords(EditStateOfDailyPerformanceDto.getDto((EditStateOfDailyPerformance) data));
		}
	}
	
	public void updateDatas( List<EditStateOfDailyPerformance> datas){
		datas.stream().forEach(c -> updateData(c));
	}

	@Override
	public List<EditStateOfDailyPerformance> toDomain() {
		return data == null ? null : data.stream().map(c -> c.toDomain(getEmployeeId(), getWorkDate()))
				.collect(Collectors.toList());
	}
}
