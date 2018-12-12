package nts.uk.ctx.pr.core.app.command.wageprovision.wagetable;

import lombok.Value;
/**
 * 
 * @author HungTT
 *
 */
@Value
public class DeleteWageTableHistoryCommand {

	private String wageTableCode;
	
	private String historyId;
	
}
