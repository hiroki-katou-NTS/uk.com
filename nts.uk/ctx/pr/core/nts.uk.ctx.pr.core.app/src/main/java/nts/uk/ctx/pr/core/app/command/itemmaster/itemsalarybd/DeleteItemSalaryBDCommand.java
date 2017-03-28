package nts.uk.ctx.pr.core.app.command.itemmaster.itemsalarybd;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteItemSalaryBDCommand {
	private String itemCd;
	private String itemBreakdownCd;
}
