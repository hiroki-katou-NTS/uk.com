package nts.uk.ctx.at.record.infra.entity.stamp.stampcard;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

//@Entity
//@Table(name = "KRCMT_STAMP_CARD")
public class KrcmtStamcard {
	
	//@Id
	//@Column(name = "STAMP_CARD_ID")
	public String cardId;
	
	//@Column(name = "SID")
	public String sid;
	
	//@Column(name = "STAMP_NUMBER")
	public String stampNumber;

	//@Column(name = "REGISTER_DATE")
    public GeneralDate registerDate;
	

	//@Override
	protected Object getKey() {
		return cardId;
	}

}
