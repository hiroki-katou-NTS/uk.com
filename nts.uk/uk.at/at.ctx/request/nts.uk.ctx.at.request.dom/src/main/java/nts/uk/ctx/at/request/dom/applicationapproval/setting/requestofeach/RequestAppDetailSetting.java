package nts.uk.ctx.at.request.dom.applicationapproval.setting.requestofeach;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.request.dom.applicationapproval.application.ApplicationType;
import nts.uk.ctx.at.request.dom.applicationapproval.application.UseAtr;
/**
 * 申請詳細設定
 * @author dudt
 *
 */
@Getter
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class RequestAppDetailSetting extends DomainObject {
	/**
	 *　会社Iｄ 	
	 */
	public String companyId;
	/**
	 * 申請種類
	 */
	public ApplicationType appType;
	/**
	 * 備考
	 */
	public Memo memo;
	/**
	 * 利用区分
	 */
	public UseAtr userAtr;
	/**
	 * 休出時間申請の事前必須設定
	 */
	public SettingFlg prerequisiteForpauseFlg;
	/**
	 * 残業申請の事前必須設定
	 */
	public SettingFlg otAppSettingFlg;
	/**
	 * 時間年休申請の時刻計算を利用する
	 */
	public SettingFlg holidayTimeAppCalFlg;
	/**
	 * 遅刻早退取消申請の実績取消
	 */
	public SettingFlg lateOrLeaveAppCancelFlg;
	/**
	 * 遅刻早退取消申請の実績取消を申請時に選択
	 */
	public SettingFlg lateOrLeaveAppSettingFlg;
	
	/**
	 *休憩入力欄を表示する
	 */
	public DisplayFlg breakInputFieldDisFlg;
	/**
	 * 休憩時間を表示する
	 */
	public DisplayFlg breakTimeDisFlg;	
	/**
	 * 出退勤時刻初期表示区分
	 */
	public DisplayFlg atworkTimeBeginDisFlg;	
	/**
	 * 実績から外出を初期表示する
	 */
	public DisplayFlg goOutTimeBeginDisFlg;
	
	/**
	 * 時刻計算利用区分
	 */
	public UseAtr timeCalUseAtr;
	/**
	 * 時間入力利用区分
	 */
	public UseAtr timeInputUseAtr;
	
	public SettingFlg requiredInstructionFlg; 
	
	public static RequestAppDetailSetting createSimpleFromJavaType(String companyId,
			int appType,
			String memo,
			int useAtr,
			int prerequisiteForpauseFlg,
			int otAppSettingFlg,
			int holidayTimeAppCalFlg,
			int lateOrLeaveAppCancelFlg,
			int lateOrLeaveAppSettingFlg,
			int breakInputFieldDisFlg,
			int breakTimeDisFlg,
			int atworkTimeBeginDisFlg,
			int goOutTimeBeginDisFlg,
			int timeCalUseAtr,
			int timeInputUseAtr,
			int requiredInstructionFlg) {
				return new RequestAppDetailSetting(companyId, 
						EnumAdaptor.valueOf(appType, ApplicationType.class),
						new Memo(memo),
						EnumAdaptor.valueOf(useAtr, UseAtr.class),
						EnumAdaptor.valueOf(prerequisiteForpauseFlg, SettingFlg.class),
						EnumAdaptor.valueOf(otAppSettingFlg, SettingFlg.class),
						EnumAdaptor.valueOf(holidayTimeAppCalFlg, SettingFlg.class),
						EnumAdaptor.valueOf(lateOrLeaveAppCancelFlg, SettingFlg.class),
						EnumAdaptor.valueOf(lateOrLeaveAppSettingFlg, SettingFlg.class),
						EnumAdaptor.valueOf(breakInputFieldDisFlg, DisplayFlg.class),
						EnumAdaptor.valueOf(breakTimeDisFlg, DisplayFlg.class),
						EnumAdaptor.valueOf(atworkTimeBeginDisFlg, DisplayFlg.class),
						EnumAdaptor.valueOf(goOutTimeBeginDisFlg, DisplayFlg.class),
						EnumAdaptor.valueOf(timeCalUseAtr, UseAtr.class),
						EnumAdaptor.valueOf(timeInputUseAtr, UseAtr.class),
						EnumAdaptor.valueOf(requiredInstructionFlg, SettingFlg.class));
		
	}
	
}
