package nts.uk.ctx.at.record.infra.entity.stamp.stampcard;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Entity
@Table(name = "KRCMT_STAMP_CARD")
@AllArgsConstructor
@NoArgsConstructor
public class KrcmtStampCard extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CARD_ID")
	public String cardId;
	
	@Column(name = "SID")
	public String sid;
	
	@Column(name = "CARD_NUMBER")
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
