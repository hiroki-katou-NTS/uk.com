package nts.uk.ctx.at.record.dom.remainingnumber.subhdmana;

import java.util.List;

import lombok.Getter;

@Getter
public class LeaveManaData {
	

	private List<LeavesManaData> leaveMana;
	private String employeeId;
	private String comDayOffID;
	
	public LeaveManaData(List<LeavesManaData> leaveMana, String employeeId, String comDayOffID) {
		super();
		this.leaveMana = leaveMana;
		this.employeeId = employeeId;
		this.comDayOffID = comDayOffID;
	}
	
	
	
	
}
