package nts.uk.ctx.at.function.dom.alarm.checkcondition.multimonth;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.ExtractionCondition;

/**
 * 
 * @author Hiep.TH
 *
 */

//複数月のアラームチェック条件
@Getter
@AllArgsConstructor
public class MulMonAlarmCond extends ExtractionCondition {
	
	//ID
	private String mulMonAlarmCondID;
	
	//勤務実績のエラーアラームチェックID
	private List<String> errorAlarmCondIds = new ArrayList<>();;
	
	@Override
	public void changeState(ExtractionCondition extractionCondition) {
		if (extractionCondition instanceof MulMonAlarmCond) {
			MulMonAlarmCond value = (MulMonAlarmCond) extractionCondition;
			this.errorAlarmCondIds = value.errorAlarmCondIds;
		}
	}
}
