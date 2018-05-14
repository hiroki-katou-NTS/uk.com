package nts.uk.ctx.at.function.app.find.attendancerecord.item;

import lombok.Value;

@Value
public class AttendanceRecordKeyDto {
	
	/** The code. */
	private long code;
	
	/** The column index. */
	private long columnIndex;
	
	/** The position. */
	private long position;
	
	/** The export art. */
	private long exportArt;
	
	

}
