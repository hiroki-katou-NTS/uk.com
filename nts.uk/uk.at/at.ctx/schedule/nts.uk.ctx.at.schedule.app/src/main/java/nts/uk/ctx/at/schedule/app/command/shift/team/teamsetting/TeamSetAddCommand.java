package nts.uk.ctx.at.schedule.app.command.shift.team.teamsetting;

import java.util.List;

import lombok.Value;

@Value
public class TeamSetAddCommand {

	List<String> employeeCodes;
	String teamCode;
	String workPlaceId;
}
