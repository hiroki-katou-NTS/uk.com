package nts.uk.ctx.at.function.app.command.attendancerecord.export;

import lombok.Getter;

/**
 * The Class AttendanceRecordExportSaveCommand.
 */
@Getter
public class AttendanceRecordExportSaveCommand {

	/** The export code. */
	String exportCode;

	/** The export atr. */
	int exportAtr;

	/** The column index. */
	int columnIndex;

	/** The user atr. */
	Boolean userAtr;

	/** The upper position. */
	String upperPosition;

	/** The lowwer position. */
	String lowwerPosition;
}
