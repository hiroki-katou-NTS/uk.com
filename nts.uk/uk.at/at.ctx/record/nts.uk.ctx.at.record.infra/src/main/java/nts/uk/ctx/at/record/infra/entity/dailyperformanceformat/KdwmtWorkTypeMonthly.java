package nts.uk.ctx.at.record.infra.entity.dailyperformanceformat;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;

import nts.uk.shr.infra.data.entity.UkJpaEntity;

public class KdwmtWorkTypeMonthly extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KdwmtWorkTypeMonthlyPK kdwmtWorkTypeMonthlyPK;

	@Column(name = "ORDER")
	public BigDecimal order;

	@Column(name = "COLUMN_WIDTH")
	public BigDecimal columnWidth;

	@Override
	protected Object getKey() {
		return this.kdwmtWorkTypeMonthlyPK;
	}
}
