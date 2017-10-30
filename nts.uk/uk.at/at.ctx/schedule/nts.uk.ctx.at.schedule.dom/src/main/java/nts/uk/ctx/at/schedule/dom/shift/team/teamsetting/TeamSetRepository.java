package nts.uk.ctx.at.schedule.dom.shift.team.teamsetting;

import java.util.List;

/**
 * interface team setting
 * 
 * @author Trung Tran
 *
 */
public interface TeamSetRepository {
	/**
	 * get all team setting
	 * 
	 * @return
	 */
	List<TeamSet> getAllTeamSet();

	/**
	 * add team setting
	 */
	void addTeamSet(TeamSet teamSet);

	/**
	 * remove team setting
	 */
	 void removeTeamSetByTeamCode(String workPlace, String teamCode);

	/**
	 * remove list team setting
	 */
	void removeListTeamSet(List<String> employees, String workPlace);

	/**
	 * remove team setting
	 */
	void removeTeamSet(String employees, String workPlace);
}
