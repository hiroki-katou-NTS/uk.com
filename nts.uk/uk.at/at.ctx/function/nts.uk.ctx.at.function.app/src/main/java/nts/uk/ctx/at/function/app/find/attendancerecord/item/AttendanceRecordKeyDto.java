package nts.uk.ctx.at.function.app.find.attendancerecord.item;

import lombok.Value;

@Value
public class AttendanceRecordKeyDto {
	
	/** The code. */
	private String code;
	
	/** The column index. */
	private int columnIndex;
	
	/** The position. */
	private int position;
	
	/** The export art. */
	private int exportArt;
	
	

}
