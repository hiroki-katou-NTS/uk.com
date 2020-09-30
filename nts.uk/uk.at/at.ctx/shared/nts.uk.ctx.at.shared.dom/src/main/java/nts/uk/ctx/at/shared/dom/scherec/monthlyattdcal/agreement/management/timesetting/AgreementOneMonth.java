package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeStatusOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.ExcessState;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.AgreementOneMonthTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.OneMonthErrorAlarmTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.OneMonthTime;
import org.apache.commons.lang3.tuple.Pair;

/** 36協定1ヶ月 */
@AllArgsConstructor
@Getter
public class AgreementOneMonth {

	/** 基本設定 */
	private OneMonthTime basic;
	/** 特例条項による上限 */
	private OneMonthTime specConditionLimit;
	
	public AgreementOneMonth() {
		this.basic = new OneMonthTime();
		this.specConditionLimit = new OneMonthTime();
	}
	
	/** エラーチェック */
	public AgreementTimeStatusOfMonthly check(AttendanceTimeMonth agreementTarget,
			AttendanceTimeMonth legalLimitTarget) {
		/** エラーチェック */
		val legalState = this.specConditionLimit.check(legalLimitTarget);
		
		/** 法定上限対象時間の超過状態をチェック */
		if (legalState == ExcessState.ALARM_OVER) {
			
			return AgreementTimeStatusOfMonthly.EXCESS_EXCEPTION_LIMIT_ALARM;
		}
		if (legalState == ExcessState.ERROR_OVER) {
			
			return AgreementTimeStatusOfMonthly.EXCESS_EXCEPTION_LIMIT_ERROR;
		}
		if (legalState == ExcessState.UPPER_LIMIT_OVER) {
			
			return AgreementTimeStatusOfMonthly.EXCESS_BG_GRAY;
		}
		
		/** エラーチェック */
		val agreementState = this.basic.check(agreementTarget);
		
		/** 月別実績の36協定時間状態を判断 */
		if (agreementState == ExcessState.ALARM_OVER) {
			
			return AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ALARM;
		}
		if (agreementState == ExcessState.ERROR_OVER 
				|| agreementState == ExcessState.UPPER_LIMIT_OVER) {
			
			return AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ERROR;
		}
		
		return AgreementTimeStatusOfMonthly.NORMAL;
	}


	//	[2] 特例条項による上限のエラー時間を超えているか
	public Pair<Boolean, AgreementOneMonthTime> checkErrorTimeExceeded(AgreementOneMonthTime applicationTime) {

		return basic.isErrorTimeOver(applicationTime);
	}

	// 	[3] アラーム時間を計算する
	public AgreementOneMonthTime calculateAlarmTime(AgreementOneMonthTime applicationTime) {
		return basic.calcAlarmTime(applicationTime);
	}
	// 	[1] エラーチェック
	public AgreementTimeStatusOfMonthly checkError(AttendanceTimeMonth agreementTargetTime,
												   AttendanceTimeMonth hoursSubjectToLegalUpperLimit,
												   OneMonthErrorAlarmTime applicationTime) {
		//input.対象時間 <= エラーアラーム時間.アラーム時間：
		//        　超過状態←"正常"
		//        else
		//input.対象時間 <= エラーアラーム時間.エラー時間：
		//        　超過状態←"アラーム時間超過"
		//        else
		//input.対象時間 <= 上限時間：
		//       　超過状態←"エラー時間超過"
		//        else
		//        　超過状態←"上限時間超過"
		int limitTargetTime;
		if (hoursSubjectToLegalUpperLimit.lessThanOrEqualTo(basic.getErAlTime().getAlarm().v())) {
			limitTargetTime = ExcessState.NORMAL.value;
		} else if (hoursSubjectToLegalUpperLimit.lessThanOrEqualTo(basic.getErAlTime().getError().v())) {
			limitTargetTime = ExcessState.ALARM_OVER.value;
		} else if (hoursSubjectToLegalUpperLimit.lessThanOrEqualTo(specConditionLimit.getUpperLimit().v())) {
			limitTargetTime = ExcessState.ERROR_OVER.value;
		} else {
			limitTargetTime = ExcessState.UPPER_LIMIT_OVER.value;
		}

		int targetTimeOfAgreement;
		if (agreementTargetTime.lessThanOrEqualTo(basic.getErAlTime().getAlarm().v())) {
			targetTimeOfAgreement = ExcessState.NORMAL.value;
		} else if (agreementTargetTime.lessThanOrEqualTo(basic.getErAlTime().getError().v())) {
			targetTimeOfAgreement = ExcessState.ALARM_OVER.value;
		} else if (agreementTargetTime.lessThanOrEqualTo(specConditionLimit.getUpperLimit().v())) {
			targetTimeOfAgreement = ExcessState.ERROR_OVER.value;
		} else {
			targetTimeOfAgreement = ExcessState.UPPER_LIMIT_OVER.value;
		}

		if (limitTargetTime != ExcessState.NORMAL.value) {
			if (limitTargetTime == ExcessState.ALARM_OVER.value) {
				limitTargetTime = AgreementTimeStatusOfMonthly.EXCESS_EXCEPTION_LIMIT_ALARM.value;
			} else if (limitTargetTime == ExcessState.ERROR_OVER.value) {
				limitTargetTime = AgreementTimeStatusOfMonthly.EXCESS_EXCEPTION_LIMIT_ERROR.value;
			} else {
				limitTargetTime = AgreementTimeStatusOfMonthly.EXCESS_BG_GRAY.value;
			}
			return EnumAdaptor.valueOf(limitTargetTime, AgreementTimeStatusOfMonthly.class);
		} else {
			if (applicationTime == null) {
				// 超過状態=正常：
				//　月別実績の36協定時間状態←"正常"
				//超過状態=アラーム時間超過：
				// 　月別実績の36協定時間状態←"限度アラーム時間超過"
				//超過状態=エラー時間超過 or 超過状態=上限時間超過：
				//　月別実績の36協定時間状態←"限度エラー時間超過"
				if (targetTimeOfAgreement == ExcessState.NORMAL.value) {
					targetTimeOfAgreement = AgreementTimeStatusOfMonthly.NORMAL.value;
				} else if (targetTimeOfAgreement == ExcessState.ALARM_OVER.value) {
					targetTimeOfAgreement = AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ALARM.value;
				} else {
					targetTimeOfAgreement = AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ERROR.value;
				}
				return EnumAdaptor.valueOf(targetTimeOfAgreement, AgreementTimeStatusOfMonthly.class);
			} else {
				//超過状態=正常：
				//　月別実績の36協定時間状態←"正常(特例あり)"
				//超過状態=アラーム時間超過：
				//　月別実績の36協定時間状態←"限度アラーム時間超過(特例あり)"
				//超過状態=エラー時間超過 or 超過状態=上限時間超過：
				//　月別実績の36協定時間状態←"限度エラー時間超過(特例あり)"
				if (targetTimeOfAgreement == ExcessState.NORMAL.value) {
					targetTimeOfAgreement = AgreementTimeStatusOfMonthly.NORMAL_SPECIAL.value;
				} else if (targetTimeOfAgreement == ExcessState.ALARM_OVER.value) {
					targetTimeOfAgreement = AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ALARM_SP.value;
				} else {
					targetTimeOfAgreement = AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ERROR_SP.value;
				}
				return EnumAdaptor.valueOf(targetTimeOfAgreement, AgreementTimeStatusOfMonthly.class);
			}
		}
	}
}
