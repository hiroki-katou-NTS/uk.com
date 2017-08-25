package nts.uk.ctx.at.request.dom.application.common.service;

import java.util.ArrayList;

public class ProcessAfterDetailScreenRegistrationDefault implements ProcessAfterDetailScreenRegistrationService {
	
	public void processAfterDetailScreenRegistration(String companyID, String appID){
		/*
		Application app = Application.find(appID);
		if(!app.isPresent) return;
		obj = acquireApproverWhoApproved();
		// Update the domain model 'application' and 'approval phase' 'approval frame' 'reflection information'
			Update condition: applicationID
			Application.reasonForReversion <=> 「申請」．差戻し理由  clear
			All "approval phases". Approval classification = Unapproved <=> 「承認フェーズ」．承認区分 = 未承認  
			"Approval frame". Make the approval classification unapproved <=> 「承認枠」．承認区分 = 未承認
			"Approval frame". Clear approvers and agents <=> 「承認枠」．承認者 and 「承認枠」.代行者 clear 
			"Approval frame". Clear the reason <=> 「承認枠」．理由 clear
			"Approval frame". Clear the date <=> 「承認枠」．日付 clear
			"Reflection information". Make the actual reflection state unreflected <=> 「反映情報」．実績反映状態 = 未反映
		Application.update(); // 申請
		ApprovalPhase.ApprovalFrame().update(); // 「承認フェーズ」「承認枠」
		ReflectionInformation.update(); // 「反映情報」
		if(approverListThatMadeApproval.size < 1) return;
		ApplicationTypeSetting obj2 = ApplicationTypeSetting.find();
		if(obj2.Send mail automatically when newly registered == false) return; // 「申請種類別設定」．新規登録時に自動でメールを送信する
		destinationList.clear (initialization) // 送信先リスト clear
		for( item in approverListWhoApproved) {
			if(item.agentFlag == true) { item.代行者 == true
				// アルゴリズム「承認代行情報の取得処理」を実行する ( Executes the algorithm "acquisition process of approval substitution information" )
				obj3 = find(companyID, approverList[loopIndex]);
				if(obj3.approvalAgentList.contains(approverInLoop))) {
					destinationList.add(approverInLoop);
				}
			} else {
				// item.代行者 == false
				destinationList.add(approverInLoop);
			}
		}
		destinationList.deleteDuplicate();
		if(destination.size >= 1) {
			sendMail();
			Imported(Employment)[Employee]; // Imported(就業)「社員」を取得する ???
		}
		*/
	}
	
	public ArrayList<ArrayList<String>> acquireApproverWhoApproved(String approvalPhases){
		/*
		approverListWhoApproved.clear(); // 承認を行った承認者一覧
		approverList.clear(); // 承認者一覧
		for(item in approvalPhases) {
			// アルゴリズム「承認者一覧を取得する」を実行する ( Execute algorithm "Acquire approver list" )
			approverList1 = find(item);
			if(approverList1){
				for(item1 in approvalPhases) {
					if(item1.approvalClassification == true ){ // 承認区分
						if(item1.substitute == true) { // item1.代行者 == true
							approverListWhoApproved.add(item1.substitutePerson, true); // 承認者一覧に(「承認枠」．代行者, true)
						} else {
							// item1.代行者 == false
							approverListWhoApproved.add(item1.approver, false); // 承認者一覧に(「承認枠」．承認者, false)
						}
						approverList.add(item1.approverList);
					} 
				}
			}
		}
		return approverListWhoApproved, approverList;
		*/
		return new ArrayList<ArrayList<String>>();
	}
}
