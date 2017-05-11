package nts.uk.ctx.at.record.app.find.divergencetime;

import lombok.Value;
import nts.uk.ctx.at.record.dom.divergencetime.AttendanceType;
@Value
public class AttendanceTypeDto {
	/*会社ID*/
	private String companyId;
	/*勤怠項目ID*/
	private int attendanceItemId;
	/*勤怠項目名称*/
	private int attendanceItemType;
	
	public static AttendanceTypeDto fromDomain(AttendanceType domain){
		return new AttendanceTypeDto(domain.getCompanyId(),
					domain.getAttendanceItemId(),
					domain.getAttendanceItemType().value);
	}
}
