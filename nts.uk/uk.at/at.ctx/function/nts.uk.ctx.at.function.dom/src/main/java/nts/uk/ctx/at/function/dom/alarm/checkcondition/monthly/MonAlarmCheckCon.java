package nts.uk.ctx.at.function.dom.alarm.checkcondition.monthly;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.ExtractionCondition;

/**
 * 月次のアラームチェック条件	
 * @author tutk
 *
 */
@Getter
public class MonAlarmCheckCon extends ExtractionCondition {
	/**ID*/
	private String monAlarmCheckConID;
	/**固定抽出条件*/
	private List<Integer> listFixExtraMon = new ArrayList<>();
	/**任意抽出条件*/
	List<String> arbExtraCon = new ArrayList<>();
	public MonAlarmCheckCon(String monAlarmCheckConID, List<String> arbExtraCon) {
		super();
		this.monAlarmCheckConID = monAlarmCheckConID;
		this.arbExtraCon = arbExtraCon;
	}

	
	@Override	
	public void changeState(ExtractionCondition extractionCondition) {
		if (extractionCondition instanceof MonAlarmCheckCon) {
			MonAlarmCheckCon value = (MonAlarmCheckCon) extractionCondition;
			this.monAlarmCheckConID = value.monAlarmCheckConID;
			this.listFixExtraMon = value.listFixExtraMon;
			this.arbExtraCon = value.arbExtraCon;
		}
		
	}
}
