package nts.uk.ctx.at.shared.app.command.remainingnumber.specialleavegrant;

import java.math.BigDecimal;

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
	private BigDecimal numberDayGrant;
	private BigDecimal timeGrant;
	private BigDecimal numberDayUse;
	private BigDecimal timeUse;
	private BigDecimal useSavingDays;
	private BigDecimal numberDaysOver;
	private BigDecimal timeOver;
	private BigDecimal numberDayRemain;
	private BigDecimal timeRemain;

}
