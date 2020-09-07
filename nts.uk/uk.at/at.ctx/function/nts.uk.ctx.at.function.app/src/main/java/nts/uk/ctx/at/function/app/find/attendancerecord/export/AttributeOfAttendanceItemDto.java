package nts.uk.ctx.at.function.app.find.attendancerecord.export;

import java.util.Optional;

import lombok.Builder;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.enums.TypesMasterRelatedDailyAttendanceItem;

/**
 * Attribute of attendance item.
 */
@Data
@Builder
public class AttributeOfAttendanceItemDto {

	/**
	 *	勤怠項目ID
	 */
	private Integer attendanceItemId;

	/**
	 *	名称
	 */
	private String attendanceItemName;
	
	/**
	 *	属性 
	 */
	private int attendanceAtr;
	
	/**
	 *	マスタの種類
	 */
	private Optional<TypesMasterRelatedDailyAttendanceItem> masterType;
	
	/**
	 *	表示番号
	 */
	private Integer displayNumber;

}
