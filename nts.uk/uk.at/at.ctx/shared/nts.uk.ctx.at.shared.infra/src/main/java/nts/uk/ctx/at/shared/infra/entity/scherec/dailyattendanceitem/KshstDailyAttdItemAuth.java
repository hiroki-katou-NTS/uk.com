package nts.uk.ctx.at.shared.infra.entity.scherec.dailyattendanceitem;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "KSHST_DAI_ITEM_AUTH")
public class KshstDailyAttdItemAuth extends UkJpaEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public KshstDailyAttdItemAuthPK kshstDailyAttdItemAuthPK;
	@Column(name = "CHANGED_BY_YOU")
	public BigDecimal youCanChangeIt;
	@Column(name = "CHANGED_BY_OTHERS")
	public BigDecimal canBeChangedByOthers;
	@Column(name = "USE_ATR")
	public BigDecimal use;
	@Override
	protected Object getKey() {
		return this.kshstDailyAttdItemAuthPK;
	}

}
