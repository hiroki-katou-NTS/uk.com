package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthly.TimeMonthWithCalculation;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.overtime.OverTimeOfMonthly;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績の残業時間 */
public class OverTimeOfMonthlyDto {

	/** 残業合計時間 */
	@AttendanceItemLayout(jpPropertyName = "残業合計時間", layout = "A")
	private TimeMonthWithCalculationDto totalOverTime;

	/** 事前残業時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = "事前残業時間", layout = "B")
	private int beforeOverTime;

	/** 振替残業合計時間 */
	@AttendanceItemLayout(jpPropertyName = "振替残業合計時間", layout = "C")
	private TimeMonthWithCalculationDto totalTransferOverTime;

	/** 集計残業時間 */
	@AttendanceItemLayout(jpPropertyName = "集計残業時間", layout = "D", listMaxLength = 10, indexField = "overTimeFrameNo")
	private List<AggregateOverTimeDto> aggregateOverTimeMap;

	public OverTimeOfMonthly toDomain() {
		return OverTimeOfMonthly.of(totalOverTime == null ? new TimeMonthWithCalculation() : totalOverTime.toDomain(),
									new AttendanceTimeMonth(beforeOverTime),
									totalTransferOverTime == null ? new TimeMonthWithCalculation() : totalTransferOverTime.toDomain(),
									ConvertHelper.mapTo(aggregateOverTimeMap, c -> c.toDomain()));
	}
	
	public static OverTimeOfMonthlyDto from(OverTimeOfMonthly domain) {
		OverTimeOfMonthlyDto dto = new OverTimeOfMonthlyDto();
		if(domain != null) {
			dto.setAggregateOverTimeMap(ConvertHelper.mapTo(domain.getAggregateOverTimeMap(), c -> AggregateOverTimeDto.from(c.getValue())));
			dto.setBeforeOverTime(domain.getBeforeOverTime() == null ? 0 : domain.getBeforeOverTime().valueAsMinutes());
			dto.setTotalOverTime(TimeMonthWithCalculationDto.from(domain.getTotalOverTime()));
			dto.setTotalTransferOverTime(TimeMonthWithCalculationDto.from(domain.getTotalTransferOverTime()));
		}
		return dto;
	}
}
