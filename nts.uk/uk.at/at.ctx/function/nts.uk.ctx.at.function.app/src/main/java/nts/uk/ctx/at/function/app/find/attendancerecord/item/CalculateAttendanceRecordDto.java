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
	List<Integer> itemId;

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
	 * @param name
	 *            the name
	 * @param itemId
	 *            the item id
	 * @param attribute
	 *            the attribute
	 */
	public CalculateAttendanceRecordDto(String name, List<Integer> itemId, int attribute) {
		super();
		this.name = name;
		this.itemId = itemId;
		this.attribute = attribute;
	}

}
