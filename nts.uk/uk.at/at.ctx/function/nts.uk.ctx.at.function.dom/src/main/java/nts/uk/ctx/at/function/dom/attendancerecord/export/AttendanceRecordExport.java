package nts.uk.ctx.at.function.dom.attendancerecord.export;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.function.dom.attendancerecord.item.AttendanceRecordDisplay;

/**
 * The Class OutputItemForAttendanceRegister.
 */
// 出勤簿に出力する項目
@Getter
@Setter
@AllArgsConstructor
public class AttendanceRecordExport extends DomainObject {

	/** The output classification. */
	// 出力区分
	private ExportAtr exportAtr;

	/** The column index. */
	// 列番号
	private int columnIndex;

	/** The use atr. */
	// 使用区分
	private Boolean useAtr;

	/** The upper stage output. */
	// 上段出力項目
	private Optional<AttendanceRecordDisplay> upperPosition;

	/** The lower stage output. */
	// 下段出力項目
	private Optional<AttendanceRecordDisplay> lowerPosition;

	/**
	 * Instantiates a new attendance record export.
	 */
	public AttendanceRecordExport() {
		super();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(AttendanceRecordExportSetMemento memento) {
		memento.setExportClassification(this.exportAtr);
		memento.setColumnIndex(this.columnIndex);
		memento.isUseAtr(this.useAtr);
		memento.setUpperPosition(this.upperPosition);
		memento.setLowerPosition(this.lowerPosition);
	}

	/**
	 * Instantiates a new attendance record export.
	 *
	 * @param memento
	 *            the memento
	 */
	public AttendanceRecordExport(AttendanceRecordExportGetMemento memento) {
		this.exportAtr = memento.getExportAtr();
		this.columnIndex = memento.getColumnIndex();
		this.useAtr = memento.isUseAtr();
		this.upperPosition = memento.getUpperPosition();
		this.lowerPosition = memento.getLowerPosition();
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
		result = prime * result + columnIndex;
		result = prime * result + ((exportAtr == null) ? 0 : exportAtr.hashCode());
		result = prime * result + ((lowerPosition == null) ? 0 : lowerPosition.hashCode());
		result = prime * result + ((upperPosition == null) ? 0 : upperPosition.hashCode());
		result = prime * result + ((useAtr == null) ? 0 : useAtr.hashCode());
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
		AttendanceRecordExport other = (AttendanceRecordExport) obj;
		if (columnIndex != other.columnIndex)
			return false;
		if (exportAtr != other.exportAtr)
			return false;
		if (lowerPosition == null) {
			if (other.lowerPosition != null)
				return false;
		} else if (!lowerPosition.equals(other.lowerPosition))
			return false;
		if (upperPosition == null) {
			if (other.upperPosition != null)
				return false;
		} else if (!upperPosition.equals(other.upperPosition))
			return false;
		if (useAtr == null) {
			if (other.useAtr != null)
				return false;
		} else if (!useAtr.equals(other.useAtr))
			return false;
		return true;
	}

}
