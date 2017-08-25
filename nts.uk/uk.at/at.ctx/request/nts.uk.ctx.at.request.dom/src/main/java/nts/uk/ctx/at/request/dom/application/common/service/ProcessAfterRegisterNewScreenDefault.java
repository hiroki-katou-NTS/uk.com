package nts.uk.ctx.at.request.dom.application.common.service;

import java.util.List;

public class ProcessAfterRegisterNewScreenDefault {
	
	public void processAfterRegisterNewScreen(String companyID, String appID){
		/*
		Application result = Application.find(companyID, appID);
		if(result.size < 1) return;
		ApplicationTypeSetting applicationTypeSetting = from cache when ApplicationTypeSetting.applicationType == Application.applicationType; // 「申請種類別設定」．申請種類 = 「申請」．申請種類
		if(applicationTypeSetting.sendMailAutomaticallyWhenNewlyRegistered == false) return;
		
		// アルゴリズム「送信先リストの取得」を実行する  ( Execute the algorithm "Acquire destination list" )
		List<String> destinationList = acquireDestinationList(result); 
		if(destinationList.size < 1 ) return;
		for(item in destinationList) {
			sendMail(item);
			Imported(Employment)[Employee]; // Imported(就業)「社員」 ??? 
		}
		*/
	}
	
	// 1.送信先リストの取得
	public List<String> acquireDestinationList(Object application){
		/*
		// 送信先リストをクリアする ( Clear the destination list ) ???
		application.destinationList.clear();
		ReflectionInformation reflectionInformation = ReflectionInformation.find(appID); // 「反映情報」．実績反映状態 
		if(reflectionInformation.reflectionStatus == true) return;
		// ドメインモデル「申請」．「承認フェーズ」1～5の順でループする ( Domain model 'application'. Loop in the order of "approval phase" 1 to 5 )
		for(item in Application.ApprovalPhase) {
			if((item.approvalAtr == denied)||(item.approvalAtr == returned)) { // 「承認フェーズ」．承認区分 == (否認|| は差し戻し)
				break;
			} else if(item.approvalAtr == approved) { // 「承認フェーズ」．承認区分 == 承認済
				continue;
			} else {
				// item.approvalAtr == not approved, 「承認フェーズ」．承認区分 == 未承認
				// アルゴリズム「承認者一覧を取得する」を実行する ( Execute algorithm "Acquire approver list" ) 
				String approver = ApprovalPhase.find();
				if(approverList.size < 1) continue;
				// アルゴリズム「未承認の承認者一覧を取得する」を実行する ( Execute algorithm "Acquire unapproved approver list" )
				List<String> unApprovedApproverList = ApprovalPhase.find();
				if(!unApprovedApproverList.containts(approver)) break;
				// アルゴリズム「承認代行情報の取得処理」を実行する ( Executes the algorithm "acquisition process of approval substitution information" )
				Object obj1 = find(companyID, unApprovedApproverList);
				// アルゴリズム「送信先の判断処理」を実行する ( Executes the algorithm "destination determination process" )
				Object obj2 = find(obj1.ListOfSubstituteInformationOfApprover);
				application.destinationList.add(obj2);
			}
		}
		application.destinationList.distinct(); // Delete duplicate approvers in the destination list
		return application.destinationList;
		*/
		return null;
	}
}
