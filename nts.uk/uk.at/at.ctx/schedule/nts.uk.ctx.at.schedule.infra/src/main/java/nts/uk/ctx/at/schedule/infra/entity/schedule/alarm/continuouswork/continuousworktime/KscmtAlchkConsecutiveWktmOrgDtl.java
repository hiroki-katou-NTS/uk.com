package nts.uk.ctx.at.schedule.infra.entity.schedule.alarm.continuouswork.continuousworktime;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveworktime.MaxDaysOfContinuousWorkTimeOrganization;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * KSCMT_ALCHK_CONSECUTIVE_WKTM_ORG_DTL
 * @author hiroko_miura
 *
 */

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "KSCMT_ALCHK_CONSECUTIVE_WKTM_ORG_DTL")
public class KscmtAlchkConsecutiveWktmOrgDtl extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final JpaEntityMapper<KscmtAlchkConsecutiveWktmOrgDtl> MAPPER = new JpaEntityMapper<>(KscmtAlchkConsecutiveWktmOrgDtl.class);
	
	@EmbeddedId
	public KscmtAlchkConsecutiveWktmOrgDtlPk pk;
	
	@Override
	protected Object getKey() {
		return this.pk;
	}
	
	
	/**
	 * convert to entityList
	 * @param companyId
	 * @param domain
	 * @return
	 */
	public static List<KscmtAlchkConsecutiveWktmOrgDtl> toDetailEntityList (String companyId, MaxDaysOfContinuousWorkTimeOrganization domain) {
		
		return domain.getMaxDaysContiWorktime().getWorkTimeCodes().stream()
				.map(wktmCd -> new KscmtAlchkConsecutiveWktmOrgDtl(new KscmtAlchkConsecutiveWktmOrgDtlPk(
						companyId
						, domain.getTargeOrg().getUnit().value
						, domain.getTargeOrg().getTargetId()
						, domain.getCode().v()
						, wktmCd.v())))
				.collect(Collectors.toList());
	}
}
