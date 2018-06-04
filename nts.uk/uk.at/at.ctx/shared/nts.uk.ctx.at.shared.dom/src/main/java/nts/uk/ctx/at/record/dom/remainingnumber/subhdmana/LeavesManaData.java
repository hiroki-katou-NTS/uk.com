package nts.uk.ctx.at.record.dom.remainingnumber.subhdmana;

import lombok.Data;

@Data
public class LeavesManaData {
	
	private String leaveManaID;
	private String dayOff;
	private String remainDays;
	
	public LeavesManaData(String leaveManaID, String dayOff, String remainDays) {
		super();
		this.leaveManaID = leaveManaID;
		this.dayOff = dayOff;
		this.remainDays = remainDays;
	}

	
	
}
