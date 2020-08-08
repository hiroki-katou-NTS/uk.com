package nts.uk.ctx.at.request.dom.application.common.service.detailscreen;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.ReflectedState;
import nts.uk.ctx.at.request.dom.application.ReflectionStatusOfDay;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@Stateless
public class ProcessCancelImpl implements ProcessCancel {

	@Inject
	private ApplicationRepository appRepo;
	
	@Override
	public void detailScreenCancelProcess(String companyID, String appID, Application application) {
		// 「反映情報」．実績反映状態を「取消待ち」にする
		for(ReflectionStatusOfDay reflectionStatusOfDay : application.getReflectionStatus().getListReflectionStatusOfDay()) {
			reflectionStatusOfDay.setActualReflectStatus(ReflectedState.CANCELED);
		}
		// アルゴリズム「反映状態の更新」を実行する
		appRepo.update(application);
	}

}
