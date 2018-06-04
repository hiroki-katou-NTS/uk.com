package nts.uk.ctx.at.record.app.command.remainingnumber.paymana;

import lombok.Getter;
import nts.arc.time.GeneralDate;

@Getter
public class DeletePayoutManagementDataCommand {

	private String payoutId;
	
	private String employeeId;
	
	private GeneralDate dayOff;
	
	private GeneralDate dayoffDate;
	
}
