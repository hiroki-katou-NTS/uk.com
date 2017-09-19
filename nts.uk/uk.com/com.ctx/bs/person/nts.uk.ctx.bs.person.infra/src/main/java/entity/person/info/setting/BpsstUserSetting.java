package entity.person.info.setting;

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
	@Column(name = "SCD_VALUE")
	public String employeeCodeValue;
	
	@Basic(optional = false)
	@Column(name = "CID")
	public String companyId;

	@Basic(optional = false)
	@Column(name = "RECENT_REGISTRATION")
	public String recentRegistration;

	@Basic(optional = false)
	@Column(name = "CARD_NUMBER_VALUE")
	public String cardNumberValue;

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
