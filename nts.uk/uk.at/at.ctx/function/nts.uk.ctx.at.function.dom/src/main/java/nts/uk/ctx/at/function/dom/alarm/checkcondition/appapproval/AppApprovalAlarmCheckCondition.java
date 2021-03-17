package nts.uk.ctx.at.function.dom.alarm.checkcondition.appapproval;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.ExtractionCondition;

/**
 * 申請承認のアラームチェック条件
 */
@Getter
@NoArgsConstructor
public class AppApprovalAlarmCheckCondition extends ExtractionCondition {
	
	private String approvalAlarmConID;

	private List<String> errorAlarmCheckId;
	
	private List<Integer> fixedApprovalCondition = new ArrayList<Integer>();

	public AppApprovalAlarmCheckCondition(List<String> errorAlarmCheckId) {
		super();
		this.errorAlarmCheckId = errorAlarmCheckId;
	}
	
	public AppApprovalAlarmCheckCondition(String approvalAlarmConID) {
		super();
		this.approvalAlarmConID = approvalAlarmConID;
	}

	@Override
	public void changeState(ExtractionCondition extractionCondition) {
		if (extractionCondition instanceof AppApprovalAlarmCheckCondition) {
			AppApprovalAlarmCheckCondition condition = (AppApprovalAlarmCheckCondition) extractionCondition;
			this.errorAlarmCheckId = condition.getErrorAlarmCheckId();
			this.fixedApprovalCondition = condition.getFixedApprovalCondition();
		}
	}

}
