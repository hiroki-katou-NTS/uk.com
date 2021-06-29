package nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.support;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support.SupportCard;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
/**
 * 応援カード entity
 * @author laitv
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCMT_SUPPORT_CARD")
public class KrcmtSupportCard extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcmtSupportCardPk pk;

	// 	職場ID
	@Column(name = "WORKPLACE_ID")
	public String workPlaceId;

	@Override
	protected Object getKey() {
		return pk;
	}
	
	public static KrcmtSupportCard convert(SupportCard domain) {
		
		KrcmtSupportCard entity = new KrcmtSupportCard();
		KrcmtSupportCardPk pk = new KrcmtSupportCardPk(domain.getCid().toString(), domain.getSupportCardNumber().v());
		entity.pk = pk;
		entity.workPlaceId = domain.getWorkplaceId().toString(); 		
		return entity;
	}
	
}
