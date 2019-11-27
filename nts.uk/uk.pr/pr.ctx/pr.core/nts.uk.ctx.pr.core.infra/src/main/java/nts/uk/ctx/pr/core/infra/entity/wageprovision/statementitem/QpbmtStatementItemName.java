package nts.uk.ctx.pr.core.infra.entity.wageprovision.statementitem;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.StatementItemName;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 明細書項目名称
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_STATEMENT_ITEM_NAME")
public class QpbmtStatementItemName extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@EmbeddedId
	public QpbmtStatementItemNamePk statementItemNamePk;

	/**
	 * 名称
	 */
	@Basic(optional = false)
	@Column(name = "NAME")
	public String name;

	/**
	 * 略名
	 */
	@Basic(optional = false)
	@Column(name = "SHORT_NAME")
	public String shortName;

	/**
	 * その他言語名称
	 */
	@Basic(optional = true)
	@Column(name = "OTHER_LANGUAGE_NAME")
	public String otherLanguageName;

	/**
	 * 英語名称
	 */
	@Basic(optional = true)
	@Column(name = "ENGLISH_NAME")
	public String englishName;

	@Override
	protected Object getKey() {
		return statementItemNamePk;
	}

	public StatementItemName toDomain() {
		return new StatementItemName(this.statementItemNamePk.cid, this.statementItemNamePk.categoryAtr, this.statementItemNamePk.itemNameCd, this.name,
				this.shortName, this.otherLanguageName, this.englishName);
	}

	public static QpbmtStatementItemName toEntity(StatementItemName domain) {
		return new QpbmtStatementItemName(new QpbmtStatementItemNamePk(domain.getCid(), domain.getCategoryAtr().value, domain.getItemNameCd().v()),
				domain.getName().v(), domain.getShortName().v(),
				domain.getOtherLanguageName().map(i -> i.v()).orElse(null),
				domain.getEnglishName().map(i -> i.v()).orElse(null));
	}

}
