package nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.support;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support.SupportCardEdit;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 応援カード編集設定 entity
 * @author nws_namnv2
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCMT_SUPPORT_CARD_EDIT")
public class KrcmtSupportCardEdit extends ContractUkJpaEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KrcmtSupportCardEditPk pk;
	
	// 編集方法
	@Column(name = "EDIT_METHOD")
	public int editMethod;

	@Override
	protected Object getKey() {
		return pk;
	}
	
	public static KrcmtSupportCardEdit toEntity(SupportCardEdit domain) {
		String companyId = AppContexts.user().companyId();
		KrcmtSupportCardEditPk pk = new KrcmtSupportCardEditPk(companyId);
		return new KrcmtSupportCardEdit(pk, domain.getEditMethod().value);
	}
	
}
