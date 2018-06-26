package nts.uk.ctx.at.function.app.find.attendancerecord.item;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceRecordKeyDto {
	
	/** The code. */
	private long code;
	
	/** The column index. */
	private long columnIndex;
	
	/** The position. */
	private long position;
	
	/** The export art. */
	private long exportAtr;
	
}
