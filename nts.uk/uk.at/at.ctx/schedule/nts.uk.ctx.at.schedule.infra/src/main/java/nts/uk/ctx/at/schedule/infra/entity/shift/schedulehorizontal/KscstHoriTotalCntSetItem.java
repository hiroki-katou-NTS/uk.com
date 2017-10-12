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
@Table(name = "KSCST_HORI_TOTAL_CNT_SET")
public class KscstHoriTotalCntSetItem extends UkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public KscstHoriTotalCntSetPK kscstHoriTotalCntSetPK;

	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "CID", referencedColumnName = "KSCMT_HORI_TOTAL_CATEGORY.CID", insertable = false, updatable = false),
			@JoinColumn(name = "CATEGORY_CD", referencedColumnName = "KSCMT_HORI_TOTAL_CATEGORY.CATEGORY_CD", insertable = false, updatable = false)
	})
	public KscmtHoriTotalCategoryItem kscmtHoriTotalCategory2;
	
	@Override
	protected Object getKey() {
		return kscstHoriTotalCntSetPK;
	}
	
	public KscstHoriTotalCntSetItem(KscstHoriTotalCntSetPK kscstHoriTotalCntSetPK){
		super();
		this.kscstHoriTotalCntSetPK = kscstHoriTotalCntSetPK;
	}
}
