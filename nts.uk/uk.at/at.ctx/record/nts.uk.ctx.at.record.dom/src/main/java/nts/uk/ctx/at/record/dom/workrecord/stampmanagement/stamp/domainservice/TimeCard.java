package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainValue;


/**
 * VO : タイムカード	
 * @author tutk
 *
 */

public class TimeCard implements DomainValue {
	/**
	 * 社員ID
	 */
	@Getter
	private final String employeeID;
	
	/**
	 * 日々の実績
	 */
	@Getter
	private final List<AttendanceOneDay> listAttendanceOneDay;

	public TimeCard(String employeeID, List<AttendanceOneDay> listAttendanceOneDay) {
		super();
		this.employeeID = employeeID;
		this.listAttendanceOneDay = listAttendanceOneDay;
	}
	
	
}
