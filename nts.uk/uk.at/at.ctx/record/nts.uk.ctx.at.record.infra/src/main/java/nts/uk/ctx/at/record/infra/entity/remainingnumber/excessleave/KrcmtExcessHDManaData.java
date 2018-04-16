package nts.uk.ctx.at.record.infra.entity.remainingnumber.excessleave;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name = "KRCMT_EXCESS_HD_MANA_DATA")
public class KrcmtExcessHDManaData extends UkJpaEntity{
	
	@Id
	@Column(name = "EXCESS_HD_ID")
	public String id;
	
	@Column(name = "CID")
	public String cID;
	
	@Column(name = "SID")
	public String sID;
	
	@Column(name = "GRANT_DATE")
	public GeneralDate grantDate;
	
	@Column(name = "EXPIRED_DATE")
	public GeneralDate expiredDate;
	
	@Column(name = "EXPIRED_STATE")
	public int expiredState;
	
	@Column(name = "REGISTRATION_TYPE")
	public int registrationType;
	
	@Column(name = "OCCURRENCES_NUMBER")
	public int occurrencesNumber;
	
	@Column(name = "USED_NUMBER")
	public int usedNumber;
	
	@Column(name = "REMAIN_NUMBER")
	public int remainNumer;

	@Override
	protected Object getKey() {
		return this.id;
	}

}
