package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.premiumtime.PremiumTimeOfMonthly;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績の割増時間 */
public class PremiumTimeOfMonthlyDto implements ItemConst {

	/** 割増時間: 集計割増時間 */
	@AttendanceItemLayout(jpPropertyName = PREMIUM, layout = LAYOUT_A, listMaxLength = 10, indexField = DEFAULT_INDEX_FIELD_NAME)
	private List<AggregatePremiumTimeDto> premiumTimes;

	/** 深夜時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = LATE_NIGHT, layout = LAYOUT_B)
	private int midnightTime;

	/** 法定外休出時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = ILLEGAL + HOLIDAY_WORK, layout = LAYOUT_C)
	private int illegalHolidayWorkTime;

	/** 法定外時間外時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = ILLEGAL + TIME, layout = LAYOUT_D)
	private int illegalOutsideWorkTime;

	/** 法定内休出時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = LEGAL + HOLIDAY_WORK, layout = LAYOUT_E)
	private int legalHolidayWorkTime;

	/** 法定内時間外時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = LEGAL + TIME, layout = LAYOUT_F)
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
