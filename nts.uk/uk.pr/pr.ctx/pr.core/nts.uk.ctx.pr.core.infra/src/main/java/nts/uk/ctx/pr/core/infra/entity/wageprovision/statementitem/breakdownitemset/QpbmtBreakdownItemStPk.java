package nts.uk.ctx.pr.core.infra.entity.wageprovision.statementitem.breakdownitemset;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 内訳項目設定: 主キー情報
 */
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class QpbmtBreakdownItemStPk implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 給与項目ID
	 */
	@Basic(optional = false)
	@Column(name = "SALARY_ITEM_ID")
	public String salaryItemId;

	/**
	 * 内訳項目コード
	 */
	@Basic(optional = false)
	@Column(name = "BREAKDOWN_ITEM_CODE ")
	public String breakdownItemCode;

}
