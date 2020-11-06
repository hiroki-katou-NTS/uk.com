package nts.uk.ctx.at.record.app.find.monthly.root.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.arc.time.calendar.period.DatePeriod;

@Data
/** 期間 */
@NoArgsConstructor
@AllArgsConstructor
public class DatePeriodDto implements ItemConst {

	/** 開始日 */
	@AttendanceItemValue(type = ValueType.DATE)
	@AttendanceItemLayout(jpPropertyName = START, layout = LAYOUT_A)
	private GeneralDate start;

	/** 終了日 */
	@AttendanceItemValue(type = ValueType.DATE)
	@AttendanceItemLayout(jpPropertyName = END, layout = LAYOUT_B)
	private GeneralDate end;
	
	public static DatePeriodDto from(DatePeriod domain){
		return domain == null ? null : new DatePeriodDto(domain.start(), domain.end());
	}
	
	public DatePeriod toDomain(){
		return new DatePeriod(start, end);
	}
}
