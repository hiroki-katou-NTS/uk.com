package nts.uk.ctx.at.function.app.command.attendancerecord.item;

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
public class TimeItemDto {
	
	/** The formula type. */
	private int formulaType;
	
	/** The time item id. */
	private int timeItemId;

	/**
	 * Instantiates a new time item dto.
	 */
	public TimeItemDto() {
		super();
	}
	

}
