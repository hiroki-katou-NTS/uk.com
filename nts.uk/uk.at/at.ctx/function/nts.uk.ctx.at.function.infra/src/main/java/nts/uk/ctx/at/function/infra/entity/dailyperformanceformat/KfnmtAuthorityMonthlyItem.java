package nts.uk.ctx.at.function.infra.entity.dailyperformanceformat;

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
@Table(name = "KFNMT_DAY_FORM_MON_ITEM")
public class KfnmtAuthorityMonthlyItem extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KfnmtAuthorityMonthlyItemPK kfnmtAuthorityMonthlyItemPK;
	
	@Column(name = "DISPLAY_ORDER")
	public int displayOrder;

	@Column(name = "COLUMN_WIDTH")
	public BigDecimal columnWidth;

	@Override
	protected Object getKey() {
		return this.kfnmtAuthorityMonthlyItemPK;
	}

}
