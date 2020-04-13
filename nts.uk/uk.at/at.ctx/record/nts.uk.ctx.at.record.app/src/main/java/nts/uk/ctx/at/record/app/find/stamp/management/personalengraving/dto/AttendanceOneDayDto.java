package nts.uk.ctx.at.record.app.find.stamp.management.personalengraving.dto;

import lombok.Data;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.AttendanceOneDay;

/**
 * @author anhdt
 *
 */
@Data
public class AttendanceOneDayDto {
	private String date;
	private String workIn1;
	private String workOut1;

	private String workIn2;
	private String workOut2;
	
	public AttendanceOneDayDto (AttendanceOneDay domain) {
		this.date = domain.getDate().toString();
		this.workIn1 = new TimeActualStampDto(domain.getAttendance1()).getActualAfterRoundingTime();
		this.workOut1 = new TimeActualStampDto(domain.getLeavingStamp1()).getActualAfterRoundingTime();
		this.workIn1 = new TimeActualStampDto(domain.getAttendance2()).getActualAfterRoundingTime();
		this.workOut2 = new TimeActualStampDto(domain.getLeavingStamp2()).getActualAfterRoundingTime();
	}
}
