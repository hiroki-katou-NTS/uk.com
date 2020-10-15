package nts.uk.ctx.at.request.dom.application.approval;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootStateAdapter;

@Stateless
public class ApprovalInfoService implements ApprovalInfoInterface{
	@Inject
	private ApprovalRootStateAdapter apprRootStateAd;
	@Inject
	private ApplicationRepository appRepo;
	/**
	 * 利用者の判定
	 * @param 会社ID companyId
	 * @param ドメインモデル「申請」．ID appId
	 * @param ログイン者の社員ID sId
	 * @return
	 */
	@Override
	public JudgmentUserAtr judgmentUser(String companyId, String appId, String sId){
		//ドメインモデル「申請」を取得する
		Optional<Application> app = appRepo.findByID(companyId, appId);
		if(!app.isPresent()) return JudgmentUserAtr.OTHER;
		//アルゴリズム「指定した社員が承認者であるかの判断」を実行する (Thực hiện [kiểm tra xem employee đã chỉ định có phải là approver ko])
		Boolean	approverFlag = apprRootStateAd.judgmentTargetPersonIsApprover(companyId, appId, sId);
		String applicantID = app.get().getEmployeeID();
		if(approverFlag){//承認者フラグがtrue TH co the approve
			if(sId.equals(applicantID)) return JudgmentUserAtr.ALL;
			return JudgmentUserAtr.APPROVER;
		}
		if(sId.equals(applicantID)) return JudgmentUserAtr.APPLICATION;
		return JudgmentUserAtr.OTHER;
	};
}
