package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.TimeMonthWithCalculation;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.totalworkingtime.hdwkandcompleave.AggregateHolidayWorkTime;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.HolidayWorkFrameNo;

@Data
/** 集計休出時間 */
@NoArgsConstructor
@AllArgsConstructor
public class AggregateHolidayWorkTimeDto implements ItemConst {

	/** 休出枠NO */
	private int no;

	/** 休出時間 */
	@AttendanceItemLayout(jpPropertyName = HOLIDAY_WORK, layout = LAYOUT_A)
	private TimeMonthWithCalculationDto holidayWorkTime;

	/** 事前休出時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = BEFORE, layout = LAYOUT_B)
	private int beforeHolidayWorkTime;

	/** 振替時間 */
	@AttendanceItemLayout(jpPropertyName = TRANSFER, layout = LAYOUT_C)
	private TimeMonthWithCalculationDto transferTime;

	/** 法定内休出時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = LEGAL + HOLIDAY_WORK, layout = LAYOUT_D)
	private int legalHolidayWorkTime;

	/** 法定内振替休出時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = LEGAL + TRANSFER, layout = LAYOUT_E)
	private int legalTransferHolidayWorkTime;


	public AggregateHolidayWorkTime toDomain() {
		return AggregateHolidayWorkTime.of(new HolidayWorkFrameNo(no),
											holidayWorkTime == null ? new TimeMonthWithCalculation() : holidayWorkTime.toDomain(),
											new AttendanceTimeMonth(beforeHolidayWorkTime),
											transferTime == null ? new TimeMonthWithCalculation() : transferTime.toDomain(),
											new AttendanceTimeMonth(legalHolidayWorkTime),
											new AttendanceTimeMonth(legalTransferHolidayWorkTime));
	}
	
	public static AggregateHolidayWorkTimeDto from(AggregateHolidayWorkTime domain) {
		AggregateHolidayWorkTimeDto dto = new AggregateHolidayWorkTimeDto();
		if(domain != null) {
			dto.setBeforeHolidayWorkTime(domain.getBeforeHolidayWorkTime() == null ? 0 : domain.getBeforeHolidayWorkTime().valueAsMinutes());
			dto.setNo(domain.getHolidayWorkFrameNo().v());
			dto.setHolidayWorkTime(TimeMonthWithCalculationDto.from(domain.getHolidayWorkTime()));
			dto.setLegalHolidayWorkTime(domain.getLegalHolidayWorkTime() == null ? 0 : domain.getLegalHolidayWorkTime().valueAsMinutes());
			dto.setLegalTransferHolidayWorkTime(domain.getLegalTransferHolidayWorkTime() == null ? 0 : domain.getLegalTransferHolidayWorkTime().valueAsMinutes());
			dto.setTransferTime(TimeMonthWithCalculationDto.from(domain.getTransferTime()));
		}
		return dto;
	}
}
