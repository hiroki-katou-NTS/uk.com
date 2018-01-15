package nts.uk.ctx.at.schedule.dom.shift.team.teamsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.schedule.dom.shift.team.TeamCode;

/**
 * チーム設定
 * 
 * @author sonnh1
 *
 */
@Getter
@AllArgsConstructor
public class TeamSet extends AggregateRoot {
	/**
	 * team code
	 */
	private TeamCode teamCode;
	/**
	 * employee Id
	 */
	private String sId;
	/**
	 * workplace Id
	 */
	private String workPlaceId;

	public static TeamSet createFromJavaType(String teamCode, String sId, String workPlaceId) {
		return new TeamSet(new TeamCode(teamCode), sId, workPlaceId);
	}
}
