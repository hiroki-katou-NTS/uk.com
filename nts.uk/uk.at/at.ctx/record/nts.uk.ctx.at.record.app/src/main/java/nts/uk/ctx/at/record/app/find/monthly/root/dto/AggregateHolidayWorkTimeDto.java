package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.hdwkandcompleave.AggregateHolidayWorkTime;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.HolidayWorkFrameNo;

@Data
/** 集計休出時間 */
@NoArgsConstructor
@AllArgsConstructor
public class AggregateHolidayWorkTimeDto {

	/** 休出枠NO */
	private int holidayWorkFrameNo;

	/** 休出時間 */
	@AttendanceItemLayout(jpPropertyName = "休出時間", layout = "A")
	private TimeMonthWithCalculationDto holidayWorkTime;

	/** 事前休出時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = "事前休出時間", layout = "B")
	private Integer beforeHolidayWorkTime;

	/** 振替時間 */
	@AttendanceItemLayout(jpPropertyName = "振替時間", layout = "C")
	private TimeMonthWithCalculationDto transferTime;

	/** 法定内休出時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = "法定内休出時間", layout = "D")
	private Integer legalHolidayWorkTime;

	/** 法定内振替休出時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = "法定内振替休出時間", layout = "E")
	private Integer legalTransferHolidayWorkTime;

	public AggregateHolidayWorkTime toDomain() {
		return AggregateHolidayWorkTime.of(new HolidayWorkFrameNo(holidayWorkFrameNo),
											holidayWorkTime == null ? null : holidayWorkTime.toDomain(),
											beforeHolidayWorkTime == null ? null : new AttendanceTimeMonth(beforeHolidayWorkTime),
											transferTime == null ? null : transferTime.toDomain(),
											legalHolidayWorkTime == null ? null : new AttendanceTimeMonth(legalHolidayWorkTime),
											legalTransferHolidayWorkTime == null ? null : new AttendanceTimeMonth(legalTransferHolidayWorkTime));
	}
	
	public static AggregateHolidayWorkTimeDto from(AggregateHolidayWorkTime domain) {
		AggregateHolidayWorkTimeDto dto = new AggregateHolidayWorkTimeDto();
		if(domain != null) {
			dto.setBeforeHolidayWorkTime(domain.getBeforeHolidayWorkTime() == null ? null : domain.getBeforeHolidayWorkTime().valueAsMinutes());
			dto.setHolidayWorkFrameNo(domain.getHolidayWorkFrameNo().v());
			dto.setHolidayWorkTime(TimeMonthWithCalculationDto.from(domain.getHolidayWorkTime()));
			dto.setLegalHolidayWorkTime(domain.getLegalHolidayWorkTime() == null ? null : domain.getLegalHolidayWorkTime().valueAsMinutes());
			dto.setLegalTransferHolidayWorkTime(domain.getLegalTransferHolidayWorkTime() == null ? null : domain.getLegalTransferHolidayWorkTime().valueAsMinutes());
			dto.setTransferTime(TimeMonthWithCalculationDto.from(domain.getTransferTime()));
		}
		return dto;
	}
}
