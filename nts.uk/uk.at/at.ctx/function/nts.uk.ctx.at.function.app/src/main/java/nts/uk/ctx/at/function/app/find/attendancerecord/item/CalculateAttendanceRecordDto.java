package nts.uk.ctx.at.function.app.find.attendancerecord.item;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class CalculateAttendanceRecordDto.
 */
@Getter
@Setter
public class CalculateAttendanceRecordDto implements AttendanceRecordDisplayDto {

	/** The name. */
	String name;

	/** The item id. */
	List<AttendanceRecordItemDto> addedItem;
	
	/** The subtracted item. */
	List<AttendanceRecordItemDto> subtractedItem;

	/** The attribute. */
	int attribute;

	/**
	 * Instantiates a new calculate attendance record dto.
	 */
	public CalculateAttendanceRecordDto() {
		super();
	}

	/**
	 * Instantiates a new calculate attendance record dto.
	 *
	 * @param name the name
	 * @param addedItem the added item
	 * @param subtractedItem the subtracted item
	 * @param attribute the attribute
	 */
	public CalculateAttendanceRecordDto(String name, List<AttendanceRecordItemDto> addedItem, List<AttendanceRecordItemDto> subtractedItem,
			int attribute) {
		super();
		this.name = name;
		this.addedItem = addedItem;
		this.subtractedItem = subtractedItem;
		this.attribute = attribute;
	}
}
