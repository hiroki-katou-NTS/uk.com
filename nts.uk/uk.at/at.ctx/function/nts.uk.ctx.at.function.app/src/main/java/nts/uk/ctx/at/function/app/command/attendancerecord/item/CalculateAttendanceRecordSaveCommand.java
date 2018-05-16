package nts.uk.ctx.at.function.app.command.attendancerecord.item;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

// TODO: Auto-generated Javadoc
/**
 * Instantiates a new calculate attendance record save command.
 */

/**
 * Instantiates a new calculate attendance record save command.
 *
 * @param exportSettingCode the export setting code
 * @param useAtr the use atr
 * @param exportAtr the export atr
 * @param columnIndex the column index
 * @param position the position
 * @param timeTimes the time times
 * @param attribute the attribute
 * @param name the name
 */
@AllArgsConstructor

/**
 * Gets the name.
 *
 * @return the name
 */

/**
 * Gets the name.
 *
 * @return the name
 */
@Getter

/**
 * Sets the name.
 *
 * @param name the new name
 */

/**
 * Sets the name.
 *
 * @param name the new name
 */
@Setter
public class CalculateAttendanceRecordSaveCommand {
	/** The export setting code. */
	private int exportSettingCode;

	/** The use atr. */
	private boolean useAtr;
	
	/** The export atr. */
	private int exportAtr;

	/** The column index. */
	private int columnIndex;

	/** The position. */
	private int position;

	/** The time items. */
	private List<TimeItemDto> timeItems;

	/** The attribute. */
	private int attribute;

	/** The name. */
	private String name;

	/**
	 * Instantiates a new calculate attendance record save command.
	 */
	public CalculateAttendanceRecordSaveCommand() {
		super();
	}
	
	
}
