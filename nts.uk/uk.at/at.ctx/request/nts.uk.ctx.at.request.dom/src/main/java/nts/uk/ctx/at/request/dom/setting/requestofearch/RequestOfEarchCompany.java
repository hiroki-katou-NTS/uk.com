package nts.uk.ctx.at.request.dom.setting.requestofearch;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.request.dom.application.common.ApplicationType;
import nts.uk.ctx.at.request.dom.application.common.UseAtr;
/**
 * 会社別申請承認設定
 * @author dudt
 *
 */
@Getter
@AllArgsConstructor
public class RequestOfEarchCompany extends AggregateRoot {
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
	 * 申請時の承認者の選択
	 */
	public SelectionFlg selectOfApproversFlg;
	/**
	 * 残業申請休憩入力欄を表示する
	 */
	public SettingFlg appUseSettingFlg;
	/**
	 * 残業申請の申請詳細設定
	 */
	public AppDetailSetting otDetailSetting;
	/**
	 * 休出申請の申請詳細設定　(hw: holiday work)
	 */
	public AppDetailSetting hwDetailSetting;
	
	public static RequestOfEarchCompany createSimpleFromJavaType(String companyId,
			int appType,
			String memo,
			int userAtr,
			int prerequisiteForpauseFlg,
			int otAppSettingFlg,
			int selectOfApproversFlg,
			int appUseSettingFlg,
			int otBreakInputFieldDisFlg,
			int otBreakTimeDisFlg,
			int otAtworkTimeBeginDisFlg,
			int otGoOutTimeBeginDisFlg,
			int otTimeCalUseAtr,
			int otTimeInputUseAtr,
			int hwBreakInputFieldDisFlg,
			int hwBreakTimeDisFlg,
			int hwAtworkTimeBeginDisFlg,
			int hwGoOutTimeBeginDisFlg,
			int hwTimeCalUseAtr,
			int hwTimeInputUseAtr) {
				return new RequestOfEarchCompany(companyId, 
						EnumAdaptor.valueOf(appType, ApplicationType.class),
						new Memo(memo),
						EnumAdaptor.valueOf(userAtr, UseAtr.class),
						EnumAdaptor.valueOf(prerequisiteForpauseFlg, SettingFlg.class),
						EnumAdaptor.valueOf(otAppSettingFlg, SettingFlg.class),
						EnumAdaptor.valueOf(selectOfApproversFlg, SelectionFlg.class),
						EnumAdaptor.valueOf(appUseSettingFlg, SettingFlg.class),
						AppDetailSetting.appDetailSetting(otBreakInputFieldDisFlg, 
								otBreakTimeDisFlg, 
								otAtworkTimeBeginDisFlg, 
								otGoOutTimeBeginDisFlg,
								otTimeCalUseAtr,
								otTimeInputUseAtr),
						AppDetailSetting.appDetailSetting(hwBreakInputFieldDisFlg,
								hwBreakTimeDisFlg,
								hwAtworkTimeBeginDisFlg,
								hwGoOutTimeBeginDisFlg,
								hwTimeCalUseAtr, 
								hwTimeInputUseAtr));
		
	}
}
