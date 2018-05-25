package nts.uk.ctx.at.record.app.find.remainingnumber.subhdmana.dto;

import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
public class LeaveManaDto {
	
	private GeneralDate dateHoliday;
	private String numberDay;
	private boolean usedDay;
	private String leaveManaID;
	
	public LeaveManaDto(GeneralDate dateHoliday, String numberDay, boolean usedDay, String leaveManaID) {
		super();
		this.dateHoliday = dateHoliday;
		this.numberDay = numberDay;
		this.usedDay = usedDay;
		this.leaveManaID = leaveManaID;
	}
	
	
}
