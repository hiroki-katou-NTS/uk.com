package nts.uk.ctx.at.request.dom.applicationreflect.service;

import nts.uk.ctx.at.request.dom.application.Application_New;

/**
 * 申請反映Mgrクラス
 * @author do_dt
 *
 */
public interface AppReflectManager {
	/**
	 * 社員の申請を反映
	 * @param appInfor
	 */
	public ReflectResult reflectEmployeeOfApp(Application_New appInfor, InformationSettingOfEachApp reflectSetting);

}
