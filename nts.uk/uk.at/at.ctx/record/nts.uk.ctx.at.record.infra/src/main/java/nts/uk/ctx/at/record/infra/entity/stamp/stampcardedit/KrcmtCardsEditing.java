package nts.uk.ctx.at.record.infra.entity.stamp.stampcardedit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Entity
@Table(name = "KRCMT_CARDS_EDITING")
public class KrcmtCardsEditing extends ContractUkJpaEntity{
	
	@Id
	@Column(name = "CID")
	public String cid;
	
	@Column(name = "NUMBER_OF_DIGITS")
    public int numberOfDigits;
    
	@Column(name = "EDITING_METHOD")
    public int editingMethod;

	@Override
	protected Object getKey() {
		return cid;
	}

}
