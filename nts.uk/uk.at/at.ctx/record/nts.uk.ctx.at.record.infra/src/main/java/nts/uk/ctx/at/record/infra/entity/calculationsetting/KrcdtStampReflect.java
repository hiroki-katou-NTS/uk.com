package nts.uk.ctx.at.record.infra.entity.calculationsetting;

import java.io.Serializable;

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
	public String breakSwitchClass;

	// 自動打刻反映区分
	@Column(name = "AUTO_STAMP_REFLECT_ATR")
	public String autoStampReflectionClass;

	// 実打刻と申請の優先区分
	@Column(name = "ACTUAL_STAMP_PRIORITY_ATR")
	public String actualStampOfPriorityClass;

	// 就業時間帯打刻反映区分
	@Column(name = "REFLECT_WORKING_TIME_ATR")
	public String reflectWorkingTimeClass;

	// 直行直帰外出補正区分
	@Column(name = "GO_BACK_OUT_CORRECTION_ATR")
	public String goBackOutCorrectionClass;

	// 入退門の管理をする
	@Column(name = "MANAGEMENT_OF_ENTRANCE")
	public String managementOfEntrance;
	
	// 未来日の自動打刻セット区分
	@Column(name = "AUTO_STAMP_FUTURE_DAY_ATR")
	public String autoStampForFutureDayClass;

	@Override
	protected Object getKey() {
		return this.krcdtStampReflectPK;
	}

}
