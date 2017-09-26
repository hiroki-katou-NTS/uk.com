package entity.person.info.setting.user;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BPSST_USER_SET")
public class BpsstUserSetting extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public BpsstUserSettingPk BpsstUserSettingPk;

	@Basic(optional = false)
	@Column(name = "SCD_VALUE_ATR")
	public int employeeCodeValueType;

	@Basic(optional = false)
	@Column(name = "RECENT_REGISTRATION_ATR")
	public int recentRegistrationType;

	@Basic(optional = false)
	@Column(name = "CARD_NUMBER_VALUE_ATR")
	public int cardNumberValueType;

	@Basic(optional = false)
	@Column(name = "SCD_LETTER")
	public String employeeCodeLetter;

	@Basic(optional = false)
	@Column(name = "CARD_NUMBER_LETTER")
	public String cardNumberLetter;

	@Override
	protected Object getKey() {
		return BpsstUserSettingPk;
	}

}
