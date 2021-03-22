package nts.uk.ctx.at.function.app.command.attendancerecord.item;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author tuannt-nws
 *
 */
@AllArgsConstructor
@Getter
@Setter
public class CalculateAttendanceRecordDeleteCommand {

	/** The layout id. */
	private String layoutId;
	
	/** The export setting code. */
	private int exportSettingCode;

	/** The export atr. */
	private int exportAtr;

	/** The column index. */
	private int columnIndex;

	/** The position. */
	private int position;

	/** The time item ids. */
	private List<TimeItemDto> timeItems;

	/** The attribute. */
	private int attribute;
	
	/** The name. */
	private String name;

}
