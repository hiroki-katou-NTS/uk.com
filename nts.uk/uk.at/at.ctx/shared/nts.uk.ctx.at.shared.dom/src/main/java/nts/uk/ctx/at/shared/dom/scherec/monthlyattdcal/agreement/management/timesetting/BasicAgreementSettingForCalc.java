package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeStatusOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.AgreementOneYearTime;

/** 集計用の３６協定基本設定 */
@AllArgsConstructor
public class BasicAgreementSettingForCalc {

	/** 36協定基本設定: ３６協定基本設定 */
	@Getter
	private BasicAgreementSetting basicSetting;
	
	/** 社員の上限設定あるか: boolean */
	private boolean haveEmployeeSetting;
	
	/** 個人の３６協定設定がセット済みにする */
	public void personAgreementSetted() {
		
		this.haveEmployeeSetting = true;
	}
	
	/** 月間エラーチェック */
	public AgreementTimeStatusOfMonthly checkForOneMonth(AttendanceTimeMonth agreementTarget, AttendanceTimeMonth legalLimitTarget) {
		
		/** エラーチェック */
		val state = this.basicSetting.getOneMonth().check(agreementTarget, legalLimitTarget);
		
		/** 「社員の年月上限設定あるか」を確認する */
		if(!haveEmployeeSetting) {
			
			/** 取得した「月別実績の36協定時間状態」を返す */
			return state;
		}
		
		/** 特例つけて返す */
		switch (state) {
			case NORMAL:
				return AgreementTimeStatusOfMonthly.NORMAL_SPECIAL;
			case EXCESS_LIMIT_ERROR:
				return AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ERROR_SP;
			case EXCESS_LIMIT_ALARM:
				return AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ALARM_SP;
			default:
				return state;
		}
	}
	
	/** 年間のエラーチェック */
	public AgreementTimeStatusOfMonthly checkForOneYear(AgreementOneYearTime agreementTarget, AgreementOneYearTime legalLimitTarget) {
		
		/** エラーチェック */
		val state = this.basicSetting.getOneYear().check(agreementTarget, legalLimitTarget);
		
		/** 「社員の年月上限設定あるか」を確認する */
		if(!haveEmployeeSetting) {
			
			/** 取得した「月別実績の36協定時間状態」を返す */
			return state;
		}
		
		/** 特例つけて返す */
		switch (state) {
			case NORMAL:
				return AgreementTimeStatusOfMonthly.NORMAL_SPECIAL;
			case EXCESS_LIMIT_ERROR:
				return AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ERROR_SP;
			case EXCESS_LIMIT_ALARM:
				return AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ALARM_SP;
			default:
				return state;
		}
	}
}