package nts.uk.ctx.at.request.app.command.setting.requestofeach;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.request.dom.setting.requestofeach.RequestAppDetailSetting;

@Data
@AllArgsConstructor
public class RequestAppDetailSettingCommand {
	private String companyId;
	/**申請種類*/
	private int appType;
	/**
	 * 備考
	 */
	private String memo;
	/**
	 * 利用区分
	 */
	private int useAtr;
	/**
	 * 休出時間申請の事前必須設定
	 */
	private int prerequisiteForpauseFlg;
	/**
	 * 残業申請の事前必須設定
	 */
	private int otAppSettingFlg;
	/**
	 * 時間年休申請の時刻計算を利用する
	 */
	private int holidayTimeAppCalFlg;
	/**
	 * 遅刻早退取消申請の実績取消
	 */
	private int lateOrLeaveAppCancelFlg;
	/**
	 * 遅刻早退取消申請の実績取消を申請時に選択
	 */
	private int lateOrLeaveAppSettingFlg; 
		
	/**
	 * 休憩入力欄を表示する
	 */
	private int breakInputFieldDisFlg;
	/**
	 * 休憩時間を表示する
	 */
	private int breakTimeDisFlg;
	/**
	 * 出退勤時刻初期表示区分
	 */
	private int atworkTimeBeginDisFlg;
	/**
	 * 実績から外出を初期表示する
	 */
	private int goOutTimeBeginDisFlg;
	/**
	 * 時刻計算利用区分
	 */
	private int timeCalUseAtr;	
	/**
	 * 時間入力利用区分
	 */
	private int timeInputUseAtr;
	/**
	 * 退勤時刻初期表示区分
	 */
	private int timeEndDispFlg;
	/**
	 * 指示が必須
	 */
	private int requiredInstructionFlg;
	/**
	 * 指示利用設定 - 指示区分
	 */
	private int instructionAtr;
	/**
	 * 指示利用設定 - 備考
	 */
	private String instructionMemo;
	/**
	 * 指示利用設定 - 利用区分
	 */
	private int instructionUseAtr;
	
	public RequestAppDetailSetting toDomainDetail(String companyId) {
		return RequestAppDetailSetting.createSimpleFromJavaType(companyId,
				appType, memo,
				useAtr, prerequisiteForpauseFlg,
				otAppSettingFlg, holidayTimeAppCalFlg,
				lateOrLeaveAppCancelFlg, lateOrLeaveAppSettingFlg,
				breakInputFieldDisFlg, breakTimeDisFlg, 
				atworkTimeBeginDisFlg, goOutTimeBeginDisFlg, 
				timeCalUseAtr, timeInputUseAtr,
				requiredInstructionFlg, instructionAtr,
				instructionMemo, instructionUseAtr,
				timeEndDispFlg);
	}
}
