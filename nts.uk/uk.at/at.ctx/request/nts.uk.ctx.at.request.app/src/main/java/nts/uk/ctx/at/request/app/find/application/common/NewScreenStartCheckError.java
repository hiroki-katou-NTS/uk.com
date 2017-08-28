package nts.uk.ctx.at.request.app.find.application.common;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSettingRepository;
import nts.uk.ctx.at.request.dom.setting.requestofearch.RequestAppDetailSetting;
import nts.uk.ctx.at.request.dom.setting.requestofearch.RequestOfEarchCompanyRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class NewScreenStartCheckError {
	/**
	 * 申請詳細設定
	 */
	@Inject
	private RequestOfEarchCompanyRepository requestRepo;
	/**
	 * 申請設定
	 */
	@Inject
	private ApplicationSettingRepository appSettingRepo;

	/**
	 * 1-5.新規画面起動時のエラーチェック
	 */
	public void CheckError(int appType) {
		String companyId = AppContexts.user().companyId();
		Optional<RequestAppDetailSetting> requestSet = requestRepo.getRequestDetail(companyId, appType);
		if (requestSet.isPresent()) {
			if (requestSet.map(c -> c.userAtr).get().value == 0) {
				//利用区分が利用しない
				throw new BusinessException("Msg_323");
			} else {
				//利用区分が利用する
				Optional<ApplicationSetting> appSet = appSettingRepo.getApplicationSettingByComID(companyId);
				// 「申請設定」．承認ルートの基準日がシステム日付時点の場合
				if (appSet.map(x -> x.getBaseDateFlg()).get().value == 0) {
					//chưa biết lấy 承認ルート
					
					
					
				} else {
					// 「申請設定」．承認ルートの基準日が申請対象日時点の場合
				}
			}
		}
	}

}
