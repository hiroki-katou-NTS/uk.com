package nts.uk.ctx.at.schedule.infra.entity.schedule.alarm.worktogether.ban;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.worktogether.ban.BanWorkTogether;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * KSCMT_ALCHK_BAN_WORK_TOGETHER_DTL
 * @author hiroko_miura
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCMT_ALCHK_BAN_WORK_TOGETHER_DTL")
public class KscmtAlchkBanWorkTogetherDtl extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final JpaEntityMapper<KscmtAlchkBanWorkTogetherDtl> MAPPER = new JpaEntityMapper<>(KscmtAlchkBanWorkTogetherDtl.class);
	
	@EmbeddedId
	public KscmtAlchkBanWorkTogetherDtlPk pk;
	
	@Override
	protected Object getKey() {
		return this.pk;
	}

	/**
	 * convert to entityList
	 * @param domain
	 * @param companyId
	 * @return
	 */
	public static List<KscmtAlchkBanWorkTogetherDtl> toDetailEntityList(BanWorkTogether domain, String companyId) {

		return domain.getEmpBanWorkTogetherLst().stream()
				.map(sid -> new KscmtAlchkBanWorkTogetherDtl(new KscmtAlchkBanWorkTogetherDtlPk(
						companyId
						, domain.getTargetOrg().getUnit().value
						, domain.getTargetOrg().getTargetId()
						, domain.getCode().v()
						, sid)))
				.collect(Collectors.toList());
	}
}
