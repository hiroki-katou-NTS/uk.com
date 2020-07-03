package nts.uk.ctx.at.request.dom.setting.workplace;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.request.dom.setting.workplace.appuseset.ApplicationUseSetting;

/**
 * 申請承認機能設定
 * @author Doan Duy Hung
 *
 */
@Getter
@AllArgsConstructor
public class ApprovalFunctionSetting extends DomainObject {
	
	/**
	 * 休出時間申請の事前必須設定
	 */
	private SettingFlg prerequisiteForpause;
	
	/**
	 * 指示利用設定
	 */
	private InstructionUseSetting instructionUseSetting;
	
	/**
	 * 時間年休申請の時刻計算を利用する
	 */
	private SettingFlg holidayTimeAppCal;
	
	/**
	 * 残業申請の事前必須設定
	 */
	private SettingFlg overtimeAppSetting;
	
	/**
	 * 遅刻早退取消申請の実績取消
	 */
	private SettingFlg lateOrLeaveAppCancelFlg;
	 
	/**
	 * 遅刻早退取消申請の実績取消を申請時に選択
	 */
	private SettingFlg lateOrLeaveAppSettingFlg;
	
	/**
	 * 申請利用設定
	 */
	private ApplicationUseSetting appUseSetting;
	
	/**
	 * 申請詳細設定
	 * only available with overTime and holidayWork
	 */
	private Optional<ApplicationDetailSetting> applicationDetailSetting;
	
}
