package nts.uk.ctx.at.record.app.command.dailyperform.optionalitem;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.record.app.find.dailyperform.optionalitem.dto.OptionalItemOfDailyPerformDto;
import nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemValueOfDaily;
import nts.uk.ctx.at.shared.app.util.attendanceitem.DailyWorkCommonCommand;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemCommon;

public class OptionalItemOfDailyPerformCommand extends DailyWorkCommonCommand {

	@Getter
	private Optional<OptionalItemOfDailyPerformDto> data;

	@Override
	public void setRecords(AttendanceItemCommon item) {
		this.data = item == null || !item.isHaveData() ? Optional.empty() : Optional.of((OptionalItemOfDailyPerformDto) item);
	}

	@Override
	public void updateData(Object item) {
		if(data == null){ return; }
		setRecords(OptionalItemOfDailyPerformDto.getDto((AnyItemValueOfDaily) item));
	}

	@Override
	public Optional<AnyItemValueOfDaily> toDomain() {
		return data == null ? null : data.map(c -> c.toDomain(getEmployeeId(), getWorkDate()));
	}
}
