package nts.uk.ctx.at.schedule.app.find.shift.team;

import lombok.Value;
import nts.uk.ctx.at.schedule.app.find.shift.specificdayset.company.CompanySpecificDateDto;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.company.CompanySpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.team.Team;

@Value
public class TeamDto {
	private String workPlaceId;
	private String teamCode;
	private String teamName;

	public static TeamDto fromDomain(Team domain) {
		return new TeamDto(domain.getWorkPlaceId(), domain.getTeamCode().v(), domain.getTeamName().v());
	}
}
