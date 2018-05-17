package nts.uk.ctx.at.record.app.command.remainingnumber.paymana;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@Getter
public class PayManaRemainCommand {

	@Setter
	private String payoutId;
	private String cID;
	private String sID;
	private Boolean unknownDate;
	private GeneralDate dayOff;
	private GeneralDate expiredDate;
	private int lawAtr;
	private Double occurredDays;
	private Double unUsedDays;
	private int stateAtr;
	private String subOfHDID;
	private boolean subUunknownDate;
	private GeneralDate subDayoffDate;
	private Double requiredDays;	
	private Double remainDays;
	private Boolean pickUp;
	private Boolean pause;
	
}
