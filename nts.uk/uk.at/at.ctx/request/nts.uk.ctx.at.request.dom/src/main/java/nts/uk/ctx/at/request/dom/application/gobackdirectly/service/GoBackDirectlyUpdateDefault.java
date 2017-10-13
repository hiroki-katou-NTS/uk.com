package nts.uk.ctx.at.request.dom.application.gobackdirectly.service;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.Application;
import nts.uk.ctx.at.request.dom.application.common.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.common.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.DetailAfterUpdate;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.DetailBeforeUpdate;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectly;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectlyRepository;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.GoBackDirectlyCommonSetting;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.GoBackDirectlyCommonSettingRepository;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.primitive.CheckAtr;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class GoBackDirectlyUpdateDefault implements GoBackDirectlyUpdateService {

	@Inject
	private DetailBeforeUpdate detailBeforeUpdate;

	@Inject
	private GoBackDirectlyRegisterService goBackDirectlyRegisterService;

	@Inject
	private GoBackDirectlyCommonSettingRepository commonSetRepo;

	@Inject
	private GoBackDirectlyRepository goBackDirectlyRepo;
	
	@Inject 
	private ApplicationRepository appRepo;

	@Inject
	private DetailAfterUpdate detailAfterUpdate;

	@Override
	public void update(GoBackDirectly goBackDirectly, Application application) {
		String companyId = AppContexts.user().companyId();
		// アルゴリズム「直行直帰更新前チェック」を実行する
		this.checkErrorBeforeUpdate(
				application.getEnteredPersonSID(), 
				application.getApplicationDate(), 
				1,
				application.getApplicationID(), 
				application.getPrePostAtr());
		// アルゴリズム「直行直帰するチェック」を実行する
		GoBackDirectAtr goBackAtr = goBackDirectlyRegisterService.goBackDirectCheck(goBackDirectly);

		if (goBackAtr == GoBackDirectAtr.NOT) {
			throw new BusinessException("Msg_307");
		} else {
			GoBackDirectLateEarlyOuput lateEarlyOut = goBackDirectlyRegisterService.goBackDirectLateEarlyCheck(goBackDirectly);
			// 直行直帰申請共通設定.早退遅刻設定がチェックするの場合、メッセージを表示する
			Optional<GoBackDirectlyCommonSetting> commonSet = commonSetRepo.findByCompanyID(companyId);
			if (lateEarlyOut.isError && commonSet.isPresent()) {
				// ・チェックする（登録可）
				if (commonSet.get().getLateLeaveEarlySettingAtr() == CheckAtr.CHECKNOTREGISTER) {
					// 遅行早退のチェックメッセージに確認メッセージ（Msg_298）を追加して表示する
					throw new BusinessException("Msg_298");
					// 入力項目をエラー「赤色」枠を表示する
				} else if (commonSet.get().getLateLeaveEarlySettingAtr() == CheckAtr.CHECKREGISTER) {
					throw new BusinessException("Msg_297");
				}
			} else {
				//アルゴリズム「直行直帰更新」を実行する
				this.appRepo.updateApplication(application);
				this.updateGoBackDirectly(goBackDirectly);
				
			}
		}
	}

	/**
	 * アルゴリズム「直行直帰更新前チェック」を実行する
	 */
	@Override
	public void checkErrorBeforeUpdate(String employeeID, GeneralDate appDate, int employeeRouteAtr, String appID, PrePostAtr postAtr) {
		// アルゴリズム「4-1.詳細画面登録前の処理」を実行する
		String companyId = AppContexts.user().companyId();
		this.detailBeforeUpdate.processBeforeDetailScreenRegistration(companyId, employeeID, appDate, employeeRouteAtr,
				appID, postAtr);
	}

	/**
	 * アルゴリズム「直行直帰更新」を実行する
	 */
	@Override
	public void updateGoBackDirectly(GoBackDirectly goBackDirectly) {
		String companyID = AppContexts.user().companyId();
		// ドメインモデル「直行直帰申請」の更新する
		this.goBackDirectlyRepo.update(goBackDirectly);
		// アルゴリズム「4-2.詳細画面登録後の処理」を実行する
		this.detailAfterUpdate.processAfterDetailScreenRegistration(companyID, goBackDirectly.getAppID());

	}

}
