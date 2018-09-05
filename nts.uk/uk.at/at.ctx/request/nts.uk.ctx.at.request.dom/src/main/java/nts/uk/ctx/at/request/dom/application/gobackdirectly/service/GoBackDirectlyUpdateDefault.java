package nts.uk.ctx.at.request.dom.application.gobackdirectly.service;

import java.util.Arrays;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository_New;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.EmploymentRootAtr;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.DetailAfterUpdate;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.DetailBeforeUpdate;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectly;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectlyRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSettingRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSettingRepository;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.GoBackDirectlyCommonSetting;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.GoBackDirectlyCommonSettingRepository;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.primitive.CheckAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainDataMngRegisterDateChange;

@Stateless
public class GoBackDirectlyUpdateDefault implements GoBackDirectlyUpdateService {

	@Inject
	private DetailBeforeUpdate detailBeforeUpdate;

	@Inject
	private GoBackDirectlyRepository goBackDirectlyRepo;
	
	@Inject 
	private ApplicationRepository_New appRepo;

	@Inject
	private ApplicationSettingRepository applicationSettingRepository;
	
	@Inject
	private DetailAfterUpdate detailAfterUpdate;
	
	@Inject
	private GoBackDirectlyCommonSettingRepository goBackDirectCommonSetRepo;
	
	@Inject
	private GoBackDirectlyRegisterService goBackDirectlyRegisterService;

	@Inject
	private AppTypeDiscreteSettingRepository appTypeDiscreteSettingRepository;
	
	@Inject
	private InterimRemainDataMngRegisterDateChange interimRemainDataMngRegisterDateChange;
	
	/**
	 * アルゴリズム「直行直帰更新前チェック」を実行する
	 */
	@Override
	public void checkErrorBeforeUpdate(GoBackDirectly goBackDirectly, String companyID, String appID, Long version) {
		// アルゴリズム「4-1.詳細画面登録前の処理」を実行する
		Application_New application_New = appRepo.findByID(companyID, appID).get();
		this.detailBeforeUpdate.processBeforeDetailScreenRegistration(
				companyID, 
				application_New.getEmployeeID(), 
				application_New.getAppDate(), 
				EmploymentRootAtr.APPLICATION.value,
				application_New.getAppID(), 
				application_New.getPrePostAtr(), 
				version);
		GoBackDirectlyCommonSetting goBackCommonSet = goBackDirectCommonSetRepo.findByCompanyID(companyID).get();
		// アルゴリズム「直行直帰するチェック」を実行する - client da duoc check
		// アルゴリズム「直行直帰遅刻早退のチェック」を実行する
		GoBackDirectLateEarlyOuput goBackLateEarly = goBackDirectlyRegisterService.goBackDirectLateEarlyCheck(goBackDirectly, application_New);
		//直行直帰遅刻早退のチェック
		//TODO: chua the thuc hien duoc nen mac dinh luc nao cung co loi エラーあり
		if(goBackLateEarly.isError) {
			//直行直帰申請共通設定.早退遅刻設定がチェックする
			if(goBackCommonSet.getLateLeaveEarlySettingAtr() == CheckAtr.CHECKREGISTER) {
				goBackDirectlyRegisterService.createThrowMsg("Msg_297", goBackLateEarly.msgLst);
			}else if(goBackCommonSet.getLateLeaveEarlySettingAtr() == CheckAtr.CHECKNOTREGISTER) {
				goBackDirectlyRegisterService.createThrowMsg("Msg_298", goBackLateEarly.msgLst);	
			}
		}
	}

	/**
	 * アルゴリズム「直行直帰更新」を実行する
	 */
	@Override
	public ProcessResult updateGoBackDirectly(GoBackDirectly goBackDirectly, Application_New application, Long version) {
		// ドメインモデル「直行直帰申請」の更新する
		this.goBackDirectlyRepo.update(goBackDirectly);
		application.setVersion(version);
		appRepo.updateWithVersion(application);
		
		// 暫定データの登録
		interimRemainDataMngRegisterDateChange.registerDateChange(
				application.getCompanyID(), 
				application.getEmployeeID(), 
				Arrays.asList(application.getAppDate()));
		
		// アルゴリズム「4-2.詳細画面登録後の処理」を実行する
		return this.detailAfterUpdate.processAfterDetailScreenRegistration(application);
	}

}
