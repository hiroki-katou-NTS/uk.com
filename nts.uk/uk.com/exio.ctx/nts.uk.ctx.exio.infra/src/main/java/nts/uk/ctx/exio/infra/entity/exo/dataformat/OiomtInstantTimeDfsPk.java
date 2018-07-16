package nts.uk.ctx.exio.infra.entity.exo.dataformat;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 時刻型データ形式設定: 主キー情報
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class OiomtInstantTimeDfsPk extends OiomtInTimeDataFmSetPk {
	private static final long serialVersionUID = 1L;

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
