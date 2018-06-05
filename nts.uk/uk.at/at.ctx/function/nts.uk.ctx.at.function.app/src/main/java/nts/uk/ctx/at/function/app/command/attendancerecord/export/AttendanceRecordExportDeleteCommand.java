package nts.uk.ctx.at.function.app.command.attendancerecord.export;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class AttendanceRecordExportSaveCommand.
 *
 * @author locph
 */
@Getter
@Setter
@NoArgsConstructor
public class AttendanceRecordExportDeleteCommand {

	/**
	 * The export code.
	 */
	long exportSettingCode;

	/**
	 * The export atr.
	 */
	int exportAtr;

	/**
	 * The column index.
	 */
	int columnIndex;

	/**
	 * The user atr.
	 */
	Boolean userAtr;

	/**
	 * The upper position.
	 */
	String upperPosition;

	/**
	 * The lowwer position.
	 */
	String lowwerPosition;
}
