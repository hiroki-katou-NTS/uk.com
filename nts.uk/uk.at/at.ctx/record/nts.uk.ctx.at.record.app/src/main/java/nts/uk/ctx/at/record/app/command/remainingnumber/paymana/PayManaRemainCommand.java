package nts.uk.ctx.at.record.app.command.remainingnumber.paymana;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@Getter
public class PayManaRemainCommand {

	@Setter
	protected String payoutId;
	protected String cID;
	protected String sID;
	protected boolean unknownDate;
	protected GeneralDate dayOff;
	protected GeneralDate expiredDate;
	protected int lawAtr;
	protected Double occurredDays;
	protected Double unUsedDays;
	protected int stateAtr;
}
