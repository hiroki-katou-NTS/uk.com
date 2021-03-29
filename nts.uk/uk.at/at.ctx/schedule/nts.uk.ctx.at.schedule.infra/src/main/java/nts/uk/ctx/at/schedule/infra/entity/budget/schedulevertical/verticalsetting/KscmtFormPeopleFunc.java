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
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCMT_FORM_PEOPLE_FUNC")
public class KscmtFormPeopleFunc extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/* 主キー */
	@EmbeddedId
	public KscmtFormPeopleFuncPK kscmtFormPeopleFuncPK;
	
	/* 外部予算実績項目コード */
	@Column(name = "EXTERNAL_BUDGET_CD")
	public String externalBudgetCd;

	/* カテゴリ区分 */
	@Column(name = "CATEGORY_ATR")
	public int categoryAtr;
	
	/* 演算子区分 */
	@Column(name = "OPERATOR_ATR")
	public int operatorAtr;
	
	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "CID", referencedColumnName = "KSCMT_FORM_PEOPLE.CID", insertable = false, updatable = false),
		@JoinColumn(name = "VERTICAL_CAL_CD", referencedColumnName = "KSCMT_FORM_PEOPLE.VERTICAL_CAL_CD", insertable = false, updatable = false),
		@JoinColumn(name = "VERTICAL_CAL_ITEM_ID", referencedColumnName = "KSCMT_FORM_PEOPLE.VERTICAL_CAL_ITEM_ID", insertable = false, updatable = false)
	})
	public KscmtFormPeople kscmtFormPeople;
	
	
	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return kscmtFormPeopleFuncPK;
	}
}
