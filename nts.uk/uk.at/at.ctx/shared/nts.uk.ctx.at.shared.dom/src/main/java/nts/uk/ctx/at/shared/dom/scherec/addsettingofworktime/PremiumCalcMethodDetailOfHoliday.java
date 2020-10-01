/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime;

import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * The Class PremiumCalcMethodDetailOfHoliday.
 */
// 休暇の割増計算方法詳細
@NoArgsConstructor
@Getter
public class PremiumCalcMethodDetailOfHoliday extends DomainObject{
	// 休暇分を含める設定
	private IncludeHolidaysPremiumCalcDetailSet includeVacationSet;
	
	// 育児・介護時間を含めて計算する
	private NotUseAtr calculateIncludCareTime;
	
	// 遅刻・早退を控除しない
	private DeductLeaveEarly notDeductLateLeaveEarly;
	
	// インターバル免除時間を含めて計算する
	private NotUseAtr calculateIncludIntervalExemptionTime;

	/**
	 * @param includeVacationSet
	 * @param calculateIncludCareTime
	 * @param notDeductLateLeaveEarly
	 * @param calculateIncludIntervalExemptionTime
	 */
	public PremiumCalcMethodDetailOfHoliday(IncludeHolidaysPremiumCalcDetailSet includeVacationSet,
			int calculateIncludCareTime, DeductLeaveEarly notDeductLateLeaveEarly,
			int calculateIncludIntervalExemptionTime) {
		super();
		this.includeVacationSet = includeVacationSet;
		this.calculateIncludCareTime = NotUseAtr.valueOf(calculateIncludCareTime);
		this.notDeductLateLeaveEarly = notDeductLateLeaveEarly;
		this.calculateIncludIntervalExemptionTime = NotUseAtr.valueOf(calculateIncludIntervalExemptionTime);
	}
	
	/**
	 * 遅刻・早退を控除するか判断する
	 * @param commonSetting 就業時間帯の共通設定
	 * @return 控除する場合はtrueが返る
	 */
	public boolean isDeductLateLeaveEarly(Optional<WorkTimezoneCommonSet> commonSetting) {
		if(this.notDeductLateLeaveEarly.isEnableSetPerWorkHour()) {
			//就業時間帯ごとに設定を見る
			if(commonSetting.isPresent() && commonSetting.get().getLateEarlySet().getCommonSet().isDelFromEmTime()) {
				//就業時間帯「遅刻・早退」の詳細タブの「控除する」にチェック有り
				return true;
			}
			return false;
		}else {
			//計算設定を見る
			if (!this.notDeductLateLeaveEarly.isDeduct()) {
				//遅刻・早退をマイナスしない□(チェック無し = 控除する)
				return true;
			}else {
				//遅刻・早退をマイナスしない☑(チェック有り = 控除しない)
				return false;
			}
		}
	}
	
	/**
	 * 「休暇の就業時間計算方法詳細」を「休暇の割増計算方法詳細」に変換する
	 * @param workTimeCalcMethod 休暇の就業時間計算方法詳細
	 * @return 休暇の割増計算方法詳細
	 */
	public Optional<PremiumCalcMethodDetailOfHoliday> of(Optional<WorkTimeCalcMethodDetailOfHoliday> workTimeCalcMethod) {
		if(!workTimeCalcMethod.isPresent())
			return Optional.empty();
		
		return Optional.of(new PremiumCalcMethodDetailOfHoliday(
				this.includeVacationSet.of(workTimeCalcMethod.get().getIncludeVacationSet()),
				workTimeCalcMethod.get().getCalculateIncludCareTime().value,
				workTimeCalcMethod.get().getNotDeductLateLeaveEarly(),
				workTimeCalcMethod.get().getCalculateIncludIntervalExemptionTime().value));
	}
	
	/**
	 * 育児・介護時間を含めて計算する
	 * @return true：含める、false：含めない
	 */
	public boolean isCalculateIncludCareTime() {
		return this.calculateIncludCareTime.equals(NotUseAtr.USE);
	}
}

