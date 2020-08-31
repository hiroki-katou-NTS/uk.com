package nts.uk.ctx.at.function.app.find.attendancerecord.export;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.enums.DailyAttendanceAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.enums.TypesMasterRelatedDailyAttendanceItem;

/**
 * Attribute of attendance item.
 */
@Setter
@Getter
@AllArgsConstructor
public class AttributeOfAttendanceItemDto {

	/**
	 * List 勤怠項目ID
	 */
	private List<Integer> attendanceItemIds;

	/**
	 * List 名称
	 */
	private List<String> attendanceItemNames;
	
	/**
	 * List 属性 
	 */
	private List<DailyAttendanceAtr> attendanceAtrs;
	
	/**
	 *  List マスタの種類
	 */
	private List<Optional<TypesMasterRelatedDailyAttendanceItem>> masterTypes;
	
	/**
	 * List 表示番号
	 */
	private List<Integer> displayNumbers;
	
	/**
	 * Attribute of attendance item.
	 */
	public AttributeOfAttendanceItemDto() {
		
	}
}
