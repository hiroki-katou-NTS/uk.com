package nts.uk.ctx.at.shared.infra.entity.order;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 
 * @author sonnh1
 *
 */
@Table(name = "KODST_ORDER")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class KodstOrder extends UkJpaEntity {

	@EmbeddedId
	public KodspOrderPK kodspOrderPK;

	@Column(name = "SORT_ORDER")
	public int sortOrder;

	@Override
	protected Object getKey() {
		return this.kodspOrderPK;
	}

}
