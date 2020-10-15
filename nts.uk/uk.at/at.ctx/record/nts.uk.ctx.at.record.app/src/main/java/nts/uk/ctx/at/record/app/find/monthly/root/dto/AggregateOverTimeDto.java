package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.TimeMonthWithCalculation;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.totalworkingtime.overtime.AggregateOverTime;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;

@Data
/** 集計残業時間 */
@NoArgsConstructor
@AllArgsConstructor
public class AggregateOverTimeDto implements ItemConst {

	/** 残業枠NO */
	private int no;

	/** 残業時間 */
	@AttendanceItemLayout(jpPropertyName = OVERTIME, layout = LAYOUT_A)
	private TimeMonthWithCalculationDto overTime;

	/** 事前残業時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = BEFORE, layout = LAYOUT_B)
	private int beforeOverTime;

	/** 振替残業時間 */
	@AttendanceItemLayout(jpPropertyName = TRANSFER, layout = LAYOUT_C)
	private TimeMonthWithCalculationDto transferOverTime;

	/** 法定内残業時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = LEGAL, layout = LAYOUT_D)
	private int legalOverTime;

	/** 法定内振替残業時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = LEGAL + TRANSFER, layout = LAYOUT_E)
	private int legalTransferOverTime;

	public AggregateOverTime toDomain() {
		return AggregateOverTime.of(new OverTimeFrameNo(no), 
									overTime == null ? new TimeMonthWithCalculation() : overTime.toDomain(),
									new AttendanceTimeMonth(beforeOverTime),
									transferOverTime == null ? new TimeMonthWithCalculation() : transferOverTime.toDomain(),
									new AttendanceTimeMonth(legalOverTime),
									new AttendanceTimeMonth(legalTransferOverTime));
	}
	
	public static AggregateOverTimeDto from(AggregateOverTime domain) {
		AggregateOverTimeDto dto = new AggregateOverTimeDto();
		if(domain != null) {
			dto.setBeforeOverTime(domain.getBeforeOverTime() == null ? 0 : domain.getBeforeOverTime().valueAsMinutes());
			dto.setLegalOverTime(domain.getLegalOverTime() == null ? 0 : domain.getLegalOverTime().valueAsMinutes());
			dto.setLegalTransferOverTime(domain.getLegalTransferOverTime() == null ? 0 : domain.getLegalTransferOverTime().valueAsMinutes());
			dto.setOverTime(TimeMonthWithCalculationDto.from(domain.getOverTime()));
			dto.setNo(domain.getOverTimeFrameNo().v());
			dto.setTransferOverTime(TimeMonthWithCalculationDto.from(domain.getTransferOverTime()));
		}
		return dto;
	}
}
