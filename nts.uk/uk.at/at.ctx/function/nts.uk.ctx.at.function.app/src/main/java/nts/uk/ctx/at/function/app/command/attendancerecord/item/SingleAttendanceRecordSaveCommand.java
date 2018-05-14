package nts.uk.ctx.at.function.app.command.attendancerecord.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class SingleAttendanceRecordSaveCommand {

	/** The attribute. */
	private int attribute;

	/** The name. */
	private String name;

	/** The added item. */
	private int timeItemId;

	/**
	 * Instantiates a new single attendance record save command.
	 */
	public SingleAttendanceRecordSaveCommand() {
		super();
	}

}
