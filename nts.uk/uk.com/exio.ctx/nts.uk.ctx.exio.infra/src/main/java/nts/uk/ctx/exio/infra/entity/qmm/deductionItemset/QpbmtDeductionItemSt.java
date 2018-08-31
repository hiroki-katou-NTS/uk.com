package nts.uk.ctx.exio.infra.entity.qmm.deductionItemset;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.exio.dom.qmm.deductionItemset.DeductionItemSt;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 控除項目設定
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_DEDUCTION_ITEM_ST")
public class QpbmtDeductionItemSt extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@EmbeddedId
	public QpbmtDeductionItemStPk deductionItemStPk;

	/**
	 * 控除項目区分
	 */
	@Basic(optional = false)
	@Column(name = "DEDUCTION_ITEM_ATR")
	public int deductionItemAtr;

	/**
	 * 内訳項目利用区分
	 */
	@Basic(optional = false)
	@Column(name = "BREAKDOWN_ITEM_USE_ATR")
	public int breakdownItemUseAtr;

	/**
	 * 備考
	 */
	@Basic(optional = true)
	@Column(name = "NOTE")
	public String note;

	@Override
	protected Object getKey() {
		return deductionItemStPk;
	}

	public DeductionItemSt toDomain() {
		return new DeductionItemSt(this.deductionItemStPk.cid, this.deductionItemStPk.salaryItemId,
				this.deductionItemAtr, this.breakdownItemUseAtr, this.note);
	}

	public static QpbmtDeductionItemSt toEntity(DeductionItemSt domain) {
		return new QpbmtDeductionItemSt(new QpbmtDeductionItemStPk(domain.getCid(), domain.getSalaryItemId()),
				domain.getDeductionItemAtr().value,
				domain.getBreakdownItemUseAtr().value,
				domain.getNote().orElse(null));
	}

}
