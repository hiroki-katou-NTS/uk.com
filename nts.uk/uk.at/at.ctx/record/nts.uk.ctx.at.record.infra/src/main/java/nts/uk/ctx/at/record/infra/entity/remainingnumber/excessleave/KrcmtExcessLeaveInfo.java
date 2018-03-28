package nts.uk.ctx.at.record.infra.entity.remainingnumber.excessleave;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name = "KRCMT_EXCESS_LEAVE_INFO")
public class KrcmtExcessLeaveInfo extends UkJpaEntity{

    @Column(name = "CID")
    public String cID;
	
	@Id
    @Column(name = "SID")
    public String employeeId;
	
	@Column(name="USE_ATR")
	public int useAtr;
	
	@Column(name="OCCURRENCE_UNIT")
	public int occurrenceUnit;
	
	@Column(name ="PAYMENT_METHOD")
	public int paymentMethod;

	@Override
	protected Object getKey() {
		return employeeId;
	}

}
