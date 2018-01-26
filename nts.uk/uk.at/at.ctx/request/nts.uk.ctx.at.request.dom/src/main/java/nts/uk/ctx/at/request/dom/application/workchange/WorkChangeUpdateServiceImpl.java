package nts.uk.ctx.at.request.dom.application.workchange;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.ApplicationRepository_New;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.DetailAfterUpdate;

@Stateless
public class WorkChangeUpdateServiceImpl implements IWorkChangeUpdateService {

	@Inject 
	private ApplicationRepository_New appRepository;
	
	@Inject
	private DetailAfterUpdate detailAfterUpdate;
	
	@Inject
	private IAppWorkChangeRepository workChangeRepository;
	
	@Override
	public void UpdateWorkChange(Application_New app, AppWorkChange workChange) {
		
		//ドメインモデル「勤務変更申請」の更新をする
		appRepository.updateWithVersion(app);
		workChangeRepository.update(workChange);
		
		//アルゴリズム「4-2.詳細画面登録後の処理」を実行する
		detailAfterUpdate.processAfterDetailScreenRegistration(app);		
	}

}
