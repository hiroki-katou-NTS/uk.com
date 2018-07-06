package nts.uk.ctx.at.function.app.find.attendancerecord.export;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * The Class AttendanceItemDto.
 */
@Setter
@Getter
@AllArgsConstructor
public class AttendanceIdItemDto {

	/** The attendance item id. */
	private int attendanceItemId;

	/** The attendance item name. */
	private String attendanceItemName;

	/** The screen use item. */
	private int screenUseItem;
	
	/**
	 * Instantiates a new attendance item dto.
	 */
	public AttendanceIdItemDto(){
		
	}
}
