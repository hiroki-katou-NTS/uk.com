package nts.uk.ctx.at.schedule.dom.shift.team;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * チーム
 * 
 * @author sonnh1
 *
 */
@Getter
@AllArgsConstructor
public class Team extends AggregateRoot {
	/**
	 *  職場ID
	 */
	private String workPlaceId;
	/**
	 *  コード
	 */
	private TeamCode teamCode;
	/**
	 *  名称
	 */
	private TeamName teamName;

	public static Team createFromJavaType(String workPlaceId, String teamCode, String teamName) {
		return new Team(workPlaceId, new TeamCode(teamCode), new TeamName(teamName));
	}
}
