package nts.uk.ctx.at.schedule.infra.entity.shift.schedulehorizontal;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
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
	
	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "CID", referencedColumnName = "KSCST_HORI_TOTAL_CATEGORY.CID", insertable = false, updatable = false),
			@JoinColumn(name = "CATEGORY_CD", referencedColumnName = "KSCST_HORI_TOTAL_CATEGORY.CATEGORY_CD", insertable = false, updatable = false)
	})
	public KscstHoriTotalCategoryItem kscstHoriTotalCategory;
	
	@Override
	protected Object getKey() {
		return kscstTotalEvalOrderPK;
	}
	
	public KscstTotalEvalOrderItem(KscstTotalEvalOrderPK kscstTotalEvalOrderPK){
		super();
		this.kscstTotalEvalOrderPK = kscstTotalEvalOrderPK;
	}
}
