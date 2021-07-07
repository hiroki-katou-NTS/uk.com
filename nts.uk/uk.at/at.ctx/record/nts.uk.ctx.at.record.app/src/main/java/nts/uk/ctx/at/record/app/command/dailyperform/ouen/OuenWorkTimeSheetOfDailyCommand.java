package nts.uk.ctx.at.record.app.command.dailyperform.ouen;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.app.find.dailyattendance.timesheet.ouen.dto.OuenWorkTimeSheetOfDailyDto;
import nts.uk.ctx.at.record.dom.daily.ouen.OuenWorkTimeSheetOfDaily;
import nts.uk.ctx.at.shared.app.util.attendanceitem.DailyWorkCommonCommand;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ConvertibleAttendanceItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.OuenWorkTimeSheetOfDailyAttendance;
public class OuenWorkTimeSheetOfDailyCommand extends DailyWorkCommonCommand {
	
	@Getter
	private List<OuenWorkTimeSheetOfDailyAttendance> data = new ArrayList<>();

	@Override
	public void setRecords(ConvertibleAttendanceItem item) {
		if(item != null && item.isHaveData()){
			val value = ((OuenWorkTimeSheetOfDailyDto) item).toDomain(getEmployeeId(), getWorkDate());
			updateDatas(value);
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
	public OuenWorkTimeSheetOfDailyDto toDto() {
		OuenWorkTimeSheetOfDaily domainDaily = OuenWorkTimeSheetOfDaily.create(getEmployeeId(), getWorkDate(), this.data);
		return OuenWorkTimeSheetOfDailyDto.getDto(domainDaily);
	}
}
