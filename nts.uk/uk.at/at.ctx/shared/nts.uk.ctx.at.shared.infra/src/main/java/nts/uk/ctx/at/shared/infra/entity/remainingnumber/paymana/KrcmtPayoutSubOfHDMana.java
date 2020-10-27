package nts.uk.ctx.at.shared.infra.entity.remainingnumber.paymana;

import java.io.Serializable;
//import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Entity
@Table(name = "KRCMT_PAYOUT_SUBOFHD_MANA")
@NoArgsConstructor
@AllArgsConstructor
public class KrcmtPayoutSubOfHDMana extends ContractUkJpaEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KrcmtPayoutSubOfHDManaPK krcmtPayoutSubOfHDManaPK;
	// 使用日数
	@Column(name = "USED_DAYS")
	public Double usedDays;
	
	// 対象選択区分
	@Column(name = "TARGET_SELECTION_ATR")
	public int targetSelectionAtr;

	@Override
	protected Object getKey() {
		return krcmtPayoutSubOfHDManaPK;
	}
}
