package nts.uk.ctx.at.schedulealarm.dom.alarmcheck;

import java.util.ArrayList;
import java.util.List;

import lombok.val;

public class MainTest {

	public static void main(String[] args) {
		List<SubCondition> subConditions = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			subConditions.add(new SubCondition(new SubCode(String.valueOf(i)), new AlarmCheckMsgContent(
					new AlarmCheckMessage(String.valueOf(i)), new AlarmCheckMessage(String.valueOf(i))), "E"));

		}
		val subCon = new SubCondition(new SubCode("0"), new AlarmCheckMsgContent(
				new AlarmCheckMessage("0"), new AlarmCheckMessage("0")), "E");
		subConditions.remove(subCon);

		subConditions.add(subCon);
		
		
		subConditions.sort((a, b) -> {
			return a.getSubCode().v().compareTo(b.getSubCode().v());
		});
		
		System.out.println(subConditions.indexOf(subCon));
		
	}

}
