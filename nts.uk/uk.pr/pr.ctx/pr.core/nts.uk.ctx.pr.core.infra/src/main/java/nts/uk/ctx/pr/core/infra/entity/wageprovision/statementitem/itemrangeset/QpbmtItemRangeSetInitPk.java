package nts.uk.ctx.pr.core.infra.entity.wageprovision.statementitem.itemrangeset;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 項目範囲設定初期値: 主キー情報
 */
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class QpbmtItemRangeSetInitPk implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 会社ID
	 */
	@Basic(optional = false)
	@Column(name = "CID")
	public String cid;

	/**
	 * 給与項目ID
	 */
	@Basic(optional = false)
	@Column(name = "SALARY_ITEM_ID")
	public String salaryItemId;

}
