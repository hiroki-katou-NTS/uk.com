package nts.uk.ctx.at.shared.app.command.remainingnumber;

import java.util.List;

import lombok.Data;

@Data
public class DeletePaymentManagementDataCommand {

	/* field 振出データID */
	private List<String> payoutId;
	
	/* field 振休データID */
	private List<String> subOfHDID;
	
}
