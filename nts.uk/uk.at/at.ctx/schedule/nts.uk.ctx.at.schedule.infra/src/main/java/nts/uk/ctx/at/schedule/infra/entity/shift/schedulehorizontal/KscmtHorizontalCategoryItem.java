package nts.uk.ctx.at.schedule.infra.entity.shift.schedulehorizontal;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCMT_HORIZONTAL_CATEGORY")
public class KscmtHorizontalCategoryItem extends ContractUkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public KscmtHorizontalCategoryPK kscmtHorizontalCategoryPK;
	/** カテゴリ名称 */
	@Column(name = "CATEGORY_NAME")
	public String categoryName;
	/** メモ */
	@Column(name = "MEMO")
	public String memo;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "kscmtHorizontalCategory", orphanRemoval = true)
	public List<KscmtHorizontalSortItem> listTotalEvalOrder;
	
	@Override
	protected Object getKey() {
		return kscmtHorizontalCategoryPK;
	}
	
	public KscmtHorizontalCategoryItem(KscmtHorizontalCategoryPK kscmtHorizontalCategoryPK){
		super();
		this.kscmtHorizontalCategoryPK = kscmtHorizontalCategoryPK;
	}
}
