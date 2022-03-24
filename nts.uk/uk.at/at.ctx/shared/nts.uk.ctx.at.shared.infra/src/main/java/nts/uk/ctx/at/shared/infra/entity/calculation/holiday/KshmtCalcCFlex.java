package nts.uk.ctx.at.shared.infra.entity.calculation.holiday;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
/**
 * @author phongtq
 * フレックス勤務の設定
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSHMT_CALC_C_FLEX")
public class KshmtCalcCFlex  extends ContractUkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	/** 主キー */
	@EmbeddedId
	public KshstFlexSetPK kshstFlexSetPK;
	
	/** 半日休日の計算方法.不足計算 */
	@Column(name = "MISS_CALC_HD")
	public boolean missCalcHd;
	
	/** 半日休日の計算方法.割増計算 */
	@Column(name = "PREMIUM_CALC_HD")
	public boolean premiumCalcHd;
	
	/** 代休取得時の計算方法.所定から控除するかどうか */
	@Column(name = "IS_DEDUCT_PRED")
	public boolean isDeductPred;
	
	/** 代休取得時の計算方法.割増計算 */
	@Column(name = "PREMIUM_CALC_SUBHD")
	public boolean premiumCalcSubhd;
	
	/** 代休取得時の計算方法.時間代休時の計算設定 */
	@Column(name="CALC_SET_TIME_SUBHD")
	public boolean calcSetTimeSubhd;

	/** 非勤務日計算.設定 */
	@Column(name="FLEX_NOWORKING_CALC")
	public boolean flexNoworkingCalc;
	
	@Override
	protected Object getKey() {
		return kshstFlexSetPK;
	}
}
