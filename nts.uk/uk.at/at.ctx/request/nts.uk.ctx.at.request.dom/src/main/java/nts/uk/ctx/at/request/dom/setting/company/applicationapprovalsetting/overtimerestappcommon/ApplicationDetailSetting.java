package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime;
import nts.uk.shr.com.enumcommon.NotUseAtr;

import java.util.Optional;

import org.apache.commons.lang3.BooleanUtils;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.残業休出申請共通設定.申請詳細設定
 * @author Doan Duy Hung
 *
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationDetailSetting {
	
	/**
	 * 指示が必須
	 */
	private Boolean requiredInstruction; 
	
	/**
	 * 事前必須設定
	 */
	private NotUseAtr preRequireSet;
	
	/**
	 * 時間入力利用区分
	 */
	private NotUseAtr timeInputUse;
	
	/**
	 * 時刻計算利用区分
	 */
	private NotUseAtr timeCalUse;
	
	/**
	 * 出退勤時刻初期表示区分
	 */
	private AtWorkAtr atworkTimeBeginDisp;
	
	/**
	 * 退勤時刻がない時システム時刻を表示するか
	 */
	private boolean dispSystemTimeWhenNoWorkTime;

	public static ApplicationDetailSetting create(Integer requiredInstruction, int preRequiredSet, int timeInputUse, int timeCalUse, int atWorkTimeBeginDisplay, int dispSystemTimeWhenNoWorkTime) {
		return new ApplicationDetailSetting(
				BooleanUtils.toBooleanObject(requiredInstruction),
				EnumAdaptor.valueOf(preRequiredSet, NotUseAtr.class),
				EnumAdaptor.valueOf(timeInputUse, NotUseAtr.class),
				EnumAdaptor.valueOf(timeCalUse, NotUseAtr.class),
				EnumAdaptor.valueOf(atWorkTimeBeginDisplay, AtWorkAtr.class),
				BooleanUtils.toBoolean(dispSystemTimeWhenNoWorkTime)
		);
	}
	/**
	 * Refactor5 事前申請が必須か確認する
	 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.残業休出申請共通設定.アルゴリズム.事前申請が必須か確認する
	 * @param appType
	 * @param prePostAtr
	 * @param overTime
	 * @param holiday
	 */
	public void checkAdvanceApp(
			ApplicationType appType,
			PrePostAtr prePostAtr,
			Optional<AppOverTime> overTime,
			Optional<AppOverTime> holiday
			) {
		// INPUT．「事前事後区分」を確認する
		if (prePostAtr == PrePostAtr.PREDICT) return;
		// 「@事前必須設定」を確認する
		if (this.preRequireSet != NotUseAtr.USE) return;
		if (appType == ApplicationType.OVER_TIME_APPLICATION) { // 申請種類 = 残業
			// INPUT．「残業申請」を確認する
			if (!overTime.isPresent()) {
				// エラーメッセージ（Msg_1656）を表示する
				throw new BusinessException("MSg_1656");
				
			}
			
		}
		if (appType == ApplicationType.HOLIDAY_WORK_APPLICATION) { // 申請種類 = 休出時間申請
			if (!holiday.isPresent()) {
				// エラーメッセージ（Msg_1656）を表示する
				throw new BusinessException("MSg_1656");
			}
		}
		
		return;
		
		
	}
	
}
