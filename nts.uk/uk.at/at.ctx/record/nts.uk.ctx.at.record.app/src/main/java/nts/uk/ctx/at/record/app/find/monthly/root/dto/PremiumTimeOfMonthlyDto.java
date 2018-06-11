package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.premiumtime.PremiumTimeOfMonthly;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績の割増時間 */
public class PremiumTimeOfMonthlyDto {

	/** 割増時間: 集計割増時間 */
	@AttendanceItemLayout(jpPropertyName = "割増時間", layout = "A", listMaxLength = 10, indexField = "premiumTimeItemNo")
	private List<AggregatePremiumTimeDto> premiumTimes;

	/** 深夜時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = "深夜時間", layout = "B")
	private int midnightTime;

	/** 法定外休出時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = "法定外休出時間", layout = "C")
	private int illegalHolidayWorkTime;

	/** 法定外時間外時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = "法定外時間外時間", layout = "D")
	private int illegalOutsideWorkTime;

	/** 法定内休出時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = "法定内休出時間", layout = "E")
	private int legalHolidayWorkTime;

	/** 法定内時間外時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = "法定内時間外時間", layout = "F")
	private int legalOutsideWorkTime;
	
	public static PremiumTimeOfMonthlyDto from(PremiumTimeOfMonthly domain) {
		PremiumTimeOfMonthlyDto dto = new PremiumTimeOfMonthlyDto();
		if(domain != null) {
			dto.setIllegalHolidayWorkTime(domain.getIllegalHolidayWorkTime() == null ? 0 : domain.getIllegalHolidayWorkTime().valueAsMinutes());
			dto.setIllegalOutsideWorkTime(domain.getIllegalOutsideWorkTime() == null ? 0 : domain.getIllegalOutsideWorkTime().valueAsMinutes());
			dto.setLegalHolidayWorkTime(domain.getLegalHolidayWorkTime() == null ? 0 : domain.getLegalHolidayWorkTime().valueAsMinutes());
			dto.setLegalOutsideWorkTime(domain.getLegalOutsideWorkTime() == null ? 0 : domain.getLegalOutsideWorkTime().valueAsMinutes());
			dto.setMidnightTime(domain.getMidnightTime() == null ? 0 : domain.getMidnightTime().valueAsMinutes());
			dto.setPremiumTimes(ConvertHelper.mapTo(domain.getPremiumTime(), c -> AggregatePremiumTimeDto.from(c.getValue())));
		}
		return dto;
	}
	
	public PremiumTimeOfMonthly toDomain(){
		return PremiumTimeOfMonthly.of(ConvertHelper.mapTo(premiumTimes, c -> c.toDomain()), toAttendanceTimeMonth(midnightTime), 
				toAttendanceTimeMonth(legalOutsideWorkTime), toAttendanceTimeMonth(legalHolidayWorkTime), 
				toAttendanceTimeMonth(illegalOutsideWorkTime), toAttendanceTimeMonth(illegalHolidayWorkTime));
	}
	
	private AttendanceTimeMonth toAttendanceTimeMonth(Integer time){
		return new AttendanceTimeMonth(time);
	}
}
