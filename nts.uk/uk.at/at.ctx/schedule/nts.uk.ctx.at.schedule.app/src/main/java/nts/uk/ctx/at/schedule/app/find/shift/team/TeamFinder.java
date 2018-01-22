package nts.uk.ctx.at.schedule.app.find.shift.team;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.text.StringUtil;
import nts.uk.ctx.at.schedule.dom.shift.team.TeamRepository;
/**
 * 
 * @author Trung Tran
 *
 */
@Stateless
public class TeamFinder {
	@Inject
	private TeamRepository teamRepository;

	/**
	 * Find all team by workplace
	 * @param workplaceId
	 * @return
	 */
	public List<TeamDto> getTeamByWorkPlace(String workplaceId) {
		if (StringUtil.isNullOrEmpty(workplaceId, true)) {
			return Collections.emptyList();
		}
		return teamRepository.getTeamByWorkPlace(workplaceId).stream().map(item -> TeamDto.fromDomain(item))
				.collect(Collectors.toList());
	}
}
