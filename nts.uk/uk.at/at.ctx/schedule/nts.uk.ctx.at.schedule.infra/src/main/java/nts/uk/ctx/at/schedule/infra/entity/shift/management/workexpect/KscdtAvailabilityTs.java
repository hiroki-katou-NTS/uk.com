package nts.uk.ctx.at.schedule.infra.entity.shift.management.workexpect;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Entity
@Table(name = "KSCDT_AVAILABILITY_TS")
@AllArgsConstructor
public class KscdtAvailabilityTs extends ContractUkJpaEntity{

	@Column(name = "CID")
	public String companyId;
	
	@EmbeddedId
	public KscdtAvailabilityTsPk pk;

	@Override
	protected Object getKey() {
		return this.pk;
	}
}
