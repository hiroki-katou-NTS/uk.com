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
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.worktime.midnighttime.IllegalMidnightTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 法定外深夜時間 */
public class IllegalMidnightTimeDto implements ItemConst {

	/** 時間 */
	@AttendanceItemLayout(jpPropertyName = TIME, layout = LAYOUT_A)
	private TimeMonthWithCalculationDto time;

	/** 事前時間 */
	@AttendanceItemLayout(jpPropertyName = BEFOR_APPLICATION, layout = LAYOUT_B)
	@AttendanceItemValue(type = ValueType.TIME)
	private int beforeTime;
	
	public static IllegalMidnightTimeDto from(IllegalMidnightTime domain) {
		IllegalMidnightTimeDto dto = new IllegalMidnightTimeDto();
		if(domain != null) {
			dto.setBeforeTime(domain.getBeforeTime() == null ? 0 : domain.getBeforeTime().valueAsMinutes());
			dto.setTime(TimeMonthWithCalculationDto.from(domain.getTime()));
		}
		return dto;
	}

	public IllegalMidnightTime toDomain(){
		return IllegalMidnightTime.of(time == null ? new TimeMonthWithCalculation() : time.toDomain(), new AttendanceTimeMonth(beforeTime));
	}
}
