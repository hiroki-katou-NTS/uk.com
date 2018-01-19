package nts.uk.ctx.at.record.app.command.dailyperform.optionalitem;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.record.app.find.dailyperform.optionalitem.dto.OptionalItemOfDailyPerformDto;
import nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemAmount;
import nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemNo;
import nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemTime;
import nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemTimes;
import nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemValue;
import nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemValueOfDaily;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.app.util.attendanceitem.DailyWorkCommonCommand;
import nts.uk.ctx.at.shared.dom.attendanceitem.util.item.ConvertibleAttendanceItem;

public class OptionalItemOfDailyPerformCommand extends DailyWorkCommonCommand {

	@Getter
	private Optional<OptionalItemOfDailyPerformDto> data;

	@Override
	public void setRecords(ConvertibleAttendanceItem item) {
		this.data = item == null ? Optional.empty() : Optional.of((OptionalItemOfDailyPerformDto) item);
	}
	
	@Override
	public AnyItemValueOfDaily toDomain() {
		return data.isPresent() ? new AnyItemValueOfDaily(getEmployeeId(), getWorkDate(),
				ConvertHelper.mapTo(data.get().getOptionalItems(),
						(c) -> new AnyItemValue(new AnyItemNo(c.getItemNo()), 
								c.isTimesItem() ? Optional.of(new AnyItemTimes(Integer.valueOf(c.getValue()))) : Optional.empty(),
								c.isAmountItem() ? Optional.of(new AnyItemAmount(new BigDecimal(c.getValue()))) : Optional.empty(),
								c.isTimeItem() ? Optional.of(new AnyItemTime(Integer.valueOf(c.getValue()))) : Optional.empty()))) : null;
	}
}
