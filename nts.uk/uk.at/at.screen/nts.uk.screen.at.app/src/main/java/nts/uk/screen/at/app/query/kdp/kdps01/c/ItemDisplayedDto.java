package nts.uk.screen.at.app.query.kdp.kdps01.c;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.DailyAttendanceItem;

/**
 * 
 * @author sonnlb
 * 
 *         表示可能項目
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemDisplayedDto {
	/**
	 * 勤怠項目ID
	 */
	private int attendanceItemId;
	/**
	 * 名称
	 */
	private String attendanceName;
	/**
	 * 属性
	 */
	private int dailyAttendanceAtr;

	/**
	 * primitiveValue
	 */
	private Integer primitiveValue;

	public static ItemDisplayedDto fromDomain(DailyAttendanceItem domain) {

		return new ItemDisplayedDto(domain.getAttendanceItemId(), domain.getAttendanceName().v(),
				domain.getDailyAttendanceAtr().value, domain.getPrimitiveValue().map(x -> x.value).orElse(null));
	}
}
