package nts.uk.ctx.at.shared.infra.entity.scherec.addsettingofworktime;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * エンティティ：労働時間の加算設定(就業時間の加算設定)
 * @author shuichi_ishida
 */
@Entity
@Table(name = "KSRMT_CALC_C_ADD_WORKTIME")
public class KsrmtCalcCAddWorktime extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/** プライマリキー */
	@EmbeddedId
	public KsrmtCalcCAddPK pk;

	/** 通常、変形の所定超過時 */
	@Column(name = "FIX_CALC_OVER_PREDTIME")
	public Integer fixCalcOverPredtime;
	/** 月次法定内のみ加算 */
	@Column(name = "FLE_CALC_IN_LEGAL")
	public Integer fleCalcInLegal;
	/** フレックスの所定不足時 */
	@Column(name = "FLE_CALC_SHORT_PREDTIME")
	public Integer fleCalcShortPredtime;
	/** 欠勤をマイナスにせず所定から控除する */
	@Column(name = "FLE_CALC_DEDUCT_PREDTIME_ABSENCE")
	public Integer fleCalcDeductPredtimeAbsence;

	@Override
	protected Object getKey() {
		return this.pk;
	}
}
