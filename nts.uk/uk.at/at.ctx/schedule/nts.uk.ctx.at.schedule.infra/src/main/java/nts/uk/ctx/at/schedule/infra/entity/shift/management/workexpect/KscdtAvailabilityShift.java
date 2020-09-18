package nts.uk.ctx.at.schedule.infra.entity.shift.management.workexpect;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Entity
@Table(name = "KSCDT_AVAILABILITY_SHIFT")
@AllArgsConstructor
public class KscdtAvailabilityShift extends ContractUkJpaEntity{
	
	@Column(name = "CID")
	public String companyId;
	
	@Getter
	@EmbeddedId
	public KscdtAvailabilityShiftPk pk;
	
	@Override
	protected Object getKey() {
		return this.pk;
	}

}
