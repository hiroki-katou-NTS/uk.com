package nts.uk.ctx.at.schedule.infra.entity.shift.schedulehorizontal;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCMT_HORIZONTAL_CNT_AGG")
public class KscstHoriTotalCntSetItem extends UkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public KscstHoriTotalCntSetPK kscstHoriTotalCntSetPK;
	// many cnt set to one order item
	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
			@JoinColumn(name = "CATEGORY_CD", referencedColumnName = "CATEGORY_CD", insertable = false, updatable = false),
			@JoinColumn(name = "TOTAL_ITEM_NO", referencedColumnName = "TOTAL_ITEM_NO", insertable = false, updatable = false)
	})
	public KscmtTotalEvalOrderItem kscmtTotalEvalOrderItem;
	
	@Override
	protected Object getKey() {
		return kscstHoriTotalCntSetPK;
	}
	
	public KscstHoriTotalCntSetItem(KscstHoriTotalCntSetPK kscstHoriTotalCntSetPK){
		super();
		this.kscstHoriTotalCntSetPK = kscstHoriTotalCntSetPK;
	}
}
