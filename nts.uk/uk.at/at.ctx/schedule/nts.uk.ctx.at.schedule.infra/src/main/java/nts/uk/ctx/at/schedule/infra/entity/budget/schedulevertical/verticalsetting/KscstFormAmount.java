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
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCMT_FORM_AMOUNT")
public class KscstFormAmount extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/* 主キー */
	@EmbeddedId
	public KscstFormAmountPK kscstFormulaAmountPK;

	/* 属性 */
	@Column(name = "CAL_METHOD_ATR")
	public int calMethodAtr;
	
	@OneToOne
	@JoinColumns({
			@JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
			@JoinColumn(name = "VERTICAL_CAL_CD", referencedColumnName = "VERTICAL_CAL_CD", insertable = false, updatable = false),
			@JoinColumn(name = "VERTICAL_CAL_ITEM_ID", referencedColumnName = "ITEM_ID", insertable = false, updatable = false) })
	public KscmtVerticalItem kscmtVerticalItem;
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "kscstFormulaAmount", orphanRemoval = true)
	public KscmtFormMoney moneyFunc;
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "kscstFormulaAmount", orphanRemoval = true)
	public KscstFormTimeUnit timeUnit;

	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return kscstFormulaAmountPK;
	}
}
