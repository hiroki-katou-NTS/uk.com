package nts.uk.ctx.at.record.infra.entity.dailyperformanceformat;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 
 * @author nampt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KFNMT_DAY_FORM_BUS_MON")
public class KrcmtBusinessTypeMonthly extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcmtBusinessTypeMonthlyPK krcmtBusinessTypeMonthlyPK;

	@Column(name = "ORDER_MONTHLY")
	public int order;

	@Column(name = "COLUMN_WIDTH")
	public BigDecimal columnWidth;

	@Override
	protected Object getKey() {
		return this.krcmtBusinessTypeMonthlyPK;
	}
}
