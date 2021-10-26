package nts.uk.ctx.at.request.app.command.application.holidayshipment.refactor5.command;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.refactor5.dto.DisplayInforWhenStarting;

@NoArgsConstructor
@Setter
@Getter
public class HolidayShipmentRefactor5Command {

	/** 振休申請 */
	public AbsenceLeaveAppCmd abs;
	/** 振出申請 */
	public RecruitmentAppCmd rec;
	/** 振休振出申請起動時の表示情報  */
	public DisplayInforWhenStarting displayInforWhenStarting;
	/** 代行申請か có phải người đại diện không*/
	public boolean represent;
	
	public boolean checkFlag;
	
	public boolean existAbs() {
		return this.abs != null;
	}
	
	public boolean existRec() {
		return this.rec != null;
	}
}
