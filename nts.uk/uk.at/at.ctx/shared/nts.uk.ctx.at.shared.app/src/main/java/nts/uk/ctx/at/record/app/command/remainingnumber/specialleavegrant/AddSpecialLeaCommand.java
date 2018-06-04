package nts.uk.ctx.at.record.app.command.remainingnumber.specialleavegrant;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@Getter
@Setter
@Data
public class AddSpecialLeaCommand {

	private String sid;
	private int specialLeaCode;
	private GeneralDate grantDate;
	private GeneralDate deadlineDate;
	private int expStatus;
	private int registerType;
	private double numberDayGrant;
	private int timeGrant;
	private double numberDayUse;
	private int timeUse;
	private double useSavingDays;
	private double numberDaysOver;
	private int timeOver;
	private double numberDayRemain;
	private int timeRemain;

}
