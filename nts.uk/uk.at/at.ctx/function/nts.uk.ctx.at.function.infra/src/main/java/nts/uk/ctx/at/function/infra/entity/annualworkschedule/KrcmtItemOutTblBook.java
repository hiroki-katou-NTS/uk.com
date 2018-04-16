package nts.uk.ctx.at.function.infra.entity.annualworkschedule;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.dom.annualworkschedule.ItemOutTblBook;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 帳表に出力する項目
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCMT_ITEM_OUT_TBL_BOOK")
public class KrcmtItemOutTblBook extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@EmbeddedId
	public KrcmtItemOutTblBookPk itemOutTblBookPk;

	/**
	 * 使用区分
	 */
	@Basic(optional = false)
	@Column(name = "USE_CLASS")
	public int useClass;

	/**
	 * 値の出力形式
	 */
	@Basic(optional = false)
	@Column(name = "VAL_OUT_FORMAT")
	public int valOutFormat;

	/**
	 * 見出し名称
	 */
	@Basic(optional = false)
	@Column(name = "HEADING_NAME")
	public String headingName;

	@Override
	protected Object getKey() {
		return itemOutTblBookPk;
	}

	public ItemOutTblBook toDomain() {
		return new ItemOutTblBook(this.itemOutTblBookPk.cid, this.itemOutTblBookPk.code, this.itemOutTblBookPk.sortBy,
				this.useClass, this.valOutFormat, this.headingName);
	}

	public static KrcmtItemOutTblBook toEntity(ItemOutTblBook domain) {
		return new KrcmtItemOutTblBook(new KrcmtItemOutTblBookPk(domain.getCid(), domain.getCode(), domain.getSortBy()),
				domain.getUseClass(), domain.getValOutFormat(), domain.getHeadingName());
	}

}
