package nts.uk.ctx.at.function.dom.adapter.monthlycheckcondition.checkremainnumber;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CheckRemainNumberMonFunImport {
	/**ID*/
	private String errorAlarmCheckID;
	/**チェックする休暇*/
	private int checkVacation;
	
	private int checkOperatorType;
	
	private CompareRangeImport compareRangeEx;
	
	private CompareSingleValueImport compareSingleValueEx;
	
	private List<Integer> listItemID = new ArrayList<>();

	public CheckRemainNumberMonFunImport(String errorAlarmCheckID, int checkVacation, int checkOperatorType, CompareRangeImport compareRangeEx, CompareSingleValueImport compareSingleValueEx, List<Integer> listItemID) {
		super();
		this.errorAlarmCheckID = errorAlarmCheckID;
		this.checkVacation = checkVacation;
		this.checkOperatorType = checkOperatorType;
		this.compareRangeEx = compareRangeEx;
		this.compareSingleValueEx = compareSingleValueEx;
		this.listItemID = listItemID;
	}

	

	
	
}
