package nts.uk.ctx.at.function.infra.entity.dailyperformanceformat;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 
 * @author anhdt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KFNMT_DAY_FORM_S_BUS_MON")
public class KrcmtBusinessTypeSMonthly extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcmtBusinessTypeSMonthlyPK krcmtBusinessTypeSMonthlyPK;

	@Column(name = "ORDER_MONTHLY")
	public int order;

	@Override
	protected Object getKey() {
		return this.krcmtBusinessTypeSMonthlyPK;
	}
}
