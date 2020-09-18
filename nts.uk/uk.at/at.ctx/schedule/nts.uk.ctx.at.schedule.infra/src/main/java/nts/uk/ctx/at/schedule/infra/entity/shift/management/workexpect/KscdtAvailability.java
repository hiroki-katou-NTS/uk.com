package nts.uk.ctx.at.schedule.infra.entity.shift.management.workexpect;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Entity
@Table(name = "KSCDT_AVAILABILITY")
@AllArgsConstructor
public class KscdtAvailability extends ContractUkJpaEntity{
	
	@EmbeddedId
	public KscdtAvailabilityPk pk;
	
	@Column(name = "MEMO")
	public String memo;
	
	@Column(name = "SPECIFIED_METHOD")
	public int method;

	@Override
	protected Object getKey() {
		return pk;
	}

}
