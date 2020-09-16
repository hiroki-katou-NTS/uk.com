package nts.uk.ctx.at.function.dom.alarm.checkcondition.appapproval;

import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.ExtractionCondition;

/**
 * 申請承認のアラームチェック条件
 */
@Getter
public class AppApprovalAlarmCheckCondition extends ExtractionCondition {

	private List<String> errorAlarmCheckId;

	public AppApprovalAlarmCheckCondition(List<String> errorAlarmCheckId) {
		this.errorAlarmCheckId = errorAlarmCheckId;
	}

	@Override
	public void changeState(ExtractionCondition extractionCondition) {
		if (extractionCondition instanceof AppApprovalAlarmCheckCondition) {
			AppApprovalAlarmCheckCondition condition = (AppApprovalAlarmCheckCondition) extractionCondition;
			this.errorAlarmCheckId = condition.getErrorAlarmCheckId();
		}
	}

}
