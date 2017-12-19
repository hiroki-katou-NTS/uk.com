package nts.uk.ctx.at.request.dom.application.workchange;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.DetailAfterUpdate;

@Stateless
public class WorkChangeUpdateServiceImpl implements IWorkChangeUpdateService {

	@Inject 
	private ApplicationRepository appRepository;
	
	@Inject
	private DetailAfterUpdate detailAfterUpdate;
	
	@Inject 
	private IWorkChangeRegisterService registerService;
	
	@Inject
	private IAppWorkChangeRepository workChangeRepository;
	
	@Override
	public void UpdateWorkChange(Application app, AppWorkChange workChange) {
		
		//アルゴリズム「勤務変更申請就業時間チェックの内容」を実行する
		//registerService.checkWorkHour(workChange);
		
		//アルゴリズム「勤務変更申請休憩時間１チェックの内容」を実行する
		//registerService.checkBreakTime1(workChange);
		
		//ドメインモデル「勤務変更申請」の更新をする
		appRepository.updateApplication(app);
		workChangeRepository.update(workChange);
		
		//アルゴリズム「4-2.詳細画面登録後の処理」を実行する
		detailAfterUpdate.processAfterDetailScreenRegistration(app);		
	}

}
