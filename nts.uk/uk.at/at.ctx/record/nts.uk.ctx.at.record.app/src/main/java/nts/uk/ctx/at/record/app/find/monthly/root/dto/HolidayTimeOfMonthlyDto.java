package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.holidaytime.HolidayTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績の勤務時間 */
public class HolidayTimeOfMonthlyDto {

	/** 法定内休日時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = "法定内休日時間", layout = "A")
	private Integer legalHolTime;
	
	/** 法定外休日時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = "法定外休日時間", layout = "B")
	private Integer illegalHolTime;
	
	/** 法定外祝日休日時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = "法定外祝日休日時間", layout = "C")
	private Integer illegalSpeHolTime;
	
	public static HolidayTimeOfMonthlyDto from(HolidayTimeOfMonthly domain) {
		HolidayTimeOfMonthlyDto dto = new HolidayTimeOfMonthlyDto();
		if(domain != null) {
			dto.setIllegalHolTime(domain.getIllegalHolidayTime() == null ? null : domain.getIllegalHolidayTime().valueAsMinutes());
			dto.setIllegalSpeHolTime(domain.getIllegalSpecialHolidayTime() == null ? null : domain.getIllegalSpecialHolidayTime().valueAsMinutes());
			dto.setLegalHolTime(domain.getLegalHolidayTime() == null ? null : domain.getLegalHolidayTime().valueAsMinutes());
		}
		return dto;
	}
	public HolidayTimeOfMonthly toDomain() {
		return HolidayTimeOfMonthly.of(toAttendanceTimeMonth(legalHolTime),
										toAttendanceTimeMonth(illegalHolTime),
										toAttendanceTimeMonth(illegalSpeHolTime));
	}
	
	private AttendanceTimeMonth toAttendanceTimeMonth(Integer time) {
		return time == null ? null : new AttendanceTimeMonth(time);
	}
}
