package nts.uk.ctx.exio.infra.entity.exi.condset;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 受入条件設定（定型）: 主キー情報
 */
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
@Setter
@Getter
public class OiomtStdAcceptCondSetPk implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 会社ID
	 */
	@Basic(optional = false)
	@Column(name = "CID")
	public String cid;

	/**
	 * 外部受入条件コード
	 */
	@Basic(optional = false)
	@Column(name = "CONDITION_SET_CD")
	public String conditionSetCd;

}
