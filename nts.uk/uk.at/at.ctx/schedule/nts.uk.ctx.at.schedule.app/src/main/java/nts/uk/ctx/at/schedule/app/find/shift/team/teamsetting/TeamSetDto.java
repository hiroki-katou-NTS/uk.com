package nts.uk.ctx.at.schedule.app.find.shift.team.teamsetting;

import lombok.Value;
import nts.uk.ctx.at.schedule.dom.shift.team.teamsetting.TeamSet;

@Value
public class TeamSetDto {
	private String teamCode;
	private String sId;
	private String workPlaceId;

	public static TeamSetDto fromDomain(TeamSet teamSet) {
		return new TeamSetDto(teamSet.getTeamCode().v(), teamSet.getSId(), teamSet.getWorkPlaceId());
	}
}
