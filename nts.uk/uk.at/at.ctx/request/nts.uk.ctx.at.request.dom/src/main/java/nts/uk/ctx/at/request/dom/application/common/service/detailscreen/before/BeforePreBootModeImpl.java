package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ReflectPlanPerState;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.AgentAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootStateAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApproverPersonImport;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalAtr;
//import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.InitMode;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.DetailedScreenPreBootModeOutput;
//import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.OutputMode;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.User;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.PeriodCurrentMonth;

@Stateless
public class BeforePreBootModeImpl implements BeforePreBootMode {
	/** 14-2.詳細画面起動前モードの判断 */
	@Inject
	OtherCommonAlgorithm otherCommonAlgorithmService;

	@Inject
	AgentAdapter approvalAgencyInformationService;
	
	@Inject
	private ApprovalRootStateAdapter approvalRootStateAdapter;
	
	@Override
	public DetailedScreenBeforeStartOutput judgmentDetailScreenMode(String companyID, String employeeID, Application application, GeneralDate baseDate) {
		// Output variables
		DetailedScreenBeforeStartOutput outputData = new DetailedScreenBeforeStartOutput(User.OTHER, ReflectPlanPerState.NOTREFLECTED, false, ApprovalAtr.UNAPPROVED, false);
		//4.社員の当月の期間を算出する
		PeriodCurrentMonth listDate = otherCommonAlgorithmService.employeePeriodCurrentMonthCalculate(companyID,
				application.getEmployeeID(), baseDate);
		GeneralDate startDate = listDate.getStartDate();
		// 締め開始日 >  ドメインモデル「申請」．申請日 がtrue
		if (startDate.after(application.getAppDate().getApplicationDate())) {
			//ステータス = 過去申請(status= 過去申請)
			outputData.setPastApp(true);
		} else {
			outputData.setPastApp(false);
		}	
		// 反映状態を取得する
		outputData.setReflectPlanState(EnumAdaptor.valueOf(application.getAppReflectedState().value, ReflectPlanPerState.class));
		// アルゴリズム「利用者の判定」を実行する(thực hiện thuật toán "đánh giá user")
		outputData.setUser(otherCommonAlgorithmService.userJudgment(companyID, application.getAppID(), employeeID));
		// 利用者をチェックする(Check người sử dụng)
		if(outputData.getUser().equals(User.APPLICANT_APPROVER)||outputData.getUser().equals(User.APPROVER)){
			// アルゴリズム「指定した社員が承認できるかの判断」を実行する
			ApproverPersonImport approverPersonImport = approvalRootStateAdapter.judgmentTargetPersonCanApprove(companyID, application.getAppID(), employeeID);
			// 承認できるフラグ = 承認できるフラグ(output)、ログイン者の承認区分 = 承認区分(output)、代行期限切れフラグ = 代行期限切れフラグ(output)
			outputData.setAuthorizableFlags(approverPersonImport.getAuthorFlag());
			outputData.setApprovalATR(EnumAdaptor.valueOf(approverPersonImport.getApprovalAtr().value, ApprovalAtr.class));
			outputData.setAlternateExpiration(approverPersonImport.getExpirationAgentFlag());
		}
		return outputData;
	}
}