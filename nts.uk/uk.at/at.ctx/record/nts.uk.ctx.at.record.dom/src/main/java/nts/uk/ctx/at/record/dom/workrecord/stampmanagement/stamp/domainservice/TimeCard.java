package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice;

import java.util.List;

import lombok.Value;
import nts.arc.layer.dom.objecttype.DomainValue;


/**
 * VO : タイムカード	
 * @author tutk
 *
 */
@Value
public class TimeCard implements DomainValue {
	
	private final String employeeID;
	
	private final List<AttendanceOneDay> listAttendanceOneDay;

	public TimeCard(String employeeID, List<AttendanceOneDay> listAttendanceOneDay) {
		super();
		this.employeeID = employeeID;
		this.listAttendanceOneDay = listAttendanceOneDay;
	}
	
	
}
