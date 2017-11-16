package nts.uk.ctx.at.request.dom.application.gobackdirectly.service;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.DetailAfterUpdate;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.DetailBeforeUpdate;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectly;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectlyRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSettingRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.common.RequiredFlg;
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
	
	@Inject
	ApplicationSettingRepository applicationSettingRepository;

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
	public void updateGoBackDirectly(GoBackDirectly goBackDirectly, Application application) {
		String companyID = AppContexts.user().companyId();
		// ドメインモデル「直行直帰申請」の更新する
		this.goBackDirectlyRepo.update(goBackDirectly);
		Optional<ApplicationSetting> applicationSettingOp = applicationSettingRepository
				.getApplicationSettingByComID(goBackDirectly.getCompanyID());
		ApplicationSetting applicationSetting = applicationSettingOp.get();
		if (applicationSetting.getRequireAppReasonFlg().equals(RequiredFlg.REQUIRED)
				&& Strings.isBlank(application.getApplicationReason().v())) {
			throw new BusinessException("Msg_115");
		}
		application.setVersion(goBackDirectly.getVersion());
		appRepo.updateApplication(application);
		// アルゴリズム「4-2.詳細画面登録後の処理」を実行する
		//this.detailAfterUpdate.processAfterDetailScreenRegistration(companyID, goBackDirectly.getAppID());
	}

}
