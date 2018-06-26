package nts.uk.ctx.at.function.dom.attendancerecord.export;

import java.util.Optional;

import nts.uk.ctx.at.function.dom.attendancerecord.item.AttendanceRecordDisplay;

/**
 * The Interface AttendanceRecordOutputSetMemento.
 */
public interface AttendanceRecordExportSetMemento {

	/**
	 * Sets the output classification.
	 *
	 * @param outputAtr the new output classification
	 */
	void setExportClassification(ExportAtr exportAtr);

	/**
	 * Sets the column index.
	 *
	 * @param index the new column index
	 */
	void setColumnIndex(int index);

	/**
	 * Checks if is use atr.
	 *
	 * @param isUseAtr the is use atr
	 */
	void isUseAtr(Boolean isUseAtr);

	/**
	 * Gets the upper stage output.
	 *
	 * @param attendanceRecDisplay the attendance rec display
	 * @return the upper stage output
	 */
	void setUpperPosition(Optional<AttendanceRecordDisplay> attendanceRecDisplay);

	/**
	 * Gets the lower stage output.
	 *
	 * @param attendanceRecDisplay the attendance rec display
	 * @return the lower stage output
	 */
	void setLowerPosition(Optional<AttendanceRecordDisplay> attendanceRecDisplay);

}
