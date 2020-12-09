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
@Table(name = "KRCMT_BONUS_PAY_TS")
public class KbpmtBPTimesheet extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public KbpmtBPTimesheetPK kbpmtBPTimesheetPK;
	@Column(name = "USE_ATR")
	public BigDecimal useAtr;
	//加給時間項目NO
	@Column(name = "TIME_ITEM_ID")
	public int timeItemId;
	@Column(name = "START_TIME")
	public BigDecimal startTime;
	@Column(name = "END_TIME")
	public BigDecimal endTime;
	@Column(name = "UNIT")
	public BigDecimal roundingTimeAtr;
	@Column(name = "ROUNDING")
	public BigDecimal roundingAtr;

	@Override
	protected Object getKey() {
		return kbpmtBPTimesheetPK;
	}

}
