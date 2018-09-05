package nts.uk.ctx.exio.infra.entity.qmm.breakdownitemset;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.exio.dom.qmm.breakdownitemset.BreakdownItemSt;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 内訳項目設定
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_BREAKDOWN_ITEM_ST")
public class QpbmtBreakdownItemSt extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@EmbeddedId
	public QpbmtBreakdownItemStPk breakdownItemStPk;

	/**
	 * 内訳項目名称
	 */
	@Basic(optional = false)
	@Column(name = "BREAKDOWN_ITEM_NAME")
	public String breakdownItemName;

	@Override
	protected Object getKey() {
		return breakdownItemStPk;
	}

	public BreakdownItemSt toDomain() {
		return new BreakdownItemSt(this.breakdownItemStPk.salaryItemId, this.breakdownItemStPk.breakdownItemCode,
				this.breakdownItemName);
	}

	public static QpbmtBreakdownItemSt toEntity(BreakdownItemSt domain) {
		return new QpbmtBreakdownItemSt(
				new QpbmtBreakdownItemStPk(domain.getSalaryItemId(), domain.getBreakdownItemCode().v()),
				domain.getBreakdownItemName().v());
	}

}
