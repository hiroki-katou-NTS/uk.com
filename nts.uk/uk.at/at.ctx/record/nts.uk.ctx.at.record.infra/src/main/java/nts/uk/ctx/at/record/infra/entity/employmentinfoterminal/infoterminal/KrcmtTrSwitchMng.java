package nts.uk.ctx.at.record.infra.entity.employmentinfoterminal.infoterminal;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Entity
@Table(name = "KRCMT_TR_SWITCH_MNG")
public class KrcmtTrSwitchMng extends UkJpaEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CONTRACT_CD")
	public String contractCode;
	
	@Column(name = "MNG_ATR")
	public int managementAtr;
	
	
	@Override
	protected Object getKey() {
		return this.contractCode;
	}	
}
