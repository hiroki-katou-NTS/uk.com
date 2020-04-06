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
	private TimeActualStampDto attendance1;
	private TimeActualStampDto leavingStamp1;

	private TimeActualStampDto attendance2;
	private TimeActualStampDto leavingStamp2;
	
	public AttendanceOneDayDto (AttendanceOneDay domain) {
		this.date = domain.getDate().toString();
		this.attendance1 = new TimeActualStampDto(domain.getAttendance1());
		this.leavingStamp1 = new TimeActualStampDto(domain.getLeavingStamp1());
		this.attendance2 = new TimeActualStampDto(domain.getAttendance2());
		this.leavingStamp2 = new TimeActualStampDto(domain.getLeavingStamp2());
	}
}
