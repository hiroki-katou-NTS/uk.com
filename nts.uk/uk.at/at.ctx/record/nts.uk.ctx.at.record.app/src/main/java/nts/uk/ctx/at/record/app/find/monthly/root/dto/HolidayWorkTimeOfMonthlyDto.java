package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthly.TimeMonthWithCalculation;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.hdwkandcompleave.HolidayWorkTimeOfMonthly;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績の休出時間 */
public class HolidayWorkTimeOfMonthlyDto {

	/** 休出合計時間 */
	@AttendanceItemLayout(jpPropertyName = "休出合計時間", layout = "A")
	private TimeMonthWithCalculationDto totalHolidayWorkTime;
	/** 事前休出時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = "事前休出時間", layout = "B")
	private int beforeHolidayWorkTime;
	/** 振替合計時間 */
	@AttendanceItemLayout(jpPropertyName = "振替合計時間", layout = "C")
	private TimeMonthWithCalculationDto totalTransferTime;
	/** 集計休出時間 */
	@AttendanceItemLayout(jpPropertyName = "集計休出時間", layout = "D", listMaxLength = 10, indexField = "holidayWorkFrameNo")
	private List<AggregateHolidayWorkTimeDto> aggregateHolidayWorkTimeMap;

	public HolidayWorkTimeOfMonthly toDomain() {
		return HolidayWorkTimeOfMonthly.of(totalHolidayWorkTime == null ? new TimeMonthWithCalculation() : totalHolidayWorkTime.toDomain(),
											new AttendanceTimeMonth(beforeHolidayWorkTime),
											totalTransferTime == null ? new TimeMonthWithCalculation() : totalTransferTime.toDomain(),
											ConvertHelper.mapTo(aggregateHolidayWorkTimeMap, c -> c.toDomain()));
	}
	
	public static HolidayWorkTimeOfMonthlyDto from(HolidayWorkTimeOfMonthly domain) {
		HolidayWorkTimeOfMonthlyDto dto = new HolidayWorkTimeOfMonthlyDto();
		if(domain != null) {
			dto.setAggregateHolidayWorkTimeMap(ConvertHelper.mapTo(domain.getAggregateHolidayWorkTimeMap(), 
					c -> AggregateHolidayWorkTimeDto.from(c.getValue())));
			dto.setBeforeHolidayWorkTime(domain.getBeforeHolidayWorkTime() == null ? 0 : domain.getBeforeHolidayWorkTime().valueAsMinutes());
			dto.setTotalHolidayWorkTime(TimeMonthWithCalculationDto.from(domain.getTotalHolidayWorkTime()));
			dto.setTotalTransferTime(TimeMonthWithCalculationDto.from(domain.getTotalTransferTime()));
		}
		return dto;
	}
}
