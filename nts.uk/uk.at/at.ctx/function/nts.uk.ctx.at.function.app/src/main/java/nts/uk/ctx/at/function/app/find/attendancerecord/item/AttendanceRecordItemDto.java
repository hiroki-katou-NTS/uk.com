package nts.uk.ctx.at.function.app.find.attendancerecord.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AttendanceRecordItemDto {
	
	/** The attendance item id. */
	private int attendanceItemId;

	/** The attendance item name. */
	private String attendanceItemName;

	/** The screen use item. */
	private int screenUseItem;
	
	/** The type of attendance item. */
	private int typeOfAttendanceItem;
	

	/**
	 * Instantiates a new attendance record item dto.
	 */
	public AttendanceRecordItemDto() {
		super();
	}
	
}
