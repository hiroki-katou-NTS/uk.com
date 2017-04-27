package nts.uk.ctx.pr.report.app.payment.comparing.confirm.command;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InsertUpdatePaycompConfirmCommand {
	
	private List<InsertUpdatePaycompConfirm> lstInsertUpdatedata;
	
	private List<String> persionIDs;
	
	private int processingYMEarlier;

	private int processingYMLater;
}
