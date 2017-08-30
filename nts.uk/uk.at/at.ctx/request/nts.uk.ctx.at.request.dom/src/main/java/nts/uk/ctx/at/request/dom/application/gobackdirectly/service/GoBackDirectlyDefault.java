package nts.uk.ctx.at.request.dom.application.gobackdirectly.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.common.Application;
import nts.uk.ctx.at.request.dom.application.common.registerapprovereflection.service.RegisterAtApproveReflectionInfoService;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectly;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectlyRepository;

/**
 * 直行直帰登録
 * 
 * @author ducpm
 */
@Stateless
public class GoBackDirectlyDefault implements GoBackDirectlyService {
	@Inject
	RegisterAtApproveReflectionInfoService registerAppReplection;
	@Inject
	GoBackDirectlyRepository goBackDirectRepo;

	@Override
	public void register(String employeeID, Application application,GoBackDirectly goBackDirectly) {
		/**
		 * 2-2.新規画面登録時承認反映情報の整理
		 */
		registerAppReplection.newScreenRegisterAtApproveInfoReflect(employeeID, application);
		/**
		 * ドメインモデル「直行直帰申請」の新規登録する
		 */
		goBackDirectRepo.insert(goBackDirectly);
	}

}
