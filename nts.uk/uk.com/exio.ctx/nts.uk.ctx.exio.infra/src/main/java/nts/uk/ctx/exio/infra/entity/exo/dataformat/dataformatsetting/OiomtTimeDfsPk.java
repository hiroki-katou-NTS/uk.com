package nts.uk.ctx.exio.infra.entity.exo.dataformat.dataformatsetting;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 時間型データ形式設定: 主キー情報
 */
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class OiomtTimeDfsPk implements Serializable {
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
	@Column(name = "COND_SET_CD")
	public String condSetCd;

	/**
	 * 出力項目コード
	 */
	@Basic(optional = false)
	@Column(name = "OUT_ITEM_CD")
	public String outItemCd;
}
