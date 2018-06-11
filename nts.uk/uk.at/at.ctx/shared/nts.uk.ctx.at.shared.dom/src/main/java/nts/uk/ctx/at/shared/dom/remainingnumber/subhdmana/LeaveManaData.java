package nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana;

import java.util.List;

import lombok.Getter;

@Getter
public class LeaveManaData {
	

	private List<LeavesManaData> leaveMana;
	private String employeeId;
	private String comDayOffID;
	private String numberDayParam;
	
	public LeaveManaData(List<LeavesManaData> leaveMana, String employeeId, String comDayOffID, String numberDayParam) {
		super();
		this.leaveMana = leaveMana;
		this.employeeId = employeeId;
		this.comDayOffID = comDayOffID;
		this.numberDayParam = numberDayParam;
	}
	
	
	
	
}
