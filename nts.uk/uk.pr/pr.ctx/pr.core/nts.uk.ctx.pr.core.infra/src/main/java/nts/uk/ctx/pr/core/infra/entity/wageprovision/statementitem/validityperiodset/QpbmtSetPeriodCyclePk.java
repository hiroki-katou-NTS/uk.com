package nts.uk.ctx.pr.core.infra.entity.wageprovision.statementitem.validityperiodset;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 有効期間とサイクルの設定: 主キー情報
 */
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class QpbmtSetPeriodCyclePk implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 会社ID
	 */
	@Basic(optional = false)
	@Column(name = "CID")
	public String cid;

	/**
	 * カテゴリ区分
	 */
	@Basic(optional = false)
	@Column(name = "CATEGORY_ATR")
	public int categoryAtr;

	/**
	 * 項目名コード
	 */
	@Basic(optional = false)
	@Column(name = "ITEM_NAME_CD")
	public String itemNameCd;

}
