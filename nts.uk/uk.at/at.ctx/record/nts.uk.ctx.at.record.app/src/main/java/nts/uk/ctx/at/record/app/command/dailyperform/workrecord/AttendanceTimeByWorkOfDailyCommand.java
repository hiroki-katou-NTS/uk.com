package nts.uk.ctx.at.record.app.command.dailyperform.workrecord;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.record.app.find.dailyperform.workrecord.dto.AttendanceTimeByWorkOfDailyDto;
import nts.uk.ctx.at.record.dom.actualworkinghours.daily.workrecord.AttendanceTimeByWorkOfDaily;
import nts.uk.ctx.at.shared.app.util.attendanceitem.DailyWorkCommonCommand;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ConvertibleAttendanceItem;

public class AttendanceTimeByWorkOfDailyCommand extends DailyWorkCommonCommand {

	@Getter
	private Optional<AttendanceTimeByWorkOfDaily> data;

	@Override
	public void setRecords(ConvertibleAttendanceItem item) {
		this.data = item == null || !item.isHaveData() ? Optional.empty() 
				: Optional.of(((AttendanceTimeByWorkOfDailyDto) item).toDomain(getEmployeeId(), getWorkDate()));
	}

	@Override
	public void updateData(Object item) {
		if(item == null){ return; }
		this.data = Optional.of((AttendanceTimeByWorkOfDaily) item);
	}

	@Override
	public Optional<AttendanceTimeByWorkOfDaily> toDomain() {
		return data;
	}

	@Override
	public Optional<AttendanceTimeByWorkOfDailyDto> toDto() {
		return getData().map(b -> AttendanceTimeByWorkOfDailyDto.getDto(b));
	}

	@Override
	@SuppressWarnings("unchecked")
	public void updateDataO(Optional<?> data) {
		this.data = (Optional<AttendanceTimeByWorkOfDaily>) data;
	}
}
