package nts.uk.ctx.exio.infra.entity.exi.condset;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 受入選別条件設定: 主キー情報
 */

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class OiomtExAcScreenCondPk implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 会社ID
	 */
	@Basic(optional = false)
	@Column(name = "CID")
	public String cid;

	/**
	 * システム種類
	 */
	@Basic(optional = false)
	@Column(name = "SYSTEM_TYPE")
	public int systemType;

	/**
	 * 条件設定コード
	 */
	@Basic(optional = false)
	@Column(name = "CONDITION_SET_CD")
	public String conditionSetCd;

	/**
	 * 受入項目の番号
	 */
	@Basic(optional = false)
	@Column(name = "ACCEPT_ITEM_NUM")
	public int acceptItemNum;

}
