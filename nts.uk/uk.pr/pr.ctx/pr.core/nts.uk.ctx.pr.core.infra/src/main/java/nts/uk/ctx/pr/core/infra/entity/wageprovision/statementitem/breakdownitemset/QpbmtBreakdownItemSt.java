package nts.uk.ctx.pr.core.infra.entity.wageprovision.statementitem.breakdownitemset;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.breakdownitemset.BreakdownItemSet;
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

	public BreakdownItemSet toDomain() {
		return new BreakdownItemSet(this.breakdownItemStPk.salaryItemId, this.breakdownItemStPk.breakdownItemCode,
				this.breakdownItemName);
	}

	public static QpbmtBreakdownItemSt toEntity(BreakdownItemSet domain) {
		return new QpbmtBreakdownItemSt(
				new QpbmtBreakdownItemStPk(domain.getSalaryItemId(), domain.getBreakdownItemCode().v()),
				domain.getBreakdownItemName().v());
	}

}
