package nts.uk.ctx.at.record.app.command.dailyperform.affiliationInfor;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.record.app.find.dailyperform.affiliationInfor.dto.BusinessTypeOfDailyPerforDto;
import nts.uk.ctx.at.shared.app.util.attendanceitem.DailyWorkCommonCommand;
import nts.uk.ctx.at.shared.dom.affiliationinformation.WorkTypeOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ConvertibleAttendanceItem;

public class BusinessTypeOfDailyPerformCommand extends DailyWorkCommonCommand {

	@Getter
	private Optional<WorkTypeOfDailyPerformance> data;

	@Override
	public void setRecords(ConvertibleAttendanceItem item) {
		this.data = item == null || !item.isHaveData() ? Optional.empty() 
				: Optional.of(((BusinessTypeOfDailyPerforDto) item).toDomain(getEmployeeId(), getWorkDate()));
	}

	@Override
	public void updateData(Object data) {
		if(data == null){ return; }
		this.data = Optional.of((WorkTypeOfDailyPerformance) data);
	}
	
	@Override
	public Optional<WorkTypeOfDailyPerformance> toDomain() {
		return data;
	}

	@Override
	public Optional<BusinessTypeOfDailyPerforDto> toDto() {
		return getData().map(b -> BusinessTypeOfDailyPerforDto.getDto(b));
	}

	@Override
	@SuppressWarnings("unchecked")
	public void updateDataO(Optional<?> data) {
		this.data = (Optional<WorkTypeOfDailyPerformance>) data;
	}
}
