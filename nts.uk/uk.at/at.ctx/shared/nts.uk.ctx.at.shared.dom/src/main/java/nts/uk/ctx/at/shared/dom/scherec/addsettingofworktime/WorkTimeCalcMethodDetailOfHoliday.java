/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.DomainObject;
import nts.gul.serialize.binary.SerializableWithOptional;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.worktime.common.GraceTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktype.AttendanceDayAttr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * The Class WorkTimeCalcMethodDetailOfHoliday.
 */
// 休暇の就業時間計算方法詳細
@Getter
@NoArgsConstructor
public class WorkTimeCalcMethodDetailOfHoliday extends DomainObject implements SerializableWithOptional{

	/** Serializable */
	private static final long serialVersionUID = 1L;
	
	/** The include vacation set. */
	// 休暇分を含める設定
	private EmploymentCalcDetailedSetIncludeVacationAmount includeVacationSet;
	
	/** The calculate includ care time. */
	// 育児・介護時間を含めて計算する
	private NotUseAtr calculateIncludCareTime;
	
	/** The not deduct late leave early. */
	// 遅刻・早退を控除する
	private DeductLeaveEarly notDeductLateLeaveEarly;
	
	/** The calculate includ interval exemption time. */
	// インターバル免除時間を含めて計算する
	private NotUseAtr calculateIncludIntervalExemptionTime;
	
	/** The minus absence time. */
	// 欠勤時間をマイナスする
	private Optional<NotUseAtr> minusAbsenceTime;

	private void writeObject(ObjectOutputStream stream){
		writeObjectWithOptional(stream);
	}
	
	private void readObject(ObjectInputStream stream){
		readObjectWithOptional(stream);
	}

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
			Integer calculateIncludCareTime, DeductLeaveEarly notDeductLateLeaveEarly,
			Integer calculateIncludIntervalExemptionTime, Integer minusAbsenceTime) {
		super();
		this.includeVacationSet = includeVacationSet;
		this.calculateIncludCareTime = NotUseAtr.valueOf(calculateIncludCareTime);
		this.notDeductLateLeaveEarly = notDeductLateLeaveEarly;
		this.calculateIncludIntervalExemptionTime = NotUseAtr.valueOf(calculateIncludIntervalExemptionTime);
		this.minusAbsenceTime = Optional.ofNullable(minusAbsenceTime == null 
															? null 
															: NotUseAtr.valueOf(minusAbsenceTime));
	}
	
	public WorkTimeCalcMethodDetailOfHoliday(EmploymentCalcDetailedSetIncludeVacationAmount includeVacationSet,
											 NotUseAtr calculateIncludCareTime,
											 DeductLeaveEarly notDeductLateLeaveEarly,
											 NotUseAtr calculateIncludIntervalExemptionTime,
											 Optional<NotUseAtr> minusAbsenceTime) {
		super();
		this.includeVacationSet = includeVacationSet;
		this.calculateIncludCareTime = calculateIncludCareTime;
		this.notDeductLateLeaveEarly = notDeductLateLeaveEarly;
		this.calculateIncludIntervalExemptionTime = calculateIncludIntervalExemptionTime;
		this.minusAbsenceTime = minusAbsenceTime;
	}
	
	
	/**
	 * 就業時間内時間帯から控除するか判断
	 * @param deductTime
	 * @param graceTimeSetting
	 * @return
	 */
	public boolean decisionLateDeductSetting(AttendanceTime deductTime, GraceTimeSetting graceTimeSetting, Optional<WorkTimezoneCommonSet> commonSetting) {
//		if(this.notDeductLateLeaveEarly==NotUseAtr.USE) {//
		if(isDeductLateLeaveEarly(commonSetting)) {//遅刻早退をマイナスする場合に処理に入る
			if(deductTime.greaterThan(0) || !graceTimeSetting.isIncludeWorkingHour()) {//猶予時間の加算設定をチェック&&パラメータ「遅刻控除時間」の確認
				return true;
			}
		}
		return false;	
	}

	/**
	 * 就業時間内時間帯から控除するか判断
	 * @param deductTime 控除時間
	 * @param graceTimeSetting 猶予時間
	 * @param 就業時間帯の共通設定
	 * @param 勤務種類
	 * @return
	 */
	public boolean decisionLateDeductSetting(AttendanceTime deductTime, GraceTimeSetting graceTimeSetting, WorkTimezoneCommonSet commonSetting, WorkType workType) {

		//出勤系ではない場合
		if(workType.chechAttendanceDay().equals(AttendanceDayAttr.HOLIDAY) || workType.chechAttendanceDay().equals(AttendanceDayAttr.HOLIDAY_WORK)) {
			return false;
		}
		if(isDeductLateLeaveEarly(Optional.of(commonSetting))) {//遅刻早退をマイナスする場合に処理に入る
			if(deductTime.greaterThan(0) || !graceTimeSetting.isIncludeWorkingHour()) {//猶予時間の加算設定をチェック&&パラメータ「遅刻控除時間」の確認
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
	 * @return 控除する場合はtrueが返る
	 */
	public boolean isDeductLateLeaveEarly(Optional<WorkTimezoneCommonSet> commonSetting) {

		//就業時間帯ごとに設定を見る
		if(this.notDeductLateLeaveEarly.isEnableSetPerWorkHour()) {
			//就業時間帯「遅刻・早退」の詳細タブの「控除する」にチェック有かどうか
			if(commonSetting.isPresent() && commonSetting.get().getLateEarlySet().getCommonSet().isDelFromEmTime()) {
				return true;
			}
			return false;
		}
		//計算設定の設定のみ見る
		else {
			//遅刻・早退をマイナスしない□(チェック無し = 控除する)の場合、ここはfalseが来る
			if (!this.notDeductLateLeaveEarly.isDeduct()) {
				return true;
			}
			//遅刻・早退をマイナスしない☑(チェック有り = 控除しない)の場合、ここはtrueが来る
			else {
				return false;
			}
		}
	}
	
	/**
	 * 育児・介護時間を含めて計算する
	 * @return true：含める、false：含めない
	 */
	public boolean isCalculateIncludCareTime() {
		return this.calculateIncludCareTime.equals(NotUseAtr.USE);
	}
}

