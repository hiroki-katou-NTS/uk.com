package nts.uk.ctx.pr.core.app.command.itemmaster.itemsalarybd;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.core.app.command.itemmaster.itemdeductbd.DeleteItemDeductBDCommand;

@Getter
@Setter
public class DeleteItemSalaryBDCommand {
	private String itemCd;
	private String itemBreakdownCd;

	public DeleteItemDeductBDCommand toItemDeduct() {
		return new DeleteItemDeductBDCommand(itemCd, itemBreakdownCd);
	}
}
