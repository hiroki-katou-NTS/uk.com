package nts.uk.ctx.at.record.app.find.monthly.root.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.app.find.monthly.root.dto.TimeMonthWithCalculationDto;
import nts.uk.ctx.at.shared.dom.common.times.AttendanceTimesMonth;
import nts.uk.ctx.at.record.dom.monthly.TimeMonthWithCalculation;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.lateleaveearly.Late;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.lateleaveearly.LeaveEarly;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 早退 + 遅刻 */
public class CommonTimeCountDto implements ItemConst {

	@AttendanceItemLayout(jpPropertyName = COUNT, layout = LAYOUT_A)
	@AttendanceItemValue(type = ValueType.COUNT)
	/** 回数: 勤怠月間回数 */
	private int times;

	@AttendanceItemLayout(jpPropertyName = TIME, layout = LAYOUT_B)
	/** 時間: 計算付き月間時間 */
	private TimeMonthWithCalculationDto time;
	
	public static CommonTimeCountDto from(LeaveEarly domain) {
		CommonTimeCountDto dto = new CommonTimeCountDto();
		if(domain != null) {
			dto.setTime(TimeMonthWithCalculationDto.from(domain.getTime()));
			dto.setTimes(domain.getTimes() == null ? 0 : domain.getTimes().v());
		}
		return dto;
	}
	
	public static CommonTimeCountDto from(Late domain) {
		CommonTimeCountDto dto = new CommonTimeCountDto();
		if(domain != null) {
			dto.setTime(TimeMonthWithCalculationDto.from(domain.getTime()));
			dto.setTimes(domain.getTimes() == null ? 0 : domain.getTimes().v());
		}
		return dto;
	}
	
	public Late toLate() {
		return Late.of(new AttendanceTimesMonth(times),
						time == null ? new TimeMonthWithCalculation() : time.toDomain());
	}
	
	public LeaveEarly toLeaveEarly() {
		return LeaveEarly.of(new AttendanceTimesMonth(times),
							time == null ? new TimeMonthWithCalculation() : time.toDomain());
	}
}
