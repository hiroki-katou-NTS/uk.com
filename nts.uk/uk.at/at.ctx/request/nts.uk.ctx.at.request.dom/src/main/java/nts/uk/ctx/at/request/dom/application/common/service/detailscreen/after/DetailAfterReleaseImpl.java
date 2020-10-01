package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.ReflectedState;
import nts.uk.ctx.at.request.dom.application.ReflectionStatusOfDay;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootStateAdapter;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author hieult
 *
 */
@Stateless
public class DetailAfterReleaseImpl implements DetailAfterRelease {
	
	@Inject
	private ApprovalRootStateAdapter approvalRootStateAdapter;
	
	@Inject
	private ApplicationRepository applicationRepository;
	
	@Override
	public ProcessResult detailAfterRelease(String companyID, String appID, Application application) {
		String loginID = AppContexts.user().employeeId();
		ProcessResult processResult = new ProcessResult();
		// 4.解除する
		Boolean releaseFlg = approvalRootStateAdapter.doRelease(companyID, appID, loginID);
		if(!releaseFlg) {
			processResult.setAppID(appID);
			return processResult;
		}
		processResult.setProcessDone(true);
		// 「反映情報」．実績反映状態を「未反映」にする(chuyển trạng thái 「反映情報」．実績反映状態 thành 「未反映」)
		for(ReflectionStatusOfDay reflectionStatusOfDay : application.getReflectionStatus().getListReflectionStatusOfDay()) {
			reflectionStatusOfDay.setActualReflectStatus(ReflectedState.NOTREFLECTED);
		}
		// アルゴリズム「反映状態の更新」を実行する ( Thực hiện thuật toán 「Update trạng thái phản ánh」
		applicationRepository.update(application);
		processResult.setAppID(appID);
		return processResult;
	}
}
