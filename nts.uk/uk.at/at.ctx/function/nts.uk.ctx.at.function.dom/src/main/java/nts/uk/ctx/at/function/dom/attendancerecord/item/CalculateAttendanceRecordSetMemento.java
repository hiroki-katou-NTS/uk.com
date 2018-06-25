package nts.uk.ctx.at.function.dom.attendancerecord.item;

import java.util.List;

/**
 * The Interface CalculateAttendanceRecordSetMemento.
 */
public interface CalculateAttendanceRecordSetMemento {

	/**
	 * Sets the attribute.
	 *
	 * @param attribute the new attribute
	 */
	void setAttribute(CalculateItemAttributes attribute);

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	void setName(ItemName name);

	/**
	 * Sets the added item.
	 *
	 * @param idList the new added item
	 */
	void setAddedItem(List<Integer> idList);

	/**
	 * Sets the subtracted item.
	 *
	 * @param idList the new subtracted item
	 */
	void setSubtractedItem(List<Integer> idList);
}
