package nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCST_FORMULA_MONEY")
public class KscstFormulaMoney extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/* 主キー */
	@EmbeddedId
	public KscstFormulaMoneyPK kscstFormulaMoneyPK;
	
    /* カテゴリ区分 */
	@Column(name = "CATEGORY_INDICATOR")
	public int categoryIndicator;
    
    /* 実績表示区分 */
	@Column(name = "ACTUAL_DISPLAY_ATR")
	public int actualDisplayAtr;

	@OneToOne
	@JoinColumns({
			@JoinColumn(name = "CID", referencedColumnName = "KSCST_MONEY_FUNC.CID", insertable = false, updatable = false),
			@JoinColumn(name = "VERTICAL_CAL_CD", referencedColumnName = "KSCST_MONEY_FUNC.VERTICAL_CAL_CD", insertable = false, updatable = false),
			@JoinColumn(name = "ITEM_ID", referencedColumnName = "KSCST_MONEY_FUNC.ITEM_ID", insertable = false, updatable = false)
		})

	public KscmtGenVertItem kscmtGenVertItemMoney;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "kscstFormulaMoney", orphanRemoval = true)
	public List<KscstMoneyFunc> listMoney;
	
	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return kscstFormulaMoneyPK;
	}
}
