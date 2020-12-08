package nts.uk.ctx.at.shared.infra.entity.bonuspay;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "KBPMT_BONUS_PAY_SET")
public class KbpmtBonusPaySetting extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public KbpmtBonusPaySettingPK kbpmtBonusPaySettingPK;
	@Column(name = "BONUS_PAY_SET_NAME")
	public String name;
	// public List<KbpmtBPTimesheet> lstBonusPayTimesheet;
	// public List<KbpmtSpecBPTimesheet> lstSpecBonusPayTimesheet;

	@Override
	protected Object getKey() {
		return kbpmtBonusPaySettingPK;
	}

}
