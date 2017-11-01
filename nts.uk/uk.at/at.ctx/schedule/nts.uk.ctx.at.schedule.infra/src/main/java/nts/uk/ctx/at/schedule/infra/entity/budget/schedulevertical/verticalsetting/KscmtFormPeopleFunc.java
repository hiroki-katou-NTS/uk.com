package nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting;

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
@Table(name = "KSCMT_FORM_PEOPLE_FUNC")
public class KscmtFormPeopleFunc extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/* 主キー */
	@EmbeddedId
	public KscmtFormPeopleFuncPK kscmtFormPeopleFuncPK;

	/* カテゴリ区分 */
	@Column(name = "CATEGORY_ATR")
	public int categoryAtr;
	
	/* 演算子区分 */
	@Column(name = "OPERATOR_ATR")
	public int operatorAtr;
	
	/* 順番 */
	@Column(name = "DISPORDER")
	public int dispOrder;
	
	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "CID", referencedColumnName = "KSCMT_GEN_VERT_ITEM.CID", insertable = false, updatable = false),
		@JoinColumn(name = "VERTICAL_CAL_CD", referencedColumnName = "KSCMT_GEN_VERT_ITEM.VERTICAL_CAL_CD", insertable = false, updatable = false),
		@JoinColumn(name = "ITEM_ID", referencedColumnName = "KSCMT_GEN_VERT_ITEM.ITEM_ID", insertable = false, updatable = false)
	})
	public KscmtGenVertItem kscmtGenVertItemFunc;
	
	
	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return kscmtFormPeopleFuncPK;
	}
}
