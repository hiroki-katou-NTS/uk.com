package nts.uk.ctx.at.record.infra.entity.remainingnumber.paymana;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name="KRCMT_PAYOUT_MANA_DATA")
public class KrcmtPayoutManaData extends UkJpaEntity{
	
	@Id
	@Column(name="PAYOUT_ID")
	public String payoutId;
	
	@Column(name="CID")
	public String cID;
	
	@Column(name="SID")
	public String sID;
	
	@Column(name= "UNKNOWN_DATE")
	public boolean unknownDate;

	@Column(name = "DAYOFF_DATE")
	public GeneralDate dayOff;
	
	@Column(name="EXPIRED_DATE")
	public GeneralDate expiredDate;
	
	@Column(name="LAW_ATR")
	public int lawAtr;
	
	@Column(name="OCCURRENCE_DAYS")
	public Double occurredDays;
	
	@Column(name="UNUSED_DAYS")
	public Double unUsedDays;
	
	@Column(name="STATE_ATR")
	public int stateAtr;

	@Override
	protected Object getKey() {
		return this.payoutId;
	}
	
}	
	
