package nts.uk.ctx.at.function.app.find.attendancetype;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AttendanceTypeDto {
	public String companyId;
	
	public int attendanceItemId;
	
	public int attendanceItemType;
}
