package nts.uk.ctx.at.schedule.infra.entity.schedule.alarm.workmethodrelationship;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Entity
@Table(name = "KSCMT_ALCHK_WORK_CONTEXT_ORG_DTL")
public class KscmtAlchkWorkContextOrgDtl extends ContractUkJpaEntity{
	
	@EmbeddedId
	public KscmtAlchkWorkContextOrgDtlPk pk;

	@Override
	protected Object getKey() {
		return pk;
	}
	
}
