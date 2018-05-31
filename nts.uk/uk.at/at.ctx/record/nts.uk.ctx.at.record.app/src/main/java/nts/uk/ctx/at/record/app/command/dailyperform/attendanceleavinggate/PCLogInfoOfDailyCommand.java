package nts.uk.ctx.at.record.app.command.dailyperform.attendanceleavinggate;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.record.app.find.dailyperform.pclogoninfor.dto.PCLogOnInforOfDailyPerformDto;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.PCLogOnInfoOfDaily;
import nts.uk.ctx.at.shared.app.util.attendanceitem.DailyWorkCommonCommand;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ConvertibleAttendanceItem;

public class PCLogInfoOfDailyCommand extends DailyWorkCommonCommand {

	@Getter
	private Optional<PCLogOnInforOfDailyPerformDto> data;

	@Override
	public void setRecords(ConvertibleAttendanceItem item) {
		this.data = item == null || !item.isHaveData() ? Optional.empty() : Optional.of((PCLogOnInforOfDailyPerformDto) item);
	}

	@Override
	public void updateData(Object item) {
		if(data == null){ return; }
		setRecords(PCLogOnInforOfDailyPerformDto.from((PCLogOnInfoOfDaily) item));
	}

	@Override
	public Optional<PCLogOnInfoOfDaily> toDomain() {
		return data == null ? null : data.map(c -> c.toDomain(getEmployeeId(), getWorkDate()));
	}
}
