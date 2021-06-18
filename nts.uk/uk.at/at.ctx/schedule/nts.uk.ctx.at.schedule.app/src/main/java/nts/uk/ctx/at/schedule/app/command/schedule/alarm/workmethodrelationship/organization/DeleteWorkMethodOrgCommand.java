package nts.uk.ctx.at.schedule.app.command.schedule.alarm.workmethodrelationship.organization;

import lombok.Getter;

@Getter
public class DeleteWorkMethodOrgCommand {

	//E1_3
	private int unit;

	//E1_3
	private String workplaceId;

	//E1_3
	private String workplaceGroupId;

	//E4_2
	private int typeWorkMethod;

	//E4_7
	private String workTimeCode;

}
