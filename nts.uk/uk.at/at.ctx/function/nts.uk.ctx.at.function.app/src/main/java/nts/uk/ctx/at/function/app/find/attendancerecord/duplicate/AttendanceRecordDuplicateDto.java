package nts.uk.ctx.at.function.app.find.attendancerecord.duplicate;

import lombok.Data;

/**
 * The Class AttendanceRecordDuplicateDto.
 */
@Data
public class AttendanceRecordDuplicateDto {
	
	/** The code. */
	public String code;
	
	/** The name. */
	public String name;
	
	/** The selected type. */
	public int selectedType;
	
	/** The duplicate code. */
	public String duplicateCode;
	
	/** The duplicate name. */
	public String duplicateName;
	
	/** The layout id. */
	public String layoutId;
	
}
