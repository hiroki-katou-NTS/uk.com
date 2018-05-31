package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.overtime.AggregateOverTime;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;

@Data
/** 集計残業時間 */
@NoArgsConstructor
@AllArgsConstructor
public class AggregateOverTimeDto {

	/** 残業枠NO */
	private int overTimeFrameNo;

	/** 残業時間 */
	@AttendanceItemLayout(jpPropertyName = "残業時間", layout = "A")
	private TimeMonthWithCalculationDto overTime;

	/** 事前残業時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = "事前残業時間", layout = "B")
	private Integer beforeOverTime;

	/** 振替残業時間 */
	@AttendanceItemLayout(jpPropertyName = "振替残業時間", layout = "C")
	private TimeMonthWithCalculationDto transferOverTime;

	/** 法定内残業時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = "法定内残業時間", layout = "D")
	private Integer legalOverTime;

	/** 法定内振替残業時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = "法定内振替残業時間", layout = "E")
	private Integer legalTransferOverTime;

	public AggregateOverTime toDomain() {
		return AggregateOverTime.of(new OverTimeFrameNo(overTimeFrameNo), 
									overTime == null ? null : overTime.toDomain(),
									beforeOverTime == null ? null : new AttendanceTimeMonth(beforeOverTime),
									transferOverTime == null ? null : transferOverTime.toDomain(),
									legalOverTime == null ? null : new AttendanceTimeMonth(legalOverTime),
									legalTransferOverTime == null ? null : new AttendanceTimeMonth(legalTransferOverTime));
	}
	
	public static AggregateOverTimeDto from(AggregateOverTime domain) {
		AggregateOverTimeDto dto = new AggregateOverTimeDto();
		if(domain != null) {
			dto.setBeforeOverTime(domain.getBeforeOverTime() == null ? null : domain.getBeforeOverTime().valueAsMinutes());
			dto.setLegalOverTime(domain.getLegalOverTime() == null ? null : domain.getLegalOverTime().valueAsMinutes());
			dto.setLegalTransferOverTime(domain.getLegalTransferOverTime() == null ? null : domain.getLegalTransferOverTime().valueAsMinutes());
			dto.setOverTime(TimeMonthWithCalculationDto.from(domain.getOverTime()));
			dto.setOverTimeFrameNo(domain.getOverTimeFrameNo().v());
			dto.setTransferOverTime(TimeMonthWithCalculationDto.from(domain.getTransferOverTime()));
		}
		return dto;
	}
}
