package nts.uk.ctx.at.request.dom.setting.requestofearch;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.request.dom.application.common.ApplicationType;
import nts.uk.ctx.at.request.dom.application.common.UseAtr;
/**
 * 申請詳細設定
 * @author dudt
 *
 */
@Getter
@AllArgsConstructor
public class AppDetailSetting extends AggregateRoot {
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
	
	
	public static AppDetailSetting createSimpleFromJavaType(String companyId,
			int appType,
			String memo,
			int userAtr,
			int prerequisiteForpauseFlg,
			int otAppSettingFlg,
			int breakInputFieldDisFlg,
			int breakTimeDisFlg,
			int atworkTimeBeginDisFlg,
			int goOutTimeBeginDisFlg,
			int timeCalUseAtr,
			int timeInputUseAtr) {
				return new AppDetailSetting(companyId, 
						EnumAdaptor.valueOf(appType, ApplicationType.class),
						new Memo(memo),
						EnumAdaptor.valueOf(userAtr, UseAtr.class),
						EnumAdaptor.valueOf(prerequisiteForpauseFlg, SettingFlg.class),
						EnumAdaptor.valueOf(otAppSettingFlg, SettingFlg.class),
						EnumAdaptor.valueOf(breakInputFieldDisFlg, DisplayFlg.class),
						EnumAdaptor.valueOf(breakTimeDisFlg, DisplayFlg.class),
						EnumAdaptor.valueOf(atworkTimeBeginDisFlg, DisplayFlg.class),
						EnumAdaptor.valueOf(goOutTimeBeginDisFlg, DisplayFlg.class),
						EnumAdaptor.valueOf(timeCalUseAtr, UseAtr.class),
						EnumAdaptor.valueOf(timeInputUseAtr, UseAtr.class));
		
	}
	
}
