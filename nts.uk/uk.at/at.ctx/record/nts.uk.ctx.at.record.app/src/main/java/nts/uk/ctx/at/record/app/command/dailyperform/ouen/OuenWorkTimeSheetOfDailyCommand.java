package nts.uk.ctx.at.record.app.command.dailyperform.ouen;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.app.find.dailyattendance.timesheet.ouen.dto.OuenWorkTimeSheetOfDailyAttendanceDto;
import nts.uk.ctx.at.shared.app.util.attendanceitem.DailyWorkCommonCommand;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ConvertibleAttendanceItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.OuenWorkTimeSheetOfDailyAttendance;
public class OuenWorkTimeSheetOfDailyCommand extends DailyWorkCommonCommand {
	
	@Getter
	private List<OuenWorkTimeSheetOfDailyAttendance> data = new ArrayList<>();

	@Override
	public void setRecords(ConvertibleAttendanceItem item) {
		if(item != null && item.isHaveData()){
			val value = ((OuenWorkTimeSheetOfDailyAttendanceDto) item).toDomain(getEmployeeId(), getWorkDate());
			updateData(value);
		}
	}
	
	@Override
	public void updateData(Object data) {
		if(data != null){
			OuenWorkTimeSheetOfDailyAttendance d = (OuenWorkTimeSheetOfDailyAttendance) data;
			this.data.removeIf(es -> es.getWorkNo() == d.getWorkNo());
			this.data.add(d);
		}
	}
	
	@Override
	public List<OuenWorkTimeSheetOfDailyAttendance> toDomain() {
		return data;
	}
	
	@Override
	public List<OuenWorkTimeSheetOfDailyAttendanceDto> toDto() {
		return getData().stream().map(b -> OuenWorkTimeSheetOfDailyAttendanceDto.from(b)).collect(Collectors.toList());
	}
}
