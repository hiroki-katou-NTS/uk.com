package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthly.AttendanceDaysMonth;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.specificdays.AggregateSpecificDays;
import nts.uk.ctx.at.record.dom.raisesalarytime.primitivevalue.SpecificDateItemNo;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績の特定日数 + 集計特定日数 */
public class AggregateSpecificDaysDto {

	/** 特定日項目No */
	private int specificDayItemNo;

	/** 特定日数: 勤怠月間日数 */
	@AttendanceItemValue(type = ValueType.DOUBLE)
	@AttendanceItemLayout(jpPropertyName = "特定日数", layout = "A")
	private Double specificDays;

	/** 休出特定日数: 勤怠月間日数 */
	@AttendanceItemValue(type = ValueType.DOUBLE)
	@AttendanceItemLayout(jpPropertyName = "休出特定日数", layout = "B")
	private Double holidayWorkSpecificDays;
	
	public AggregateSpecificDays toDomain() {
		return AggregateSpecificDays.of(new SpecificDateItemNo(specificDayItemNo), 
										specificDays == null ? null : new AttendanceDaysMonth(specificDays),
										holidayWorkSpecificDays == null ? null : new AttendanceDaysMonth(holidayWorkSpecificDays));
	}
	
	public static AggregateSpecificDaysDto from(AggregateSpecificDays domain) {
		AggregateSpecificDaysDto dto = new AggregateSpecificDaysDto();
		if(domain != null) {
			dto.setHolidayWorkSpecificDays(domain.getHolidayWorkSpecificDays() == null ? null : domain.getHolidayWorkSpecificDays().v());
			dto.setSpecificDayItemNo(domain.getSpecificDayItemNo().v());
			dto.setSpecificDays(domain.getSpecificDays() == null ? null : domain.getSpecificDays().v());
		}
		return dto;
	}
}
