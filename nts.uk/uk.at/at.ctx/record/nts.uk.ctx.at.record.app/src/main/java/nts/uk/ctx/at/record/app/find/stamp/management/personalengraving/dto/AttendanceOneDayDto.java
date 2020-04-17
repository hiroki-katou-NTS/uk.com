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
	private Integer workIn1;
	private Integer workOut1;

	private Integer workIn2;
	private Integer workOut2;
	
	public AttendanceOneDayDto (AttendanceOneDay domain) {
		this.date = domain.getDate().toString();
		this.workIn1 = new TimeActualStampDto(domain.getAttendance1()).getStampTimeWithDay();
		this.workOut1 = new TimeActualStampDto(domain.getLeavingStamp1()).getStampTimeWithDay();
		this.workIn2 = new TimeActualStampDto(domain.getAttendance2()).getStampTimeWithDay();
		this.workOut2 = new TimeActualStampDto(domain.getLeavingStamp2()).getStampTimeWithDay();
	}
}
