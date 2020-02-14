package nts.uk.ctx.hr.shared.dom.approval.rootstate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;

/**
 * 
 * @author laitv
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ApproverStateHrExport {
	
	private String approverID;
	
	private ApprovalBehaviorAtrHrExport approvalAtr;
	
	private String agentID;
	
	private String approverName;
	
	private String representerID;
	
	private String representerName;
	
	private GeneralDate approvalDate;
	
	private String approvalReason;
	
	public static ApproverStateHrExport fixData(Integer order){
		ApproverStateHrExport approver = null;
		ApprovalBehaviorAtrHrExport approvalAtr = ApprovalBehaviorAtrHrExport.UNAPPROVED;
		GeneralDate approvalDate = null;
		String approvalReason = "";
		switch(order){
			case 1://'000001
				approver = new ApproverStateHrExport("ae7fe82e-a7bd-4ce3-adeb-5cd403a9d570", approvalAtr, "", "大塚　太郎B",
						"", "", approvalDate, approvalReason);
				break;
			case 2://'000152 - '000006
				approver = new ApproverStateHrExport("0011fcd1-06ca-4fd7-b009-9f1cab325fcb", approvalAtr, "aeaa869d-fe62-4eb2-ac03-2dde53322cb5", "村井　なつみ",
						"aeaa869d-fe62-4eb2-ac03-2dde53322cb5", "あ", approvalDate, approvalReason);
				break;
			case 3://'000201 - '000200
				approver = new ApproverStateHrExport("90056534-0687-49c4-934b-da6ddbdbce6b", approvalAtr, "aee05667-67e1-4a6e-abc7-a73372bc01b2", "２ＮＦ　１",
						"aee05667-67e1-4a6e-abc7-a73372bc01b2", "ミナミ　アルプス　ビジネ", approvalDate, approvalReason);
				break;
			case 4://'000053
				approver = new ApproverStateHrExport("47b437b6-5f63-4094-bb57-afecf7a2e405", approvalAtr, "", "稲葉　隆弘",
						"", "", approvalDate, approvalReason);
				break;
			default://'SC0003
				approver = new ApproverStateHrExport("47bb629f-ff71-4c94-a06e-d94bfe8c3847", approvalAtr, "", "籔下　従業員①",
						"", "", approvalDate, approvalReason);
		}
		return approver;
	}
}
