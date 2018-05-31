package nts.uk.ctx.at.function.app.command.attendancerecord.export;

import lombok.Getter;
import lombok.Setter;

/**
 * @author locph
 * The Class AttendanceRecordExportSaveCommand.
 */
@Getter
@Setter
public class AttendanceRecordExportDeleteCommand {

	/** The export code. */
	long exportSettingCode;

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
