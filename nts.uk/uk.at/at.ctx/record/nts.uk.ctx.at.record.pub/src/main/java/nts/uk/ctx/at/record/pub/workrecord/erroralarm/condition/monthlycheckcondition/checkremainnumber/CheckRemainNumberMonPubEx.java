package nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.monthlycheckcondition.checkremainnumber;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CheckRemainNumberMonPubEx {
	/**ID*/
	private String errorAlarmCheckID;
	/**チェックする休暇*/
	private int checkVacation;
	
	private int checkOperatorType;
	
	private CompareRangeEx compareRangeEx;
	
	private CompareSingleValueEx compareSingleValueEx;
	
	private List<Integer> listItemID;

	public CheckRemainNumberMonPubEx(String errorAlarmCheckID, int checkVacation, int checkOperatorType, CompareRangeEx compareRangeEx, CompareSingleValueEx compareSingleValueEx, List<Integer> listItemID) {
		super();
		this.errorAlarmCheckID = errorAlarmCheckID;
		this.checkVacation = checkVacation;
		this.checkOperatorType = checkOperatorType;
		this.compareRangeEx = compareRangeEx;
		this.compareSingleValueEx = compareSingleValueEx;
		this.listItemID = listItemID;
	}



	
	
}
