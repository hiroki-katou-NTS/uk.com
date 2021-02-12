package nts.uk.ctx.at.shared.infra.entity.order;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 
 * @author sonnh1
 *
 */
@Table(name = "KSHMT_WORKTIME_ORDER")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class KshmtWorkTimeOrder extends ContractUkJpaEntity {

	@EmbeddedId
	public KshmpWorkTimeOrderPK kshmpWorkTimeOrderPK;

	@Column(name = "DISPORDER")
	public int dispOrder;

	@Override
	protected Object getKey() {
		return this.kshmpWorkTimeOrderPK;
	}

}
