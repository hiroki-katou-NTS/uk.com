package nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.stamp.timestampsetting;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.stamp.application.SettingsUsingEmbossing;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 
 * @author sonnlb
 * 
 *         打刻機能の利用設定
 */

@Entity
@NoArgsConstructor
@Table(name = "KRCMT_STAMP_USAGE")
public class KrcmtStampUsage extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 会社ID */
	@Id
	@Column(name = "CID")
	public String companyId;

	/**
	 * 氏名選択
	 */
	@Column(name = "NAME_SELECTION")
	public int nameSelection;

	/**
	 * 指認証打刻
	 */
	@Column(name = "FINGER_AUTHENTICATION")
	public int fingerAuthentication;

	/**
	 * ICカード打刻
	 */

	@Column(name = "IC_CARD_STAMP")
	public int ICCardStamp;

	/**
	 * 個人打刻
	 */
	@Column(name = "PERSON_STAMP")
	public int personStamp;

	/**
	 * ポータル打刻
	 */

	@Column(name = "PORTAL_STAMP")
	public int portalStamp;

	/**
	 * スマホ打刻
	 */
	@Column(name = "SMART_PHONE_STAMP")
	public int smartPhoneStamp;
	
	/**
	 * RICOH複写機打刻
	 */
	@Column(name = "RICOH_STAMP")
	public int ricohStamp;

	@Override
	protected Object getKey() {
		return this.companyId;
	}
	
	@PreUpdate
    private void setUpdateContractInfo() {
		this.contractCd = AppContexts.user().contractCode();
	}
	
	public void update(SettingsUsingEmbossing domain) {
		
		this.nameSelection = domain.isName_selection() ? 1 : 0;
		this.fingerAuthentication = domain.isFinger_authc() ? 1 : 0;
		this.ICCardStamp = domain.isIc_card() ? 1 : 0;
		this.personStamp = domain.isIndivition() ? 1 : 0;
		this.portalStamp = domain.isPortal() ? 1 : 0;
		this.smartPhoneStamp = domain.isSmart_phone() ? 1 : 0;
		this.ricohStamp = domain.isRicohStamp() ? 1 : 0;
	}

}
