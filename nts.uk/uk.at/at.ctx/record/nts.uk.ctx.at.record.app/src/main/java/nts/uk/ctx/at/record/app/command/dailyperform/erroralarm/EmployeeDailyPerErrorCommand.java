package nts.uk.ctx.at.record.app.command.dailyperform.erroralarm;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import nts.uk.ctx.at.record.app.find.dailyperform.erroralarm.dto.EmployeeDailyPerErrorDto;
import nts.uk.ctx.at.shared.app.util.attendanceitem.DailyWorkCommonCommand;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ConvertibleAttendanceItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.EmployeeDailyPerError;

public class EmployeeDailyPerErrorCommand extends DailyWorkCommonCommand {

	@Getter
	private List<EmployeeDailyPerError> data = new ArrayList<>();

	@Override
	public void setRecords(ConvertibleAttendanceItem item) {
		if(item != null && item.isHaveData()){
			updateData(((EmployeeDailyPerErrorDto) item).toDomain(getEmployeeId(), getWorkDate()));
		}
	}

	@Override
	public void updateData(Object data) {
		if(data != null){
			EmployeeDailyPerError d = (EmployeeDailyPerError) data;
			this.data.removeIf(es ->  es.getErrorAlarmWorkRecordCode().equals(d.getErrorAlarmWorkRecordCode()));
			this.data.add(d);
		}
	}

	@Override
	public List<EmployeeDailyPerError> toDomain() {
		return data;
	}
	
	@Override
	public List<EmployeeDailyPerErrorDto> toDto() {
		return getData().stream().map(b -> EmployeeDailyPerErrorDto.getDto(b)).collect(Collectors.toList());
	}
}
