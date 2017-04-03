package nts.uk.ctx.pr.core.app.command.itemmaster.itemdeductbd;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteItemDeductBDCommand {
	private String itemCode;
	private String itemBreakdownCode;

	public DeleteItemDeductBDCommand(String itemCode, String itemBreakdownCode) {
		super();
		this.itemCode = itemCode;
		this.itemBreakdownCode = itemBreakdownCode;
	}

}
