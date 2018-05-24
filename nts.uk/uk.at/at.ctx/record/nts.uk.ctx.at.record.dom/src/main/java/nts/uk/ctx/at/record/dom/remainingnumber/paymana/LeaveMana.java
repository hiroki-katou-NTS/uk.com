package nts.uk.ctx.at.record.dom.remainingnumber.paymana;

import lombok.Data;

@Data
public class LeaveMana {
	
	private String leaveManaID;
	private String dayOff;
	private String remainDays;
	
	public LeaveMana(String leaveManaID, String dayOff, String remainDays) {
		super();
		this.leaveManaID = leaveManaID;
		this.dayOff = dayOff;
		this.remainDays = remainDays;
	}

	
	
}
