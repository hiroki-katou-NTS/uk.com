package nts.uk.ctx.at.function.dom.alarm.checkcondition.mastercheck;

import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.ExtractionCondition;

/**
 * マスタチェックのアラームチェック条件
 */
@Getter
public class MasterCheckAlarmCheckCondition extends ExtractionCondition {

	private List<String> errorAlarmCheckId;
	
	@Override
	public void changeState(ExtractionCondition extractionCondition) {
		if (extractionCondition instanceof MasterCheckAlarmCheckCondition) {
			MasterCheckAlarmCheckCondition condition = (MasterCheckAlarmCheckCondition) extractionCondition;
			this.errorAlarmCheckId = condition.getErrorAlarmCheckId();
		}
	}

}
