package nts.uk.ctx.at.function.app.find.attendancerecord.export;

import lombok.Builder;
import lombok.Data;

/**
 * Attribute of attendance item.
 */
@Data
@Builder
public class AttributeOfAttendanceItemDto {

	/**
	 *	勤怠項目ID
	 */
	private int attendanceItemId;

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
	private Integer masterType;
	
	/**
	 *	表示番号
	 */
	private int displayNumbers;

}
