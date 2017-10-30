package nts.uk.ctx.at.schedule.app.find.shift.team;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.dom.shift.team.TeamRepository;
/**
 * 
 * @author Trung Tran
 *
 */
@Stateless
public class TeamFinder {
	@Inject
	TeamRepository teamRepository;

	public List<TeamDto> getTeamByWorkPlace(String workplaceId) {
		return teamRepository.getTeamByWorkPlace(workplaceId).stream().map(item -> TeamDto.fromDomain(item))
				.collect(Collectors.toList());
	}
}
