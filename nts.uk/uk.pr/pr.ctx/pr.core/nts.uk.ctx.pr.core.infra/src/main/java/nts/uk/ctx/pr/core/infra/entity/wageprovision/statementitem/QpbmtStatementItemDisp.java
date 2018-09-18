package nts.uk.ctx.pr.core.infra.entity.wageprovision.statementitem;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.StatementItemDisplaySet;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 明細書項目の表示設定
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_STATEMENT_ITEM_DISP")
public class QpbmtStatementItemDisp extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@EmbeddedId
	public QpbmtStatementItemDispPk statementItemDispPk;

	/**
	 * ゼロ表示区分
	 */
	@Basic(optional = false)
	@Column(name = "ZERO_DISPLAY_ATR")
	public int zeroDisplayAtr;

	/**
	 * 項目名表示
	 */
	@Basic(optional = true)
	@Column(name = "ITEM_NAME_DISPLAY")
	public Integer itemNameDisplay;

	@Override
	protected Object getKey() {
		return statementItemDispPk;
	}

	public StatementItemDisplaySet toDomain() {
		return new StatementItemDisplaySet(this.statementItemDispPk.cid, this.statementItemDispPk.salaryItemId,
				this.zeroDisplayAtr, this.itemNameDisplay);
	}

	public static QpbmtStatementItemDisp toEntity(StatementItemDisplaySet domain) {
		return new QpbmtStatementItemDisp(new QpbmtStatementItemDispPk(domain.getCid(), domain.getSalaryItemId()),
				domain.getZeroDisplayAtr().value, domain.getItemNameDisplay().map(i -> i.value).orElse(null));
	}

}
