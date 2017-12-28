package nts.uk.ctx.at.record.infra.entity.calculationsetting;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.calculationsetting.StampReflectionManagement;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 
 * @author nampt 打刻反映管理
 *
 */
@NoArgsConstructor
@Entity
@Table(name = "KRCMT_STAMP_IMPRINT")
public class KrcmtStampImprint extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcmtStampImprintPK krcdtStampReflectPK;

	/** 休出切替区分*/
	@Column(name = "BREAK_OF_SWITCH_ATR")
	public int breakSwitchClass;

	/** 自動打刻反映区分*/
	@Column(name = "AUTO_STAMP_REFLECT_ATR")
	public int autoStampReflectionClass;

	/**実打刻と申請の優先区分*/
	@Column(name = "ACTUAL_STAMP_PRIORITY_ATR")
	public int actualStampOfPriorityClass;

	/** 就業時間帯打刻反映区分*/
	@Column(name = "REFLECT_WORKING_TIME_ATR")
	public int reflectWorkingTimeClass;

	/** 直行直帰外出補正区分*/
	@Column(name = "GO_BACK_OUT_CORRECTION_ATR")
	public int goBackOutCorrectionClass;

	/** 入退門の管理をする*/
	@Column(name = "MANAGEMENT_OF_ENTRANCE")
	public int managementOfEntrance;
	
	/** 未来日の自動打刻セット区分*/
	@Column(name = "AUTO_STAMP_FUTURE_DAY_ATR")
	public int autoStampForFutureDayClass;
	
	/** 休憩として扱う外出区分*/
	@Column(name = "OUTING_ATR")
	public int outingAtr;
	
	/** 最大使用回数*/
	@Column(name = "MAX_USE_COUNT")
	public BigDecimal maxUseCount;

	@Override
	protected Object getKey() {
		return this.krcdtStampReflectPK;
	}

	public KrcmtStampImprint(KrcmtStampImprintPK krcdtStampReflectPK, int breakSwitchClass,
			int autoStampReflectionClass, int actualStampOfPriorityClass,
			int reflectWorkingTimeClass, int goBackOutCorrectionClass, int managementOfEntrance,
			int autoStampForFutureDayClass, int outingAtr, BigDecimal maxUseCount) {
		super();
		this.krcdtStampReflectPK = krcdtStampReflectPK;
		this.breakSwitchClass = breakSwitchClass;
		this.autoStampReflectionClass = autoStampReflectionClass;
		this.actualStampOfPriorityClass = actualStampOfPriorityClass;
		this.reflectWorkingTimeClass = reflectWorkingTimeClass;
		this.goBackOutCorrectionClass = goBackOutCorrectionClass;
		this.managementOfEntrance = managementOfEntrance;
		this.autoStampForFutureDayClass = autoStampForFutureDayClass;
		this.outingAtr = outingAtr;
		this.maxUseCount = maxUseCount;
	}
	public StampReflectionManagement toDomain() {
		return StampReflectionManagement.createJavaType(this.krcdtStampReflectPK.companyId, this.breakSwitchClass, this.autoStampReflectionClass, this.actualStampOfPriorityClass, this.reflectWorkingTimeClass, 
				this.goBackOutCorrectionClass, this.managementOfEntrance, this.autoStampForFutureDayClass, this.outingAtr, this.maxUseCount);
	}
	
	public static KrcmtStampImprint toEntity(StampReflectionManagement domain){
		return new KrcmtStampImprint(
				new KrcmtStampImprintPK(domain.getCompanyId()),
				domain.getBreakSwitchClass().value,domain.getAutoStampReflectionClass().value,domain.getActualStampOfPriorityClass().value, domain.getReflectWorkingTimeClass().value, domain.getGoBackOutCorrectionClass().value,
				domain.getManagementOfEntrance().value, domain.getAutoStampForFutureDayClass().value, domain.getOutingAtr().value, domain.getMaxUseCount());
	}
}
