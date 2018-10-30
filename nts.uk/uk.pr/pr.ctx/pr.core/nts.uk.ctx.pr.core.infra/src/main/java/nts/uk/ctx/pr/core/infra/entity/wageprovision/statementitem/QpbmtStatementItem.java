package nts.uk.ctx.pr.core.infra.entity.wageprovision.statementitem;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.StatementItem;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 明細書項目
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_STATEMENT_ITEM")
public class QpbmtStatementItem extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@EmbeddedId
	public QpbmtStatementItemPk statementItemPk;

	/**
	 * 既定区分
	 */
	@Basic(optional = false)
	@Column(name = "DEFAULT_ATR")
	public int defaultAtr;

	/**
	 * 値の属性
	 */
	@Basic(optional = false)
	@Column(name = "VALUE_ATR")
	public int valueAtr;

	/**
	 * 廃止区分
	 */
	@Basic(optional = false)
	@Column(name = "DEPRECATED_ATR")
	public int deprecatedAtr;

	/**
	 * 社会保険対象変更区分
	 */
	@Basic(optional = true)
	@Column(name = "SOCIAL_INSUA_EDITABLE_ATR")
	public Integer socialInsuaEditableAtr;

	/**
	 * 統合コード
	 */
	@Basic(optional = true)
	@Column(name = "INTERGRATE_CD")
	public String intergrateCd;

	@Override
	protected Object getKey() {
		return statementItemPk;
	}

	public StatementItem toDomain() {
		return new StatementItem(this.statementItemPk.cid, this.statementItemPk.categoryAtr,
				this.statementItemPk.itemNameCd, this.defaultAtr, this.valueAtr,
				this.deprecatedAtr, this.socialInsuaEditableAtr, this.intergrateCd);
	}

	public static QpbmtStatementItem toEntity(StatementItem domain) {
		return new QpbmtStatementItem(
				new QpbmtStatementItemPk(domain.getCid(), domain.getCategoryAtr().value, domain.getItemNameCd().v()),
				domain.getDefaultAtr().value, domain.getValueAtr().value, domain.getDeprecatedAtr().value,
				domain.getSocialInsuaEditableAtr().map(i -> i.value).orElse(null),
				domain.getIntergrateCd().map(i -> i.v()).orElse(null));
	}

}
