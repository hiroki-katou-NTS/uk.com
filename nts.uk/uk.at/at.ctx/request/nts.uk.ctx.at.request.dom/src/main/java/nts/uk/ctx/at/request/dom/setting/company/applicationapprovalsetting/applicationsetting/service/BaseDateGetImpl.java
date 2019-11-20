package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.service;

import javax.ejb.Stateless;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.common.BaseDateFlg;

/**
 * 基準日として扱う日の取得
 *
 */
@Stateless
public class BaseDateGetImpl implements BaseDateGet {

	@Override
	public GeneralDate getBaseDate(ApplicationSetting applicationSetting, GeneralDate appDate) {
		GeneralDate baseDate = null;
		if(applicationSetting.getBaseDateFlg().equals(BaseDateFlg.APP_DATE)){
			if(appDate==null){
			// 「申請設定」．承認ルートの基準日が申請対象日時点の場合 ( "Application setting". When the reference date of the approval route is the date of the application target date )
			// 申請対象日のパラメータがあるかチェックする ( Check if there is a parameter on the application target date )
				baseDate = GeneralDate.today();
			} else {
				baseDate = appDate;
			}
		} else {
			// 「申請設定」．承認ルートの基準日がシステム日付時点の場合 ( "Application setting". When the base date of the approval route is at the time of the system date )
			baseDate = GeneralDate.today();
		}
		return baseDate;
	}
}
