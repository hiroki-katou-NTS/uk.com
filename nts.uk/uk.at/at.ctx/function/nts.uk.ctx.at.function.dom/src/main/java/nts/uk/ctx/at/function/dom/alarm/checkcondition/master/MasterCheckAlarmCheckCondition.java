package nts.uk.ctx.at.function.dom.alarm.checkcondition.master;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.ExtractionCondition;

/**
 * マスタチェックのアラームチェック条件
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MasterCheckAlarmCheckCondition extends ExtractionCondition {
	/**
	 * マスタチェックの固定抽出条件ID
	 */
	private String alarmCheckId;
	/**
	 *  チェック条件ID
	 */
	private List<String> errorAlarmCheckId;
	
	private List<Integer> fixedMasterCheckCondition = new ArrayList<Integer>();
	
	public MasterCheckAlarmCheckCondition(String alarmCheckId, List<String> errorAlarmCheckId) {
		super();
		this.alarmCheckId = alarmCheckId;
		this.errorAlarmCheckId = errorAlarmCheckId;
	}
	
	public MasterCheckAlarmCheckCondition(String alarmCheckId) {
		super();
		this.alarmCheckId = alarmCheckId;
	}
	
	@Override
	public void changeState(ExtractionCondition extractionCondition) {
		if (extractionCondition instanceof MasterCheckAlarmCheckCondition) {
			MasterCheckAlarmCheckCondition condition = (MasterCheckAlarmCheckCondition) extractionCondition;
			this.errorAlarmCheckId = condition.getErrorAlarmCheckId();
			this.fixedMasterCheckCondition = condition.getFixedMasterCheckCondition();
		}
	}

}
