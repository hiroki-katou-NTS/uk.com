package nts.uk.ctx.pr.core.app.command.wageprovision.wagetable;

import lombok.Value;

/**
 * 
 * @author HungTT
 *
 */

@Value
public class UpdateWageTableHistoryCommand {

	private String wageTableCode;
	
	private String historyId;

	private Integer startYm;

	private Integer endYm;

}
