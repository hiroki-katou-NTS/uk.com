package nts.uk.ctx.at.schedule.infra.entity.shift.schedulehorizontal;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCMT_HORIZONTAL_SORT")
public class KscmtHorizontalSortItem extends ContractUkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public KscmtHorizontalSortPK kscmtHorizontalSortPK;
	/** 並び順 */
	@Column(name = "DISPORDER")
	public Integer dispOrder;
	// many eval order in one category
	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "CID", referencedColumnName = "KSCMT_HORIZONTAL_CATEGORY.CID", insertable = false, updatable = false),
			@JoinColumn(name = "CATEGORY_CD", referencedColumnName = "KSCMT_HORIZONTAL_CATEGORY.CATEGORY_CD", insertable = false, updatable = false)
	})
	public KscmtHorizontalCategoryItem kscmtHorizontalCategory;
	// one eval order to one cal day set
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "kscmtHorizontalSortItem", orphanRemoval = true)
	public KscstHoriCalDaysSetItem horiCalDaysSet;
	// one eval order to many cnt set
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "kscmtHorizontalSortItem", orphanRemoval = true)
	public List<KscmtHorizontalCntAggItem> listHoriCNTSet;
	
	@Override
	protected Object getKey() {
		return kscmtHorizontalSortPK;
	}
	
	public KscmtHorizontalSortItem(KscmtHorizontalSortPK kscmtHorizontalSortPK){
		super();
		this.kscmtHorizontalSortPK = kscmtHorizontalSortPK;
	}
}
