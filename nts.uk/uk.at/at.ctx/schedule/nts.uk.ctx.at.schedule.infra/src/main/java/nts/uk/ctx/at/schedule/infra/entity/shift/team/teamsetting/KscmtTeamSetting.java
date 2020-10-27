package nts.uk.ctx.at.schedule.infra.entity.shift.team.teamsetting;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
/**
 * チーム設定
 * @author sonnh1
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCMT_TEAM_SETTING")
public class KscmtTeamSetting extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public KscmtTeamSettingPK ksctTeamSetPk;

	@Column(name = "TEAM_CD")
	public String teamCode;

	@Override
	protected Object getKey() {
		return this.ksctTeamSetPk;
	}
}
