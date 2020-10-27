package nts.uk.ctx.at.record.infra.entity.workrecord.operationsetting;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Entity
@Table(name = "KRCMT_ATTENDANCE_AUT")
public class KrcmtAttendanceAut extends ContractUkJpaEntity{
	
	@EmbeddedId
	public KrcmtAttendanceAutPk pk;
	
	@Column(name = "AVAILABILITY")
	public BigDecimal availability;
	
	@Override
	protected Object getKey() {
		return pk;
	}

}
