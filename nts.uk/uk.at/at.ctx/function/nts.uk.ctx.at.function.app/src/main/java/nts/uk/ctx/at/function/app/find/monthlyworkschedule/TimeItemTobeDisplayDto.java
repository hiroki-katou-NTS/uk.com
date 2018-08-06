package nts.uk.ctx.at.function.app.find.monthlyworkschedule;

import lombok.Data;

/**
 * The Class TimeItemTobeDisplayDto.
 */
@Data
public class TimeItemTobeDisplayDto {

	/** The order no. */
	private int orderNo;
	
	/** The attendance display. */
	private int attendanceDisplay;
	
	/** The attendance name. */
	private String attendanceName;
}
