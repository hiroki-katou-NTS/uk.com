package nts.uk.ctx.basic.app.command.organization.department;


import lombok.Getter;

/**
 *  update StartDate cuar historyId1
 *	update endDate cuar historyId2
 */

@Getter
public class UpdateStartDateandEndDateHistoryCommand {

	private String historyId1;

	private String historyId2;

	private String newStartDate;
	
	private String newEndDate;

}
