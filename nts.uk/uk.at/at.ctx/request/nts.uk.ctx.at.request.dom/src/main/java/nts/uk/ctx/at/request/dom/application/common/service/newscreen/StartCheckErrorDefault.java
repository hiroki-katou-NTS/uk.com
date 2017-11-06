package nts.uk.ctx.at.request.dom.application.common.service.newscreen;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.UseAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalRootImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ErrorFlagImport;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSettingRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.common.BaseDateFlg;
import nts.uk.ctx.at.request.dom.setting.requestofeach.RequestAppDetailSetting;
import nts.uk.ctx.at.request.dom.setting.requestofeach.RequestOfEachCompanyRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 1-5 新規画面起動時のエラーチェック
 * 
 * @author ducpm
 */
@Stateless
public class StartCheckErrorDefault implements StartCheckErrorService {
	/**
	 * 申請詳細設定
	 */
	@Inject
	private RequestOfEachCompanyRepository requestRepo;
	/**
	 * 申請設定
	 */
	@Inject
	private ApplicationSettingRepository appSettingRepo;

	@Inject
	private ApprovalRootAdapter approvalRootRepo;

	@Override
	public void checkError(int appType) {
		String companyId = AppContexts.user().companyId();
		String employeeId = AppContexts.user().employeeId();
		Optional<RequestAppDetailSetting> requestSet = requestRepo.getRequestDetail(companyId, appType);
		if (requestSet.isPresent()) {
			// if (requestSet.map(c -> c.userAtr).get().value == 0) {
			if (requestSet.get().getUserAtr() == UseAtr.NOTUSE) {
				// 利用区分が利用しない
				throw new BusinessException("Msg_323");
			} else {
				// 利用区分が利用する
				Optional<ApplicationSetting> appSet = appSettingRepo.getApplicationSettingByComID(companyId);
				// 「申請設定」．承認ルートの基準日がシステム日付時点の場合
				if (appSet.get().getBaseDateFlg() == BaseDateFlg.SYSTEM_DATE) {
					// lay tu Cache
					List<ApprovalRootImport> approvalRootOutputs = approvalRootRepo.getApprovalRootOfSubjectRequest(companyId, employeeId, 1, appType, GeneralDate.today());
					if (approvalRootOutputs.size() > 0) {
						ApprovalRootImport approvalRootOutput = approvalRootOutputs.get(0);
						if (approvalRootOutput.getErrorFlag() != null) {
							if (approvalRootOutput.getErrorFlag().equals(ErrorFlagImport.NO_CONFIRM_PERSON))
								throw new BusinessException("Msg_238");
							if (approvalRootOutput.getErrorFlag().equals(ErrorFlagImport.APPROVER_UP_10))
								throw new BusinessException("Msg_237");
							if (approvalRootOutput.getErrorFlag().equals(ErrorFlagImport.NO_APPROVER))
								throw new BusinessException("Msg_324");
						}
					} else {
						// 「申請設定」．承認ルートの基準日が申請対象日時点の場合
						// DO NOT
					}
				}
			}
		}
	}
}
