package nts.uk.ctx.at.record.app.command.dailyperform.optionalitem;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.record.app.find.dailyperform.optionalitem.dto.OptionalItemOfDailyPerformDto;
import nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemValueOfDaily;
import nts.uk.ctx.at.shared.app.util.attendanceitem.DailyWorkCommonCommand;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ConvertibleAttendanceItem;

public class OptionalItemOfDailyPerformCommand extends DailyWorkCommonCommand {

	@Getter
	private Optional<AnyItemValueOfDaily> data;

	@Override
	public void setRecords(ConvertibleAttendanceItem item) {
		this.data = item == null || !item.isHaveData() ? Optional.empty() 
				: Optional.of(((OptionalItemOfDailyPerformDto) item).toDomain(getEmployeeId(), getWorkDate()));
	}

	@Override
	public void updateData(Object item) {
		if(data == null){ return; }
		this.data = Optional.of((AnyItemValueOfDaily) item);
	}

	@Override
	public Optional<AnyItemValueOfDaily> toDomain() {
		return data;
	}

	@Override
	public Optional<OptionalItemOfDailyPerformDto> toDto() {
		return getData().map(b -> OptionalItemOfDailyPerformDto.getDto(b));
	}

	@Override
	@SuppressWarnings("unchecked")
	public void updateDataO(Optional<?> data) {
		this.data = (Optional<AnyItemValueOfDaily>) data;
	}
}
