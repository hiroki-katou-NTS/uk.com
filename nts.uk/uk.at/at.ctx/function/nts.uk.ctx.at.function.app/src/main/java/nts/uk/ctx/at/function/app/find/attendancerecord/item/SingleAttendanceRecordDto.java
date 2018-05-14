package nts.uk.ctx.at.function.app.find.attendancerecord.item;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SingleAttendanceRecordDto implements AttendanceRecordDisplayDto {

	/** The name. */
	String name;

	/** The item id. */
	Integer itemId;

	/** The attribute. */
	int attribute;
	
	

	/**
	 * Instantiates a new single attendance record dto.
	 *
	 * @param name
	 *            the name
	 * @param itemId
	 *            the item id
	 * @param attribute
	 *            the attribute
	 */
	public SingleAttendanceRecordDto(String name, Integer itemId, int attribute) {
		super();
		this.name = name;
		this.itemId = itemId;
		this.attribute = attribute;
	}



	/**
	 * Instantiates a new single attendance record dto.
	 */
	public SingleAttendanceRecordDto() {
		super();
	}

}
