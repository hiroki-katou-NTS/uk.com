package nts.uk.ctx.at.function.dom.attendancerecord.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.function.dom.attendancerecord.export.ExportAtr;

/**
 * The Class SingleItemInAttendanceRecord.
 */
// 出勤簿の単一の項目

@Getter
@Setter
@AllArgsConstructor
public class SingleAttendanceRecord extends DomainObject implements AttendanceRecordDisplay {

	/** The attribute. */
	// 属性
	private SingleItemAttributes attribute;

	/** The name. */
	// 名称
	private ItemName name;

	/** The added item. */
	// 勤怠項目ID
	private Integer timeItemId;
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attribute == null) ? 0 : attribute.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((timeItemId == null) ? 0 : timeItemId.hashCode());
		return result;
	}

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
		SingleAttendanceRecord other = (SingleAttendanceRecord) obj;
		if (attribute != other.attribute)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (timeItemId == null) {
			if (other.timeItemId != null)
				return false;
		} else if (!timeItemId.equals(other.timeItemId))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.function.dom.attendancerecord.item.AttendanceRecordDisplay#
	 * switchOutputItem(nts.uk.ctx.at.function.dom.attendancerecord.output.
	 * OutputAtr, int)
	 */
	@Override
	public int switchExportItem(ExportAtr outputAtr, int number) {
		// No Coding
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.function.dom.attendancerecord.item.AttendanceRecordDisplay#
	 * switchOutputRegistrationDestination(nts.uk.ctx.at.function.dom.
	 * attendancerecord.output.OutputAtr, int)
	 */
	@Override
	public int switchExportRegistrationDestination(ExportAtr outputAtr, int number) {
		// No Coding
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.function.dom.attendancerecord.item.AttendanceRecordDisplay#
	 * returnOutputItemName()
	 */
	@Override
	public int returnExportItemName() {
		// No Coding
		return 0;
	}

	/**
	 * Instantiates a new single attendance record.
	 *
	 * @param attribute
	 *            the attribute
	 * @param name
	 *            the name
	 * @param timeItemId
	 *            the time item id
	 */
	public SingleAttendanceRecord(SingleAttendanceRecordGetMemento memento) {
		super();
		this.attribute = memento.getAttribute();
		this.name = memento.getName();
		this.timeItemId = memento.getTimeItemId();

	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(SingleAttendanceRecordSetMemento memento) {
		memento.setAttribute(this.attribute);
		memento.setName(this.name);
		memento.setTimeItemId(this.timeItemId);
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
