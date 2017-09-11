package nts.uk.ctx.at.function.pub.attendancetype;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AttendanceTypePubDto {
	public String companyId;
	
	public int attendanceItemId;
	
	public int attendanceItemType;
}
