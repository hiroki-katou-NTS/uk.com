package nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.stamp.timestampsetting;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
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

	@EmbeddedId
	public KrcmtStampUsagePk pk;

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

	@Override
	protected Object getKey() {
		return this.pk;
	}

}
