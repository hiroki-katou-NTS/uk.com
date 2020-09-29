package nts.uk.ctx.at.schedulealarm.dom.alarmcheck;

import java.util.ArrayList;
import java.util.List;

import lombok.val;

public class AlarmCheckConditionScheduleHelper {

	public static List<SubCondition> createSubCondition(int size) {
		List<SubCondition> result = new ArrayList<>();
		for (int i = 0; i < size; i++) {

			val subCond = new SubCondition(new SubCode(String.valueOf(i)),
					new AlarmCheckMsgContent(new AlarmCheckMessage(String.valueOf(i)), new AlarmCheckMessage(String.valueOf(i))),
					"explanation" + String.valueOf(i));

			result.add(subCond);

		}
		
		return result;
	}
}
