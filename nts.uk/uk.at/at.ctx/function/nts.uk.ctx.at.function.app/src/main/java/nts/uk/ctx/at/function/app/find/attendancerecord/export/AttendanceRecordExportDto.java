package nts.uk.ctx.at.function.app.find.attendancerecord.export;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AttendanceRecordExportDto {

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

	public AttendanceRecordExportDto() {
		super();
	}
}
