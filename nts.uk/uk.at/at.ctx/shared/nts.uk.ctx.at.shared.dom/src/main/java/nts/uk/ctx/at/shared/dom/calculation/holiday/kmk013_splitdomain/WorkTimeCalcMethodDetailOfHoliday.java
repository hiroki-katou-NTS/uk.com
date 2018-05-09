/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.calculation.holiday.kmk013_splitdomain;

import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.worktime.common.GraceTimeSetting;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * The Class WorkTimeCalcMethodDetailOfHoliday.
 */
// 休暇の就業時間計算方法詳細
@Getter
@NoArgsConstructor
public class WorkTimeCalcMethodDetailOfHoliday extends DomainObject{
	
	/** The include vacation set. */
	// 休暇分を含める設定
	private EmploymentCalcDetailedSetIncludeVacationAmount includeVacationSet;
	
	/** The calculate includ care time. */
	// 育児・介護時間を含めて計算する
	private NotUseAtr calculateIncludCareTime;
	
	/** The not deduct late leave early. */
	// 遅刻・早退を控除する
	private NotUseAtr notDeductLateLeaveEarly;
	
	/** The calculate includ interval exemption time. */
	// インターバル免除時間を含めて計算する
	private NotUseAtr calculateIncludIntervalExemptionTime;
	
	/** The minus absence time. */
	// 欠勤時間をマイナスする
	private Optional<NotUseAtr> minusAbsenceTime;

	/**
	 * Instantiates a new work time calc method detail of holiday.
	 *
	 * @param includeVacationSet the include vacation set
	 * @param calculateIncludCareTime the calculate includ care time
	 * @param notDeductLateLeaveEarly the not deduct late leave early
	 * @param calculateIncludIntervalExemptionTime the calculate includ interval exemption time
	 * @param minusAbsenceTime the minus absence time
	 */
	public WorkTimeCalcMethodDetailOfHoliday(EmploymentCalcDetailedSetIncludeVacationAmount includeVacationSet,
			Integer calculateIncludCareTime, Integer notDeductLateLeaveEarly,
			Integer calculateIncludIntervalExemptionTime, Integer minusAbsenceTime) {
		super();
		this.includeVacationSet = includeVacationSet;
		this.calculateIncludCareTime = NotUseAtr.valueOf(calculateIncludCareTime);
		this.notDeductLateLeaveEarly = NotUseAtr.valueOf(notDeductLateLeaveEarly);
		this.calculateIncludIntervalExemptionTime = NotUseAtr.valueOf(calculateIncludIntervalExemptionTime);
		this.minusAbsenceTime = Optional.ofNullable(minusAbsenceTime == null 
															? null 
															: NotUseAtr.valueOf(minusAbsenceTime));
	}
	
	
	
	/**
	 * 就業時間内時間帯から控除するか判断
	 * @param deductTime
	 * @param graceTimeSetting
	 * @return
	 */
	public boolean decisionLateDeductSetting(AttendanceTime deductTime, GraceTimeSetting graceTimeSetting) {
		if(this.notDeductLateLeaveEarly==NotUseAtr.USE) {//早退設定を控除項目にするかをチェックする
			if(deductTime.greaterThan(0) || !graceTimeSetting.isIncludeWorkingHour()) {
				return true;
			}
		}
		return false;	
	}
	
	/**
	 * 遅刻・早退を控除するか判断する
	 * 2018/05/09　高須
	 * caseがUSEの場合にわざとfalseにしています
	 * 画面上で「遅刻早退をマイナスしない」のチェックボックスでチェックがある場合にここにUSEが来る為です
	 * @return
	 */
	public boolean isDeductLateLeaveEarly() {
		switch(this.notDeductLateLeaveEarly) {
			case USE:
				return false;
			case NOT_USE:
				return true;
			default:
				throw new RuntimeException("unknown notDeductLateLeaveEarly");
		}	
	}
	
	
	
}

