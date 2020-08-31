package nts.uk.screen.at.app.query.kdp.kdps01.c;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.DailyAttendanceItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.AttItemName;

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

	public static ItemDisplayedDto fromDomain(DailyAttendanceItem domain, List<AttItemName> dailyItems) {
		//vì thằng DailyAttendanceItem set tên không chuẩn nên phải lấy name của thằng AttItemName
		ItemDisplayedDto result = new ItemDisplayedDto();
		dailyItems.stream().filter(x -> x.getAttendanceItemId() == domain.getAttendanceItemId()).findFirst()
				.ifPresent(item -> {
					result.setAttendanceItemId(domain.getAttendanceItemId());
					result.setAttendanceName(item.getAttendanceItemName());
					result.setDailyAttendanceAtr(domain.getDailyAttendanceAtr().value);
					result.setPrimitiveValue(domain.getPrimitiveValue().map(x -> x.value).orElse(null));
				});
		return result;
	}
}
