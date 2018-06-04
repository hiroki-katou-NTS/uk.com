package nts.uk.ctx.at.record.infra.entity.remainingnumber.publicholiday;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name ="KRCMT_PUB_HOLIDAY_REMAIN")
public class KrcmtPubHolidayRemain extends UkJpaEntity {
	
	@Column(name = "CID")
    public String cid;
	
	@Id
    @Column(name = "SID")
    public String employeeId;
	
	@Column(name = "REMAIN_NUMBER")
    public BigDecimal remainNumber;
	
	@Override
	protected Object getKey() {
		return this.employeeId;
	}

}
