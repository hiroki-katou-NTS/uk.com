package nts.uk.ctx.at.request.dom.application.gobackdirectly.service;

import java.util.List;

import nts.uk.ctx.at.request.dom.application.common.Application;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhase;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectly;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.GoBackDirectlyCommonSetting;

public interface GoBackDirectlyRegisterService {
	/**
	 * 直行直帰登録
	 * 
	 * @param employeeID
	 * @param application
	 * @param goBackDirectly
	 */
	public void register(GoBackDirectly goBackDirectly, Application application,List<AppApprovalPhase> appApprovalPhases);
	
	/**
	 * 
	 * @param goBackDirectly
	 * @param application
	 * @param appApprovalPhases
	 */
	public void checkBeforRegister(GoBackDirectly goBackDirectly, Application application,List<AppApprovalPhase> appApprovalPhases);

	/**
	 * アルゴリズム「直行直帰するチェック」を実行する
	 * 
	 * @param goBackDirectly
	 * @param goBackAtr
	 */
	public GoBackDirectAtr goBackDirectCheck(GoBackDirectly goBackDirectly);

	/**
	 * 直行直帰遅刻早退のチェック
	 * 
	 * @param goBackDirectly
	 * @return
	 */
	public GoBackDirectLateEarlyOuput goBackDirectLateEarlyCheck(GoBackDirectly goBackDirectly);

	/**
	 * Check Validity
	 * 
	 */
	public CheckValidOutput goBackLateEarlyCheckValidity(GoBackDirectly goBackDirectly,
			GoBackDirectlyCommonSetting goBackCommonSet, int line);
}
