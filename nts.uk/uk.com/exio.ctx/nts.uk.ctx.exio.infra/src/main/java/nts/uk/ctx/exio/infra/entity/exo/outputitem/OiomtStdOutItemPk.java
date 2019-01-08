package nts.uk.ctx.exio.infra.entity.exo.outputitem;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 出力項目(定型): 主キー情報
 */
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class OiomtStdOutItemPk implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 会社ID
	 */
	@Basic(optional = false)
	@Column(name = "CID")
	public String cid;

	/**
	 * 出力項目コード
	 */
	@Basic(optional = false)
	@Column(name = "OUT_ITEM_CD")
	public String outItemCd;

	/**
	 * 条件設定コード
	 */
	@Basic(optional = false)
	@Column(name = "COND_SET_CD")
	public String condSetCd;

}
