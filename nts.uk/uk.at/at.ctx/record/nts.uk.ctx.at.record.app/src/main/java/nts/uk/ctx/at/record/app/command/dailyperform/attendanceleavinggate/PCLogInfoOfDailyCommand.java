package nts.uk.ctx.at.record.app.command.dailyperform.attendanceleavinggate;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.record.app.find.dailyperform.pclogoninfor.dto.PCLogOnInforOfDailyPerformDto;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.PCLogOnInfoOfDaily;
import nts.uk.ctx.at.shared.app.util.attendanceitem.DailyWorkCommonCommand;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ConvertibleAttendanceItem;

public class PCLogInfoOfDailyCommand extends DailyWorkCommonCommand {

	@Getter
	private Optional<PCLogOnInfoOfDaily> data;

	@Override
	public void setRecords(ConvertibleAttendanceItem item) {
		
		if (item == null) {
			this.data = Optional.empty();
			return;
		}
		PCLogOnInfoOfDaily pCLogOnInfoOfDaily = new PCLogOnInfoOfDaily(getEmployeeId(), getWorkDate(), ((PCLogOnInforOfDailyPerformDto) item).toDomain(getEmployeeId(), getWorkDate()));
		this.data = item == null || !item.isHaveData() ? Optional.empty() 
				: Optional.of(pCLogOnInfoOfDaily);
	}

	@Override
	public void updateData(Object item) {
		if(item == null){ return; }
		this.data = Optional.of((PCLogOnInfoOfDaily) item);
	}

	@Override
	public Optional<PCLogOnInfoOfDaily> toDomain() {
		return this.data;
	}

	@Override
	public Optional<PCLogOnInforOfDailyPerformDto> toDto() {
		return getData().map(b -> PCLogOnInforOfDailyPerformDto.from(b));
	}

	@Override
	@SuppressWarnings("unchecked")
	public void updateDataO(Optional<?> data) {
		this.data = (Optional<PCLogOnInfoOfDaily>) data;
	}
}
