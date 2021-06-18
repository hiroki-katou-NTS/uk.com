package nts.uk.ctx.workflow.dom.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalBehaviorAtr;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalPhaseState;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalRootState;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalRootStateRepository;
import nts.uk.ctx.workflow.dom.service.output.ApprovalRepresenterOutput;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class CollectMailNotifierImpl implements CollectMailNotifierService {
	
	@Inject
	private ApprovalRootStateRepository approvalRootStateRepository;
	
	@Inject
	private JudgmentApprovalStatusService judgmentApprovalStatusService;
	
	@Inject
	private ApproveService approveService;

	@Inject
	private CollectApprovalAgentInforService collectApprovalAgentInforService;
	
	@Override
	public List<String> getMailNotifierList(String companyID, String rootStateID, Integer rootType) {
		List<String> mailList = new ArrayList<>();
		Optional<ApprovalRootState> opApprovalRootState = approvalRootStateRepository.findByID(rootStateID, rootType);
		if(!opApprovalRootState.isPresent()){
			throw new RuntimeException("状態：承認ルート取得失敗"+System.getProperty("line.separator")+"error: ApprovalRootState, ID: "+rootStateID);
		}
		ApprovalRootState approvalRootState = opApprovalRootState.get();
		// ドメインモデル「申請」．「承認フェーズインタフェース」．順序が5～1までループする 
		List<ApprovalPhaseState> approvalPhaseStateLst = approvalRootState.getListApprovalPhaseState().stream()
				.sorted(Comparator.comparing(ApprovalPhaseState::getPhaseOrder).reversed()).collect(Collectors.toList());
		for(ApprovalPhaseState approvalPhaseState : approvalPhaseStateLst){
			// アルゴリズム「承認フェーズ毎の承認者を取得する」を実行する(thực hiện xử lý 「承認フェーズ毎の承認者を取得する」)
			List<String> listApprover = judgmentApprovalStatusService.getApproverFromPhase(approvalPhaseState);
			if(CollectionUtil.isEmpty(listApprover)){
				continue;
			}
			// アルゴリズム「指定する承認フェーズに承認済の承認者一覧を取得する」を実行する 
			List<String> listUnapproveApprover = approveService.getApprovedApproverFromPhase(approvalPhaseState);
			// 承認済の承認者一覧(output)に承認者がいるかチェックする
			if(CollectionUtil.isEmpty(listUnapproveApprover)){
				continue;
			}
			// アルゴリズム「承認代行情報の取得処理」を実行する(thực hiện xử lý 「承認代行情報の取得処理」)
			ApprovalRepresenterOutput approvalRepresenterOutput = collectApprovalAgentInforService.getApprovalAgentInfor(companyID, listUnapproveApprover);
			// アルゴリズム「送信先の判断処理」を実行する(thực hiện xử lý 「送信先の判断処理」)
			List<String> destinationList = approveService.judgmentDestination(approvalRepresenterOutput.getListApprovalAgentInfor());
			// 送信先リスト(output)を送信者リストに追加する(thêm danh sách người nhận vào danh sách người nhận mai)
			mailList.addAll(destinationList);
		}
		return mailList.stream().distinct().collect(Collectors.toList());
	}

}
