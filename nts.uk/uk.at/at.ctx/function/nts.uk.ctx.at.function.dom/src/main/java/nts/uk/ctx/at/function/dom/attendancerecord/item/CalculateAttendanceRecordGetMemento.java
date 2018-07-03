package nts.uk.ctx.at.function.dom.attendancerecord.item;

import java.util.List;

/**
 * The Interface CalculateAttendanceRecordGetMemento.
 */
public interface CalculateAttendanceRecordGetMemento {

	/**
	 * Gets the attribute.
	 *
	 * @return the attribute
	 */
	CalculateItemAttributes getAttribute();

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	ItemName getName();

	/**
	 * Gets the added item.
	 *
	 * @return the added item
	 */
	List<Integer> getAddedItem();

	/**
	 * Gets the subtracted item.
	 *
	 * @return the subtracted item
	 */
	List<Integer> getSubtractedItem();
}
