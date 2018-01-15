package nts.uk.ctx.at.schedule.app.find.shift.team.teamsetting;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.dom.shift.team.teamsetting.TeamSetRepository;

@Stateless
public class TeamSetFinder {
	@Inject
	private TeamSetRepository teamSetRepos;

	public List<TeamSetDto> getAllTeamSet() {
		return teamSetRepos.getAllTeamSet().stream().map(domain -> TeamSetDto.fromDomain(domain))
				.collect(Collectors.toList());
	}

}
