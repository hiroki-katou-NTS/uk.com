package nts.uk.ctx.at.request.app.find.application.requestofearch;



import lombok.Value;
import nts.uk.ctx.at.request.dom.setting.requestofeach.RequestAppDetailSetting;

@Value
public class AppConfigDetailDto {
	/**
	 *　会社Iｄ 	
	 */
	public String companyId;
	/**
	 * 申請種類
	 */
	public int appType;
	/**
	 * 備考
	 */
	public String memo;
	/**
	 * 利用区分
	 */
	public int userAtr;
	/**
	 * 休出時間申請の事前必須設定
	 */
	public int prerequisiteForpauseFlg;
	/**
	 * 残業申請の事前必須設定
	 */
	public int otAppSettingFlg;
	/**
	 * 時間年休申請の時刻計算を利用する
	 */
	public int holidayTimeAppCalFlg;
	/**
	 * 遅刻早退取消申請の実績取消
	 */
	public int lateOrLeaveAppCancelFlg;
	/**
	 * 遅刻早退取消申請の実績取消を申請時に選択
	 */
	public int lateOrLeaveAppSettingFlg;
	
	/**
	 *休憩入力欄を表示する
	 */
	public int breakInputFieldDisFlg;
	/**
	 * 休憩時間を表示する
	 */
	public int breakTimeDisFlg;	
	/**
	 * 出退勤時刻初期表示区分
	 */
	public int atworkTimeBeginDisFlg;	
	/**
	 * 実績から外出を初期表示する
	 */
	public int goOutTimeBeginDisFlg;
	
	/**
	 * 時刻計算利用区分
	 */
	public int timeCalUseAtr;
	/**
	 * 時間入力利用区分
	 */
	public int timeInputUseAtr;
	
	public int requiredInstructionFlg; 
	


	public static AppConfigDetailDto fromDomain(RequestAppDetailSetting domain) {
		return new  AppConfigDetailDto(
				domain.getCompanyId(),
				domain.getAppType().value,
				domain.getMemo().v(),
				domain.getUserAtr().value,
				domain.getPrerequisiteForpauseFlg().value,
				domain.getOtAppSettingFlg().value,
				domain.getHolidayTimeAppCalFlg().value,
				domain.getLateOrLeaveAppCancelFlg().value,
				domain.getLateOrLeaveAppSettingFlg().value,
				domain.getBreakInputFieldDisFlg().value,
				domain.getBreakTimeDisFlg().value,
				domain.getAtworkTimeBeginDisFlg().value,
				domain.getGoOutTimeBeginDisFlg().value,
				domain.getTimeCalUseAtr().value,
				domain.getTimeInputUseAtr().value,
				domain.getRequiredInstructionFlg().value);
	}


}
