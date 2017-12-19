package nts.uk.ctx.at.shared.infra.entity.calculation.holiday;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSHST_FLEX_WORK_SET")
public class KshstFlexSet  extends UkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	/** 主キー */
	@EmbeddedId
	public KshstFlexSetPK kshstFlexSetPK;
	
	/** フレックス勤務を管理する */
	@Column(name = "MANAGING_FLEX_WORK")
	public String managingFlexWork;
	
	/** 設定 */
	@Column(name = "CONFIG_FLEX_WORK")
	public String configFlexWork;
	
	/** 不足計算 */
	@Column(name = "MISS_CALC_HD")
	public String missCalcHd;
	
	/** 割増計算 */
	@Column(name = "PREMIUM_CALC_HD")
	public String premiumCalcHd;
	
	/** 不足計算*/
	@Column(name = "MISS_CALC_SUBHD")
	public String missCalcSubhd;
	
	/** 割増計算 */
	@Column(name = "PREMIUM_CALC_SUBHD")
	public String premiumCalcSubhd;
	
	@Override
	protected Object getKey() {
		return kshstFlexSetPK;
	}
}
