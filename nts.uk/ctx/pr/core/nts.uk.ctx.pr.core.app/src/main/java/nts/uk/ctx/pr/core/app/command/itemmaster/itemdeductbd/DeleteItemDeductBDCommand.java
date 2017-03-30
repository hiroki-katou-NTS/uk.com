package nts.uk.ctx.pr.core.app.command.itemmaster.itemdeductbd;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteItemDeductBDCommand {
	private String itemCd;
	private String itemBreakdownCd;

	public DeleteItemDeductBDCommand(String itemCd, String itemBreakdownCd) {
		super();
		this.itemCd = itemCd;
		this.itemBreakdownCd = itemBreakdownCd;
	}

}
