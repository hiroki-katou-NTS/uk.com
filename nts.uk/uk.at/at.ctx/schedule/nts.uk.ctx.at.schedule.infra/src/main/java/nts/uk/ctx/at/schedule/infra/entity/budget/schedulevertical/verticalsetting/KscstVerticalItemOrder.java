package nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCST_FIXED_VERTICAL_SET")
public class KscstVerticalItemOrder extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/* 主キー */
	@EmbeddedId
	public KscstVerticalItemOrderPK kscstVerticalItemOrderPK;
	
	/* 並び順 */
	@Column(name = "DISPORDER")
	public int dispOrder;
	
	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return kscstVerticalItemOrderPK;
	}
}
