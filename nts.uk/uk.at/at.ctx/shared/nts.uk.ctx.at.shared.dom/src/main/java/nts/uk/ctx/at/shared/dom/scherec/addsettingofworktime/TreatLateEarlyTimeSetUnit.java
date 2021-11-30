/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime;

import java.io.Serializable;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.worktime.common.TreatLateEarlyTime;
import nts.uk.ctx.at.shared.dom.worktime.common.LateEarlyAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneLateEarlySet;
import nts.uk.ctx.at.shared.dom.worktype.AttendanceDayAttr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

/**
 * 遅刻早退時間の扱い設定単位
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TreatLateEarlyTimeSetUnit extends DomainObject implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/** 扱い設定 */
	private TreatLateEarlyTime treatSet = new TreatLateEarlyTime();
	/** 就業時間帯毎の設定を可能とする */
	private boolean enableSetPerWorkHour = false;

	private static final int TRUE_CONST = 1;
	
	public static TreatLateEarlyTimeSetUnit createFromJavaType(
			int include,
			int includeByApp,
			int enableSetPerWorkHour){
		TreatLateEarlyTimeSetUnit myClass = new TreatLateEarlyTimeSetUnit();
		myClass.treatSet = new TreatLateEarlyTime((include == TRUE_CONST), (includeByApp == TRUE_CONST));
		myClass.enableSetPerWorkHour = (enableSetPerWorkHour == TRUE_CONST);
		return myClass;
	}
	
	/**
	 * 就業時間に含めない設定を作成する
	 * @return 遅刻早退時間の扱い設定単位
	 */
	public static TreatLateEarlyTimeSetUnit createNotInclude() {
		return new TreatLateEarlyTimeSetUnit(new TreatLateEarlyTime(false, false), false);
	}
	
	/**
	 * 控除判断処理
	 * @param deductTime 控除時間
	 * @param lateEarlyAtr 控除区分
	 * @param timeLeavingWork 出退勤
	 * @param workType 勤務種類
	 * @param lateEarlySet 就業時間帯の遅刻・早退設定
	 * @return true:控除する,false:控除しない
	 */
	public boolean decisionLateDeductSetting(
			AttendanceTime deductTime,
			LateEarlyAtr lateEarlyAtr,
			TimeLeavingWork timeLeavingWork,
			WorkType workType,
			WorkTimezoneLateEarlySet lateEarlySet) {

		// 出勤系ではない場合
		if (workType.chechAttendanceDay().equals(AttendanceDayAttr.HOLIDAY) ||
			workType.chechAttendanceDay().equals(AttendanceDayAttr.HOLIDAY_WORK)) {
			return false;
		}
		// 遅刻早退を控除するか判断する
		if (this.decisionLateDeductSetting(lateEarlyAtr, timeLeavingWork, Optional.of(lateEarlySet)) == false){
			return false;
		}
		// 「控除時間」の確認
		if (deductTime.greaterThan(0)) return true;
		// 猶予時間の加算設定を取得
		if (lateEarlySet.getOtherEmTimezoneLateEarlySet(lateEarlyAtr).getGraceTimeSet().isIncludeWorkingHour()){
			return false;
		}
		return true;
	}
	
	/**
	 * 遅刻早退を控除するか判断する
	 * @param lateEarlyAtr 控除区分
	 * @param timeLeavingWork 出退勤
	 * @param lateEarlySet 就業時間帯の遅刻・早退設定
	 * @return true:控除する,false:控除しない
	 */
	private boolean decisionLateDeductSetting(
			LateEarlyAtr lateEarlyAtr,
			TimeLeavingWork timeLeavingWork,
			Optional<WorkTimezoneLateEarlySet> lateEarlySet) {
		
		// 遅刻早退を就業時間に含めるか判断する
		if (this.isIncludeLateEarlyInWorkTime(lateEarlySet) == false){
			// 取り消した場合の判断
			if (this.treatSet.isIncludeByApp() == false) return true;
			// 取り消しているかを判断
			boolean isCanceled = false;
			if (lateEarlyAtr.isLATE()){
				isCanceled = timeLeavingWork.isCanceledLate();
			}
			else {
				isCanceled = timeLeavingWork.isCanceledEarlyLeave();
			}
			if (isCanceled == false) return true;
		}
		// 「控除しない」を返す
		return false;	
	}

	/**
	 * 遅刻早退を就業時間に含めるか判断する
	 * @param lateEarlySet 就業時間帯の遅刻早退設定
	 * @return true:含める,false:含めない
	 */
	public boolean isIncludeLateEarlyInWorkTime(Optional<WorkTimezoneLateEarlySet> lateEarlySet) {
		if (this.enableSetPerWorkHour) {
			// 就業時間帯ごとに設定を見る
			if (lateEarlySet.isPresent() &&
				lateEarlySet.get().getCommonSet().isIncludeLateEarlyInWorkTime()) {
				// 就業時間帯「遅刻・早退」の詳細タブの「含める」にチェックあり
				return true;
			}
			return false;
		}else {
			// 計算設定を見る
			if (this.treatSet.isIncludeLateEarlyInWorkTime()) {
				// 遅刻・早退をマイナスしない☑(チェック有り = 含める)
				return true;
			}else {
				// 遅刻・早退をマイナスしない□(チェック無し = 含めない)
				return false;
			}
		}
	}
}

