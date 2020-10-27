package nts.uk.ctx.at.schedule.dom.displaysetting;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

/**
 * スケジュール修正日付別の表示設定を取得する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.表示設定.スケジュール修正日付別の表示設定を取得する
 * @author hiroko_miura
 *
 */
public class GetDisplaySettingByDateService {

	/**
	 * 取得する
	 * @param require
	 * @param targetOrg
	 * @return 
	 */
	public static DisplaySettingByDate get(Require require, TargetOrgIdenInfor targetOrg) {
		Optional<DisplaySettingByDateForOrganization> org = require.getOrg(targetOrg);
		if (org.isPresent())
			return org.get().getDispSetting();
		
		
		Optional<DisplaySettingByDateForCompany> com = require.getCmp();
		if (com.isPresent())
			return com.get().getDispSetting();
		
		return DisplaySettingByDate.create(
				DisplayRangeType.DISP24H, 
				new DisplayStartTime(0), 
				new DisplayStartTime(0));
	}
	
	public static interface Require {
		
		/**
		 * 組織別設定を取得する
		 * @param targetOrg
		 * @return
		 */
		Optional<DisplaySettingByDateForOrganization> getOrg(TargetOrgIdenInfor targetOrg);
		
		/**
		 * 会社別設定を取得する
		 * @return
		 */
		Optional<DisplaySettingByDateForCompany> getCmp();
	}
}
