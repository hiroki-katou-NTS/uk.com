package nts.uk.ctx.at.function.app.find.attendancerecord.export;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.AttItemName;

/**
 * Name and attribute corresponding to the daily work item
 */
@Setter
@Getter
@AllArgsConstructor
public class AttributeOfAttendanceItemDto {

	/** The attendance item id. */
	private List<Integer> attendanceItemIds;

	/** The attendance item name. */
	private List<AttItemName> attendanceItemNames;
	
	/**
	 * Instantiates a new attendance item dto.
	 */
	public AttributeOfAttendanceItemDto() {
		
	}
}
