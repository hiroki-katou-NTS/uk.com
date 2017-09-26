package nts.uk.ctx.at.shared.infra.entity.category;

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
@Table(name = "KSCST_TOTAL_EVAL_ORDER")
public class KscstTotalEvalOrderItem extends UkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public KscstTotalEvalOrderPK kscstTotalEvalOrderPK;
	/** 並び順 */
	@Column(name = "DISPORDER")
	public Integer dispOrder;
	
	@Override
	protected Object getKey() {
		return kscstTotalEvalOrderPK;
	}
	
	public KscstTotalEvalOrderItem(KscstTotalEvalOrderPK kscstTotalEvalOrderPK){
		super();
		this.kscstTotalEvalOrderPK = kscstTotalEvalOrderPK;
	}
}
