package nts.uk.ctx.at.shared.app.command.remainingnumber.paymana;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@Getter
public class PayManaRemainCommand {

	@Setter
	private String payoutId;
	private String employeeId;
	private GeneralDate dayOff;
	private GeneralDate expiredDate;
	private int lawAtr;
	private Double occurredDays;
	private GeneralDate subDayoffDate;
	private Double requiredDays;	
	private Double remainDays;
	private Boolean pickUp;
	private Boolean pause;
	private GeneralDate holidayDate;
	private Double subDays;
	private int closureId;
	private Boolean checkedSplit;
	private List<String> linkingDates;
	
}
