/**
 * 9:16:59 AM Mar 13, 2018
 */
package nts.uk.ctx.at.record.infra.entity.approvalmanagement;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * @author hungnm
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCMT_BOSS_CHECK_NOT_JOB")
public class KrcmtBossCheckNotJob extends ContractUkJpaEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public static final JpaEntityMapper<KrcmtBossCheckNotJob> MAPPER = new JpaEntityMapper<>(KrcmtBossCheckNotJob.class);

	@EmbeddedId
	public KrcmtBossCheckNotJobPK krcmtBossCheckNotJobPK;

	@Override
	protected Object getKey() {
		return this.krcmtBossCheckNotJobPK;
	}
}
