package nts.uk.ctx.at.request.dom.applicationapproval.application.gobackdirectly.service;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.applicationapproval.application.Application;
import nts.uk.ctx.at.request.dom.applicationapproval.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.applicationapproval.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.applicationapproval.application.common.service.detailscreen.after.DetailAfterUpdate;
import nts.uk.ctx.at.request.dom.applicationapproval.application.common.service.detailscreen.before.DetailBeforeUpdate;
import nts.uk.ctx.at.request.dom.applicationapproval.application.gobackdirectly.GoBackDirectly;
import nts.uk.ctx.at.request.dom.applicationapproval.application.gobackdirectly.GoBackDirectlyRepository;
import nts.uk.ctx.at.request.dom.applicationapproval.setting.request.gobackdirectlycommon.GoBackDirectlyCommonSetting;
import nts.uk.ctx.at.request.dom.applicationapproval.setting.request.gobackdirectlycommon.GoBackDirectlyCommonSettingRepository;
import nts.uk.ctx.at.request.dom.applicationapproval.setting.request.gobackdirectlycommon.primitive.CheckAtr;
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
		this.updateGoBackDirectly(goBackDirectly);
		appRepo.updateApplication(application);
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
		//this.detailAfterUpdate.processAfterDetailScreenRegistration(companyID, goBackDirectly.getAppID());
	}

}
