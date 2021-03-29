package nts.uk.ctx.at.schedule.app.command.schedule.alarm.workmethodrelationship.company;

import lombok.Getter;

import java.util.List;

@Getter
public class UpdateWorkingRelationshipCmpCommand {

	//D7_2
	private int typeWorkMethod;

	//D7_7
	private String workTimeCode;

	//D8_3
	private int specifiedMethod;

	//D10
	private int typeOfWorkMethods;

	//D12
	private List<String> workMethods;

}
