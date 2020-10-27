package nts.uk.ctx.exio.infra.entity.exi.condset;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 受入条件設定（定型）: 主キー情報
 */
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
public class OiomtExAcCondPk implements Serializable {
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
	 * 外部受入条件コード
	 */
	@Basic(optional = false)
	@Column(name = "CONDITION_SET_CD")
	public String conditionSetCd;

}
