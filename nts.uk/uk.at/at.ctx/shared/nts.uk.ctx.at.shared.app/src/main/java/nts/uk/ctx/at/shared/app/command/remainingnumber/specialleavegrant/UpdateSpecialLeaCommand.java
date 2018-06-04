package nts.uk.ctx.at.shared.app.command.remainingnumber.specialleavegrant;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@Getter
@Setter
@Data
public class UpdateSpecialLeaCommand {

	private String sid;
	private String specialId;
	private String cId;
	private int specialLeaCode;
	private GeneralDate grantDate;
	private GeneralDate deadlineDate;
	private int expStatus;
	private int registerType;
	private int numberDayGrant;
	private int timeGrant;
	private double numberDayUse;
	private int timeUse;
	private double useSavingDays;
	private int numberDaysOver;
	private int timeOver;
	private double numberDayRemain;
	private int timeRemain;

}
