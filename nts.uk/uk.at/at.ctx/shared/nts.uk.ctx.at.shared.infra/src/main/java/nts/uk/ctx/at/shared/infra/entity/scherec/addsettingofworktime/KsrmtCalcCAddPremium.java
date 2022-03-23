package nts.uk.ctx.at.shared.infra.entity.scherec.addsettingofworktime;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * エンティティ：労働時間の加算設定(割増時間の加算設定)
 * @author shuichi_ishida
 */
@Entity
@Table(name = "KSRMT_CALC_C_ADD_PREMIUM")
public class KsrmtCalcCAddPremium extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/** プライマリキー */
	@EmbeddedId
	public KsrmtCalcCAddPK pk;

	/** 通常、変形の所定超過時 */
	@Column(name = "FIX_CALC_OVER_PREDTIME")
	public Integer fixCalcOverPredtime;
	/** フレックスの所定超過時 */
	@Column(name = "FLE_CALC_OVER_PREDTIME")
	public Integer fleCalcOverPredtime;
	
	@Override
	protected Object getKey() {
		return this.pk;
	}
}
