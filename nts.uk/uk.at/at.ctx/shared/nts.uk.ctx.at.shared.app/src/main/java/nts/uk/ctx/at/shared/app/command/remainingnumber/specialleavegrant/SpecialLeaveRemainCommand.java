package nts.uk.ctx.at.shared.app.command.remainingnumber.specialleavegrant;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@Getter
@Setter
@Data
public class SpecialLeaveRemainCommand {

	

	private String specialid;
	private String cid;
	private String sid;
	private int specialLeaCode;
	private String grantDate;
	private String deadlineDate;
	private int expStatus;
	private int registerType;
	private double numberDayGrant;
	private int timeGrant;
	private double numberDayUse;
	private int timeUse;
	private double numberDaysOver;
	private int timeOver;
	private double numberDayRemain;
	private int timeRemain;

}
