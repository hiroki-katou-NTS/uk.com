package nts.uk.ctx.at.schedule.dom.shift.team;

import java.util.List;
import java.util.Optional;

/**
 * 
 * @author sonnh1
 *
 */
public interface TeamRepository {
	/**
	 * get team by workplaceId
	 * 
	 * @param workplaceId
	 * @return
	 */
	List<Team> getTeamByWorkPlace(String workPlaceId);

	/**
	 * insert team
	 */
	void addTeam(Team team);

	/**
	 * find team by teamCode
	 */
	Optional<Team> findTeamByTeamCode(String teamCode);

	/**
	 * update team
	 */
	void updateTeam(Team team);

	/**
	 * remove team
	 */
	void removeTeam(String workPlaceId, String teamCode);

	/**
	 * find team by PK
	 */
	Optional<Team> findTeamByPK(String workPlaceId, String teamCode);
}
