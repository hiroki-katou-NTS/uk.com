package nts.uk.ctx.at.shared.infra.entity.bonuspay;

import java.io.Serializable;
import java.math.BigDecimal;

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
@Table(name = "KBPST_BP_TIME_ITEM")
public class KbpstBonusPayTimeItem extends ContractUkJpaEntity implements Serializable {

	public static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KbpstBonusPayTimeItemPK kbpstBonusPayTimeItemPK;
	
	@Column(name = "USE_ATR")
	public BigDecimal useAtr;
	
	@Column(name = "TIME_ITEM_NAME")
	public String timeItemName;

	@Override
	protected Object getKey() {
		return kbpstBonusPayTimeItemPK;
	}
}
