/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.entity.calculationsetting;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.calculationsetting.StampReflectionManagement;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class KrcmtStampImprint.
 */
@NoArgsConstructor
@Entity
@Table(name = "KRCMT_STAMP_IMPRINT")
public class KrcmtStampImprint extends ContractUkJpaEntity implements Serializable {

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
	
	/** 未来日の自動打刻セット区分*/
	@Column(name = "AUTO_STAMP_FUTURE_DAY_ATR")
	public int autoStampForFutureDayClass;

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.krcdtStampReflectPK;
	}

	/**
	 * Instantiates a new krcmt stamp imprint.
	 *
	 * @param krcdtStampReflectPK the krcdt stamp reflect PK
	 * @param breakSwitchClass the break switch class
	 * @param autoStampReflectionClass the auto stamp reflection class
	 * @param actualStampOfPriorityClass the actual stamp of priority class
	 * @param reflectWorkingTimeClass the reflect working time class
	 * @param goBackOutCorrectionClass the go back out correction class
	 * @param autoStampForFutureDayClass the auto stamp for future day class
	 */
	public KrcmtStampImprint(KrcmtStampImprintPK krcdtStampReflectPK, int breakSwitchClass,
			int autoStampReflectionClass, int actualStampOfPriorityClass,
			int reflectWorkingTimeClass, int goBackOutCorrectionClass, 
			int autoStampForFutureDayClass
			) {
		super();
		this.krcdtStampReflectPK = krcdtStampReflectPK;
		this.breakSwitchClass = breakSwitchClass;
		this.autoStampReflectionClass = autoStampReflectionClass;
		this.actualStampOfPriorityClass = actualStampOfPriorityClass;
		this.reflectWorkingTimeClass = reflectWorkingTimeClass;
		this.goBackOutCorrectionClass = goBackOutCorrectionClass;
		this.autoStampForFutureDayClass = autoStampForFutureDayClass;
	}
	
	/**
	 * To domain.
	 *
	 * @return the stamp reflection management
	 */
	public StampReflectionManagement toDomain() {
		return StampReflectionManagement.createJavaType(this.krcdtStampReflectPK.companyId, this.breakSwitchClass, this.autoStampReflectionClass, this.actualStampOfPriorityClass, this.reflectWorkingTimeClass, 
				this.goBackOutCorrectionClass, 
				this.autoStampForFutureDayClass
				);
	}
	
	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the krcmt stamp imprint
	 */
	public static KrcmtStampImprint toEntity(StampReflectionManagement domain){
		return new KrcmtStampImprint(
				new KrcmtStampImprintPK(domain.getCompanyId()),
				domain.getBreakSwitchClass().value,domain.getAutoStampReflectionClass().value,domain.getActualStampOfPriorityClass().value, domain.getReflectWorkingTimeClass().value, domain.getGoBackOutCorrectionClass().value,
				domain.getAutoStampForFutureDayClass().value
				);
	}
}
