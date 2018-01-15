package nts.uk.ctx.at.schedule.app.find.shift.team;

import lombok.Value;
import nts.uk.ctx.at.schedule.dom.shift.team.Team;
/**
 * 
 * @author Trung Tran
 *
 */
@Value
public class TeamDto {
	private String workPlaceId;
	private String teamCode;
	private String teamName;

	public static TeamDto fromDomain(Team domain) {
		return new TeamDto(domain.getWorkPlaceId(), domain.getTeamCode().v(), domain.getTeamName().v());
	}
}
