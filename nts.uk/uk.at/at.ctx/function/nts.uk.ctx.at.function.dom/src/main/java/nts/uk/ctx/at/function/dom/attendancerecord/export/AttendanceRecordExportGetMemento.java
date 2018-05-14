package nts.uk.ctx.at.function.dom.attendancerecord.export;

import java.util.Optional;

import nts.uk.ctx.at.function.dom.attendancerecord.item.AttendanceRecordDisplay;

/**
 * The Interface AttendanceRecordOutputGetMemento.
 */
public interface AttendanceRecordExportGetMemento {

	/**
 	 * Gets the output classification.
 	 *
 	 * @return the output classification
 	 */
 	ExportAtr getExportAtr();

	/**
	 * Gets the column index.
	 *
	 * @return the column index
	 */
	int getColumnIndex();

	/**
	 * Checks if is use atr.
	 *
	 * @return the boolean
	 */
	Boolean isUseAtr();

	/**
	 * Gets the upper stage output.
	 *
	 * @return the upper stage output
	 */
	Optional<AttendanceRecordDisplay> getUpperPosition();

	/**
	 * Gets the lower stage output.
	 *
	 * @return the lower stage output
	 */
	Optional<AttendanceRecordDisplay> getLowerPosition();
}
