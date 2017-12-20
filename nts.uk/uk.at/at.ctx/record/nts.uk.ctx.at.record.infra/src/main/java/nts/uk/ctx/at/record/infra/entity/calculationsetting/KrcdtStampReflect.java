package nts.uk.ctx.at.record.infra.entity.calculationsetting;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 
 * @author nampt 打刻反映管理
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCDT_STAMP_REFLECT")
public class KrcdtStampReflect extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcdtStampReflectPK krcdtStampReflectPK;

	// 休出切替区分
	@Column(name = "BREAK_SWITCH_ATR")
	public BigDecimal breakSwitchClass;

	// 自動打刻反映区分
	@Column(name = "AUTO_STAMP_REFLECT_ATR")
	public BigDecimal autoStampReflectionClass;

	// 実打刻と申請の優先区分
	@Column(name = "ACTUAL_STAMP_PRIORITY_ATR")
	public BigDecimal actualStampOfPriorityClass;

	// 就業時間帯打刻反映区分
	@Column(name = "REFLECT_WORKING_TIME_ATR")
	public BigDecimal reflectWorkingTimeClass;

	// 直行直帰外出補正区分
	@Column(name = "GO_BACK_OUT_CORRECTION_ATR")
	public BigDecimal goBackOutCorrectionClass;

	// 入退門の管理をする
	@Column(name = "MANAGEMENT_OF_ENTRANCE")
	public BigDecimal managementOfEntrance;
	
	// 未来日の自動打刻セット区分
	@Column(name = "AUTO_STAMP_FUTURE_DAY_ATR")
	public BigDecimal autoStampForFutureDayClass;

	@Override
	protected Object getKey() {
		return this.krcdtStampReflectPK;
	}

}
