package nts.uk.ctx.at.shared.app.command.remainingnumber;

import lombok.Data;

@Data
public class DeletePaymentManagementDataCommand {

	/* field 振出データID */
	private String payoutId;
	
	/* field 振休データID */
	private String subOfHDID;
	
}
