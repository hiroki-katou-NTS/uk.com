package nts.uk.ctx.at.schedule.infra.entity.shift.team;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
/**
 * 
 * @author sonnh1
 *
 */
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KscmtTeamPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "WKP_ID")
	public String workplaceId;

	@Column(name = "TEAM_CD")
	public String teamCode;

}
