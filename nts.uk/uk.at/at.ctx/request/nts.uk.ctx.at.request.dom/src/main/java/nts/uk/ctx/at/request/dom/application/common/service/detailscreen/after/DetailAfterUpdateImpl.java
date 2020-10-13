package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.ReasonForReversion;
import nts.uk.ctx.at.request.dom.application.ReflectedState;
import nts.uk.ctx.at.request.dom.application.ReflectionStatusOfDay;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootStateAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.AgentPubImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApproverApprovedImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApproverWithFlagImport_New;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.MailResult;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.AppTypeSetting;

@Stateless
public class DetailAfterUpdateImpl implements DetailAfterUpdate {

//	@Inject
//	private AppTypeDiscreteSettingRepository appTypeDiscreteSettingRepository;
	
	@Inject
	private ApprovalRootStateAdapter approvalRootStateAdapter;
	
	@Inject
	private ApplicationRepository applicationRepository;
	
	@Inject
	private OtherCommonAlgorithm otherCommonAlgorithm;

	public ProcessResult processAfterDetailScreenRegistration(String companyID, String appID, AppDispInfoStartupOutput appDispInfoStartupOutput) {
		List<String> destinationList = new ArrayList<>();
		ProcessResult processResult = new ProcessResult();
		processResult.setProcessDone(true);
		// アルゴリズム「申請IDを使用して申請一覧を取得する」を実行する(Thực hiện thuật toán [sử dụng ApplicationID để get ApplicationList])
		Optional<Application> opApplication = applicationRepository.findByID(appID);
		if(!opApplication.isPresent()) {
			processResult.setAppID(appID);
			return processResult;
		}
		Application application = opApplication.get();
		// アルゴリズム「承認を行った承認者を取得する」を実行する(Thực hiện 「lấy người approval đã thực hiện approval」)
		ApproverApprovedImport_New approverApprovedImport = approvalRootStateAdapter.getApproverApproved(application.getAppID());
		// アルゴリズム「一括解除する」を実行する(THực hiện [delete đồng loạt])
		approvalRootStateAdapter.doReleaseAllAtOnce(companyID, application.getAppID());
		// 「申請」.差し戻し理由をクリア(Clear 「申請」.lí do trả về)
		application.setOpReversionReason(Optional.of(new ReasonForReversion("")));
		// 「反映情報」.実績反映状態を未反映にする(Set [thong tin phản ánh]. ''trạng thái phản ánh thực tế'' thành chưa phản ánh)
		for(ReflectionStatusOfDay reflectionStatusOfDay : application.getReflectionStatus().getListReflectionStatusOfDay()) {
			reflectionStatusOfDay.setActualReflectStatus(ReflectedState.NOTREFLECTED);
		}
		// アルゴリズム「「申請」に紐づく「反映状態」の更新」を実行する(Thực hiện thuật toán「「申請」に紐づく「反映状態」の更新」 )
		applicationRepository.update(application);
		// 承認を行った承認者一覧に項目があるかチェックする (Kiểm tra xem có item trên "list người phê duyệt đã thực hiện phê duyệt" hay chưa)
		if (CollectionUtil.isEmpty(approverApprovedImport.getListApprover())) {
			processResult.setAppID(application.getAppID());
			return processResult;
		}
		
		// 承認を行った承認者一覧に項目がある ( There is an item in the approver list that made approval )
		// ドメインモデル「申請種類別設定」．新規登録時に自動でメールを送信するをチェックする ( Domain model "Application type setting". Check to send mail automatically when newly registered )
		AppTypeSetting appTypeSetting = appDispInfoStartupOutput.getAppDispInfoNoDateOutput().getApplicationSetting().getAppTypeSettings()
				.stream().filter(x -> x.getAppType()==application.getAppType()).findAny().orElse(null);
		boolean condition = appTypeSetting.isSendMailWhenRegister();
		if(!condition) {
			processResult.setAppID(application.getAppID());
			return processResult;
		}
		processResult.setAutoSendMail(true);
		// 「申請種類別設定」．新規登録時に自動でメールを送信するがtrue ( "Setting by application type". Automatically send mail when new registration is true )
		// 承認を行った承認者一覧を先頭から最後までループする ( Loop from the top to the end of the approver list that gave approval )
		for (ApproverWithFlagImport_New approverWithFlagImport : approverApprovedImport.getListApproverWithFlagOutput()) {
			// 承認を行った承認者の代行者フラグをチェックする ( Check the agent's flag of the approver who made the approval )
			String loopID = approverWithFlagImport.getEmployeeID(); 
			if (approverWithFlagImport.getAgentFlag().equals(Boolean.TRUE)) {
				AgentPubImport agentPubImport = approvalRootStateAdapter.getApprovalAgencyInformation(companyID, Arrays.asList(loopID));
				// 代行の権限を持っているかチェックする ( Check if you have delegation authority )
				if(agentPubImport.getListRepresenterSID().contains(loopID)) {
					destinationList.add(loopID);
				}
			} else {
				// ループ中承認者を送信先リストに追加する ( Add an approver in the loop to the destination list )
				destinationList.add(loopID);
			}
		}
		
		// 送信先リストに重複な項目を消す ( Delete duplicate items in the destination list )
		destinationList.stream().distinct().collect(Collectors.toList());
		
		// 送信先リストに項目がいるかチェックする ( Check if there is an item in the destination list )
		if(!CollectionUtil.isEmpty(destinationList)){
			// 送信先リストにメールを送信する ( Send mail to recipient list )
			MailResult mailResult = otherCommonAlgorithm.sendMailApproverApprove(destinationList, application, "");
			processResult.setAutoSuccessMail(mailResult.getSuccessList());
			processResult.setAutoFailMail(mailResult.getFailList());
			processResult.setAutoFailServer(mailResult.getFailServerList());
		}
		processResult.setAppID(application.getAppID());
		return processResult;
	}
}
