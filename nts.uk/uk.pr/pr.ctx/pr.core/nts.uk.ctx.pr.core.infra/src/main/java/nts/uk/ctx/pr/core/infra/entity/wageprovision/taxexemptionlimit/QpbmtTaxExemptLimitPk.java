package nts.uk.ctx.pr.core.infra.entity.wageprovision.taxexemptionlimit;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 非課税限度額の登録: 主キー情報
 */
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class QpbmtTaxExemptLimitPk implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 会社ID
	 */
	@Basic(optional = false)
	@Column(name = "CID")
	public String cid;

	/**
	 * 非課税限度額コード
	 */
	@Basic(optional = false)
	@Column(name = "TAX_FREEAMOUNT_CODE")
	public String taxFreeamountCode;

}
