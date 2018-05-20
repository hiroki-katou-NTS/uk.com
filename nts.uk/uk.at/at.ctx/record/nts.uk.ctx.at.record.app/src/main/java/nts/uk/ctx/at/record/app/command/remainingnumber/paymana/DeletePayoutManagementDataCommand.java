package nts.uk.ctx.at.record.app.command.remainingnumber.paymana;

import lombok.Getter;
import nts.arc.time.GeneralDate;

@Getter
public class DeletePayoutManagementDataCommand {

	String payoutId;
	
	String siD;
	
	GeneralDate dayOff;
	
}
