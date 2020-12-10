package nts.uk.ctx.workflow.pub.service.export;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDateTime;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class ApproverStateExport {
	
	private String approverID;
	
	private ApprovalBehaviorAtrExport approvalAtr;
	
	private String agentID;
	
	@Setter
	private String approverName;
	
	@Setter
	private String agentName;
	
	private String representerID;
	
	@Setter
	private String representerName;
	
	private GeneralDateTime approvalDate;
	
	private String approvalReason;
	
	private Integer approverInListOrder;
	
	public static ApproverStateExport fixData(Integer order){
		ApproverStateExport approver = null;
		ApprovalBehaviorAtrExport approvalAtr = ApprovalBehaviorAtrExport.UNAPPROVED;
		GeneralDateTime approvalDate = null;
		String approvalReason = "";
		switch(order){
			case 1://'000001
				approver = new ApproverStateExport("ae7fe82e-a7bd-4ce3-adeb-5cd403a9d570", approvalAtr, "", "大塚　太郎B", "",
						"", "", approvalDate, approvalReason, 1);
				break;
			case 2://'000152 - '000006
				approver = new ApproverStateExport("0011fcd1-06ca-4fd7-b009-9f1cab325fcb", approvalAtr, "aeaa869d-fe62-4eb2-ac03-2dde53322cb5", "村井　なつみ", "",
						"aeaa869d-fe62-4eb2-ac03-2dde53322cb5", "あ", approvalDate, approvalReason, 2);
				break;
			case 3://'000201 - '000200
				approver = new ApproverStateExport("90056534-0687-49c4-934b-da6ddbdbce6b", approvalAtr, "aee05667-67e1-4a6e-abc7-a73372bc01b2", "２ＮＦ　１", "",
						"aee05667-67e1-4a6e-abc7-a73372bc01b2", "ミナミ　アルプス　ビジネ", approvalDate, approvalReason, 3);
				break;
			case 4://'000053
				approver = new ApproverStateExport("47b437b6-5f63-4094-bb57-afecf7a2e405", approvalAtr, "", "稲葉　隆弘", "",
						"", "", approvalDate, approvalReason, 4);
				break;
			default://'SC0003
				approver = new ApproverStateExport("47bb629f-ff71-4c94-a06e-d94bfe8c3847", approvalAtr, "", "籔下　従業員①", "",
						"", "", approvalDate, approvalReason, 5);
		}
		return approver;
	}
}
