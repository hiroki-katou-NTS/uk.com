package nts.uk.ctx.at.schedule.infra.entity.shift.team;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * チーム
 * 
 * @author sonnh1
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCMT_TEAM")
public class KscmtTeam extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KscmtTeamPK kscmtTeamPk;

	@Column(name = "TEAM_NAME")
	public String teamName;

	@Override
	protected Object getKey() {
		return this.kscmtTeamPk;
	}

}
