package nts.uk.ctx.at.record.infra.entity.stamp.stampcard;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name = "KRCMT_STAMP_CARD")
public class KrcmtStamcard extends UkJpaEntity{
	
	@Id
	@Column(name = "CARD_ID")
	public String cardId;
	
	@Column(name = "SID")
	public String sid;
	
	@Column(name = "CARD_NO")
	public String cardNo;

	@Column(name = "REGISTER_DATE")
    public GeneralDate registerDate;
    
	@Column(name = "CONTRACT_CODE")
    public String contractCd;
	

	@Override
	protected Object getKey() {
		return cardId;
	}

}
