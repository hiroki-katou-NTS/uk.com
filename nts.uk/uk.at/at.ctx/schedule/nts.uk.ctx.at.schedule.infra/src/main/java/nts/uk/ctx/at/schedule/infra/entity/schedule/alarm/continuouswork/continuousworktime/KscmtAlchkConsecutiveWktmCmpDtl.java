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
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveworktime.MaxDaysOfContinuousWorkTimeCompany;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * KSCMT_ALCHK_CONSECUTIVE_WKTM_CMP_DTL
 * @author hiroko_miura
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "KSCMT_ALCHK_CONSECUTIVE_WKTM_CMP_DTL")
public class KscmtAlchkConsecutiveWktmCmpDtl extends ContractUkJpaEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public static final JpaEntityMapper<KscmtAlchkConsecutiveWktmCmpDtl> MAPPER = new JpaEntityMapper<>(KscmtAlchkConsecutiveWktmCmpDtl.class);
	
	@EmbeddedId
	public KscmtAlchkConsecutiveWktmCmpDtlPk pk;
	

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
	public static List<KscmtAlchkConsecutiveWktmCmpDtl> toDetailEntityList (String companyId, MaxDaysOfContinuousWorkTimeCompany domain) {
		
		return domain.getMaxDaysContiWorktime().getWorkTimeCodes().stream()
				.map(wktmCd -> new KscmtAlchkConsecutiveWktmCmpDtl(new KscmtAlchkConsecutiveWktmCmpDtlPk(
						companyId
						, domain.getCode().v()
						, wktmCd.v())))
				.collect(Collectors.toList());
	}
}
