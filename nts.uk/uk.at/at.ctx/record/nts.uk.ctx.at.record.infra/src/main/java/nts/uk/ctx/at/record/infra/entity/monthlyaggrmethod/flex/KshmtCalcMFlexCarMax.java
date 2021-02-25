package nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.flex;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.flexshortage.FlexShortageLimit;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * フレックス不足の繰越上限時間
 * @author thanhnx
 */
@Entity
@Table(name = "KSHMT_CALC_M_FLEX_CAR_MAX")
@NoArgsConstructor
public class KshmtCalcMFlexCarMax extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 会社ID */
	@Id
	@Column(name = "CID")
	public String companyId;
	
	/** 繰越上限時間 */
	@Column(name = "UPPER_LIMIT_TIME")
	public int upperLimitTime;
	
	@Override
	protected Object getKey() {
		return companyId;
	}

	public FlexShortageLimit toDomain(){
		return FlexShortageLimit.of(
				this.companyId,
				new AttendanceTime(this.upperLimitTime));
	}
}
