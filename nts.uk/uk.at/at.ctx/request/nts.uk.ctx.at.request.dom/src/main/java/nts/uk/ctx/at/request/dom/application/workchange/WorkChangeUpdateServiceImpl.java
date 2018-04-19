package nts.uk.ctx.at.request.dom.application.workchange;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.ApplicationRepository_New;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.DetailAfterUpdate;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.DetailBeforeUpdate;

@Stateless
public class WorkChangeUpdateServiceImpl implements IWorkChangeUpdateService {

	@Inject 
	private ApplicationRepository_New appRepository;
	
	@Inject
	private DetailAfterUpdate detailAfterUpdate;
	
	@Inject
	private IAppWorkChangeRepository workChangeRepository;
	@Inject
	private DetailBeforeUpdate detailBeforeUpdate;
	
	@Override
	public void UpdateWorkChange(Application_New app, AppWorkChange workChange) {
		
		detailBeforeUpdate.processBeforeDetailScreenRegistration(
				app.getCompanyID(), 
				app.getEmployeeID(), 
				app.getAppDate(), 
				ApplicationType.WORK_CHANGE_APPLICATION.value, 
				app.getAppID(), 
				app.getPrePostAtr(), app.getVersion());
		
		//ドメインモデル「勤務変更申請」の更新をする
		appRepository.updateWithVersion(app);
		workChangeRepository.update(workChange);
		
		//アルゴリズム「4-2.詳細画面登録後の処理」を実行する
		detailAfterUpdate.processAfterDetailScreenRegistration(app);		
	}

}
