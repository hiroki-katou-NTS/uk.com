package nts.uk.ctx.at.function.app.command.attendancerecord.export.setting;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * The Class AttendanceRecordExportSettingDeleteCommand.
 */
@Getter
@Setter
@AllArgsConstructor
public class AttendanceRecordExportSettingDeleteCommand {

	/** The code. */
	long code;

	/** The name. */
	String name;

	/** The seal stamp. */
	List<String> sealStamp;

	/** The seal use atr. */
	Boolean sealUseAtr;

	/**
	 * Instantiates a new attendance record export setting delete command.
	 */
	public AttendanceRecordExportSettingDeleteCommand() {
		super();
	}
}
