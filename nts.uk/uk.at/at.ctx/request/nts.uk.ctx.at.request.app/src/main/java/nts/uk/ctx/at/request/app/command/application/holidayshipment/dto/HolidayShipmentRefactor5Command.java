package nts.uk.ctx.at.request.app.command.application.holidayshipment.dto;

import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.dto.DisplayInforWhenStarting;

@NoArgsConstructor
@Setter
public class HolidayShipmentRefactor5Command {

	public AbsenceLeaveAppCmd abs;
	
	public RecruitmentAppCmd rec;
	
	public DisplayInforWhenStarting displayInforWhenStarting;
	
	public boolean existAbs() {
		return this.abs != null;
	}
	
	public boolean existRec() {
		return this.rec != null;
	}
}
