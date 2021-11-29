package nts.uk.ctx.at.record.app.query.workrecord.stampmanagement.support;

import javax.ejb.Stateless;

import nts.uk.shr.com.context.AppContexts;

/**
 * Query: 初期表示を行う
 *
 * @author : NWS_namnv
 */
@Stateless
public class SupportCardSettingQuery {
	
	/**
	 * Gets the support card setting.
	 *
	 * @return the support card setting
	 */
	public SupportCardSettingDto getSupportCardSetting() {
		String cid = AppContexts.user().companyId();
		// TODO
//		return 応援カード編集設定
		return null;
	}

}
