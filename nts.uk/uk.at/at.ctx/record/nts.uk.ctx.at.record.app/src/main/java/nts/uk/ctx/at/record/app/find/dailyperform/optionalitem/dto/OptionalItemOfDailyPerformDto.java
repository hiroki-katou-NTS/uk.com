package nts.uk.ctx.at.record.app.find.dailyperform.optionalitem.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemAmount;
import nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemNo;
import nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemTime;
import nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemTimes;
import nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemValue;
import nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemValueOfDaily;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemRoot;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemCommon;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ConvertibleAttendanceItem;

@Data
@AttendanceItemRoot(rootName = "日別実績の任意項目")
public class OptionalItemOfDailyPerformDto extends AttendanceItemCommon {

	private String employeeId;

	private GeneralDate date;

	@AttendanceItemLayout(layout = "A", jpPropertyName = "任意項目値", listMaxLength = 99, indexField = "itemNo")
	private List<OptionalItemValueDto> optionalItems;

	public static OptionalItemOfDailyPerformDto getDto(AnyItemValueOfDaily domain) {
		OptionalItemOfDailyPerformDto dto = new OptionalItemOfDailyPerformDto();
		if (domain != null) {
			dto.setDate(domain.getYmd());
			dto.setEmployeeId(domain.getEmployeeId());
			dto.setOptionalItems(ConvertHelper.mapTo(domain.getItems(), (c) -> {
				boolean isTimes = c.getTimes().isPresent();
				boolean isAmount = c.getAmount().isPresent();
				boolean isTime = c.getTime().isPresent();
				String value = isAmount ? c.getAmount().get().v().toString()
						: isTime ? String.valueOf(c.getTime().get().valueAsMinutes())
								: String.valueOf(c.getTimes().get().v());
				return new OptionalItemValueDto(value, c.getItemNo().v(), isTime, isTimes, isAmount);
			}));
			dto.exsistData();
		}
		return dto;
	}

	@Override
	public String employeeId() {
		return this.employeeId;
	}

	@Override
	public GeneralDate workingDate() {
		return this.date;
	}
	
	@Override
	public AnyItemValueOfDaily toDomain(String employeeId, GeneralDate date) {
		if(!this.isHaveData()) {
			return null;
		}
		if (employeeId == null) {
			employeeId = this.employeeId();
		}
		if (date == null) {
			date = this.workingDate();
		}
		return new AnyItemValueOfDaily(employeeId, date, ConvertHelper.mapTo(optionalItems,
								(c) -> new AnyItemValue(new AnyItemNo(c.getItemNo()), 
									c.isTimesItem() ? Optional.of(new AnyItemTimes(Integer.valueOf(c.getValue()))) : Optional.empty(),
									c.isAmountItem() ? Optional.of(new AnyItemAmount(new BigDecimal(c.getValue()))) : Optional.empty(),
									c.isTimeItem() ? Optional.of(new AnyItemTime(Integer.valueOf(c.getValue()))) : Optional.empty())));
	}
}
