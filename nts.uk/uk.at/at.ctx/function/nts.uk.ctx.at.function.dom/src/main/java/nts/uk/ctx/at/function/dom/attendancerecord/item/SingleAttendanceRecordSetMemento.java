package nts.uk.ctx.at.function.dom.attendancerecord.item;

/**
 * The Interface SingleAttendanceRecordSetMemento.
 */
public interface SingleAttendanceRecordSetMemento {

	/**
	 * Sets the attribute.
	 *
	 * @param attribute the new attribute
	 */
	void setAttribute(SingleItemAttributes attribute);

	/**
	 * Sets the name.
	 *
	 * @param attribute the new name
	 */
	void setName(ItemName attribute);

	/**
	 * Gets the time item id.
	 *
	 * @param id the id
	 * @return the time item id
	 */
	void setTimeItemId(Integer id);
}
