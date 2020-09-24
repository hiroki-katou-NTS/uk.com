package nts.uk.ctx.at.schedule.infra.entity.schedule.alarm.workmethodrelationship;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Entity
@Table(name = "KSCMT_ALCHK_WORK_CONTEXT_ORG")
public class KscmtAlchkWorkContextOrg  extends ContractUkJpaEntity {
	
	@EmbeddedId
	public KscmtAlchkWorkContextOrgPk pk;

	@Column(name = "PROHIBIT_ATR")
	public int specifiedMethod;
	
	@Column(name = "CURRENT_WORK_ATR")
	public int currentWorkingMethod;

	@Override
	protected Object getKey() {
		return pk;
	}

}
