package nts.uk.ctx.at.record.app.find.monthly.root.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

@Data
/** 日付 */
@NoArgsConstructor
@AllArgsConstructor
public class ClosureDateDto {

	/** 日: int */
	@AttendanceItemValue(type = ValueType.NUMBER)
	@AttendanceItemLayout(jpPropertyName = "日", layout = "A")
	private Integer closureDay;

	/** 末日とする: boolean */
	@AttendanceItemValue(type = ValueType.FLAG)
	@AttendanceItemLayout(jpPropertyName = "末日とする", layout = "B")
	private Boolean lastDayOfMonth;
	
	public static ClosureDateDto from(ClosureDate domain) {
		ClosureDateDto dto = new ClosureDateDto();
		if(domain != null) {
			dto.setClosureDay(domain.getClosureDay() == null ? null : domain.getClosureDay().v());
			dto.setLastDayOfMonth(domain.getLastDayOfMonth());
		}
		return dto;
	}
	
	public ClosureDate toDomain() {
		return new ClosureDate(closureDay, lastDayOfMonth);
	}
}
