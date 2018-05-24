package nts.uk.ctx.at.record.dom.remainingnumber.paymana;

import java.util.List;

import lombok.Getter;

@Getter
public class LeaveManagementData {
	

	private List<LeaveMana> leaveMana;
	private String employeeId;
	private String comDayOffID;
	
	public LeaveManagementData(List<LeaveMana> leaveMana, String employeeId, String comDayOffID) {
		super();
		this.leaveMana = leaveMana;
		this.employeeId = employeeId;
		this.comDayOffID = comDayOffID;
	}
	
	
	
	
}
