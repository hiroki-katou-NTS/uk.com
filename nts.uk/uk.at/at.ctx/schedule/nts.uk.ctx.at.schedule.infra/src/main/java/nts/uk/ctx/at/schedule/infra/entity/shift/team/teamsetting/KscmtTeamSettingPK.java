package nts.uk.ctx.at.schedule.infra.entity.shift.team.teamsetting;

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
public class KscmtTeamSettingPK implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "SID")
	public String sId;
	
	@Column(name = "WKP_ID")
	public String workPlaceId;

}
