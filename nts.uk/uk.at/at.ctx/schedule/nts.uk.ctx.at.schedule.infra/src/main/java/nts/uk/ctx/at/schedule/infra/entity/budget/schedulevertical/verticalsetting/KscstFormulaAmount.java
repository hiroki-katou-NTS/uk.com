package nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCST_FORMULA_AMOUNT")
public class KscstFormulaAmount extends UkJpaEntity implements Serializable {
private static final long serialVersionUID = 1L;
	
	/* 主キー */
	@EmbeddedId
	public KscstFormulaAmountPK kscstFormulaAmountPK;
    
	/* 属性 */
	@Column(name = "CAL_METHOD_ATR")
	public int calMethodAtr;
	@OneToOne
	@JoinColumns({ @JoinColumn(name = "CID", referencedColumnName = "KSCMT_GEN_VERT_ITEM.CID", insertable = false, updatable = false),
		@JoinColumn(name = "VERTICAL_CAL_CD", referencedColumnName = "KSCMT_GEN_VERT_ITEM.VERTICAL_CAL_CD", insertable = false, updatable = false),
		@JoinColumn(name = "ITEM_ID", referencedColumnName = "KSCMT_GEN_VERT_ITEM.ITEM_ID", insertable = false, updatable = false)
	})
	public KscmtGenVertItem kscmtGenVertItemFormula;
	@OneToOne(cascade = CascadeType.ALL, mappedBy="kscmtGenVertItemMoney", orphanRemoval = true)
	public KscstFormulaMoney moneyFunc;
	@OneToOne(cascade = CascadeType.ALL, mappedBy="kscmtGenVertItemFortmula", orphanRemoval = true)
	public KscstFormulaTimeUnit timeUnit;
	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return kscstFormulaAmountPK;
	}
}
