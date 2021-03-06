package nts.uk.ctx.exio.infra.entity.exi.item;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 受入項目（定型）: 主キー情報
 */

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class OiomtStdAcceptItemPk implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 会社ID
	 */
	@Basic(optional = false)
	@Column(name = "CID")
	public String cid;

	/**
	 * 条件設定コード
	 */
	@Basic(optional = false)
	@Column(name = "CONDITION_SET_CD")
	public String conditionSetCd;

	/**
	 * 受入項目番号
	 */
	@Basic(optional = false)
	@Column(name = "ACCEPT_ITEM_NUMBER")
	public int acceptItemNumber;

}
