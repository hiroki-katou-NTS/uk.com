package nts.uk.ctx.at.function.app.find.attendancerecord.export;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.AttItemName;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.enums.DailyAttendanceAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.enums.TypesMasterRelatedDailyAttendanceItem;

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
	
	/** The attendance item name. */
	private List<DailyAttendanceAtr> attributes;
	
	/** The attendance item name. */
	private List<Optional<TypesMasterRelatedDailyAttendanceItem>> masterTypes;
	
	/** The attendance item name. */
	private List<Integer> displayNumbers;
	
	/**
	 * Instantiates a new attendance item dto.
	 */
	public AttributeOfAttendanceItemDto() {
		
	}
}
