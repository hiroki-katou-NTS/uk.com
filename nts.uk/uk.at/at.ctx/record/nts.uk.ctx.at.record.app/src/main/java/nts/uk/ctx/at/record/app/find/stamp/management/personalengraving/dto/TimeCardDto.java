package nts.uk.ctx.at.record.app.find.stamp.management.personalengraving.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.AttendanceOneDay;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.TimeCard;

/**
 * @author anhdt
 *
 */
@Data
public class TimeCardDto {
	private String employeeId;
	private List<AttendanceOneDayDto> listAttendances = new ArrayList<>();
	
	public TimeCardDto(TimeCard timeCard) {
		this.employeeId = timeCard.getEmployeeID();
		for (AttendanceOneDay att : timeCard.getListAttendanceOneDay()) {
			this.listAttendances.add(new AttendanceOneDayDto(att));
		}
	}
	
}
