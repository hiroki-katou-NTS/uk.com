package nts.uk.ctx.pereg.infra.entity.usesetting;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pereg.dom.usesetting.UserSetting;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PPEMT_NEW_ENTRY_USER_SET")
public class BpsstUserSetting extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public BpsstUserSettingPk BpsstUserSettingPk;
	// 社員コード初期値
	@Basic(optional = false)
	@Column(name = "SCD_ATR")
	public int employeeCodeType;
	// カードNO初期値
	@Basic(optional = false)
	@Column(name = "CARD_NUMBER_ATR")
	public int cardNumberType;
	// 最近の登録
	@Basic(optional = false)
	@Column(name = "RECENT_REGISTRATION_TYPE")
	public int recentRegistrationType;
	// 社員コード頭文字
	@Basic(optional = true)
	@Column(name = "SCD_LETTER")
	public String employeeCodeLetter;
	// カードNO頭文字
	@Basic(optional = true)
	@Column(name = "CARD_NUMBER_LETTER")
	public String cardNumberLetter;

	@Override
	protected Object getKey() {
		return BpsstUserSettingPk;
	}

	public void updateFromDomain(UserSetting domain) {
		this.employeeCodeType = domain.getEmpCodeValType().value;
		this.cardNumberType = domain.getCardNoValType().value;
		this.recentRegistrationType = domain.getRecentRegType().value;
		this.employeeCodeLetter = domain.getEmpCodeLetter().v();
		this.cardNumberLetter = domain.getCardNoLetter().v();

	}

}
