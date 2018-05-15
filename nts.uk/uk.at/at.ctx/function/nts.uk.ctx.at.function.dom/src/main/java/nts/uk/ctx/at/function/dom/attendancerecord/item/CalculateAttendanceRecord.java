package nts.uk.ctx.at.function.dom.attendancerecord.item;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.function.dom.attendancerecord.export.ExportAtr;

/**
 * The Class CalculateItemInAttendanceRecord.
 */
// 出勤簿の算出する項目
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CalculateAttendanceRecord extends DomainObject implements AttendanceRecordDisplay {

	/** The attribute. */
	// 属性
	private CalculateItemAttributes attribute;

	/** The name. */
	// 名称
	private ItemName name;

	/** The added item. */
	// 加算する項目
	private List<Integer> addedItem;

	/** The subtracted item. */
	// 減算する項目
	private List<Integer> subtractedItem;

	/**
	 * Instantiates a new calculate item in attendance record.
	 *
	 * @param memento
	 *            the memento
	 */
	public CalculateAttendanceRecord(CalculateAttendanceRecordGetMemento memento) {
		super();
		this.attribute = memento.getAttribute();
		this.name = memento.getName();
		this.addedItem = memento.getAddedItem();
		this.subtractedItem = memento.getSubtractedItem();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(CalculateAttendanceRecordSetMemento memento) {
		memento.setAttribute(this.attribute);
		memento.setName(this.name);
		memento.setAddedItem(this.addedItem);
		memento.setSubtractedItem(this.subtractedItem);
	}

	/**
	 * Calculate value.
	 *
	 * @return the int
	 */
	public int calculateValue() {
		return 0;
		// No Code
	}

	/**
	 * Hash code.
	 *
	 * @return the int
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((addedItem == null) ? 0 : addedItem.hashCode());
		result = prime * result + ((attribute == null) ? 0 : attribute.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((subtractedItem == null) ? 0 : subtractedItem.hashCode());
		return result;
	}

	/**
	 * Equals.
	 *
	 * @param obj
	 *            the obj
	 * @return true, if successful
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CalculateAttendanceRecord other = (CalculateAttendanceRecord) obj;
		if (addedItem == null) {
			if (other.addedItem != null)
				return false;
		} else if (!addedItem.equals(other.addedItem))
			return false;
		if (attribute != other.attribute)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (subtractedItem == null) {
			if (other.subtractedItem != null)
				return false;
		} else if (!subtractedItem.equals(other.subtractedItem))
			return false;
		return true;
	}

	/**
	 * Switch export item.
	 *
	 * @param outputAtr
	 *            the output atr
	 * @param number
	 *            the number
	 * @return the int
	 */
	@Override
	public int switchExportItem(ExportAtr outputAtr, int number) {
		// No Coding
		return 0;
	}

	/**
	 * Switch export registration destination.
	 *
	 * @param outputAtr
	 *            the output atr
	 * @param number
	 *            the number
	 * @return the int
	 */
	@Override
	public int switchExportRegistrationDestination(ExportAtr outputAtr, int number) {
		// No Coding
		return 0;
	}

	/**
	 * Return export item name.
	 *
	 * @return the int
	 */
	@Override
	public int returnExportItemName() {
		// No Coding
		return 0;
	}

	/**
	 * Gets the name display.
	 *
	 * @return the name display
	 */
	@Override
	public String getNameDisplay() {
		return this.name.toString();
	}

}
