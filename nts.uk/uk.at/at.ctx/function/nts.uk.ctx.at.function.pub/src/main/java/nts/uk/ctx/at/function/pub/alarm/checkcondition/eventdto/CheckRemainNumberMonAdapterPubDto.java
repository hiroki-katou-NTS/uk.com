package nts.uk.ctx.at.function.pub.alarm.checkcondition.eventdto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CheckRemainNumberMonAdapterPubDto {
	/**ID*/
	private String errorAlarmCheckID;
	/**チェックする休暇*/
	private int checkVacation;
	
	private int checkOperatorType;
	
	private CompareRangeAdapterPubDto compareRangeEx;
	
	private CompareSingleValueAdapterPubDto compareSingleValueEx;
	
	private List<Integer> listItemID = new ArrayList<>();

	public CheckRemainNumberMonAdapterPubDto(String errorAlarmCheckID, int checkVacation, int checkOperatorType, CompareRangeAdapterPubDto compareRangeEx, CompareSingleValueAdapterPubDto compareSingleValueEx, List<Integer> listItemID) {
		super();
		this.errorAlarmCheckID = errorAlarmCheckID;
		this.checkVacation = checkVacation;
		this.checkOperatorType = checkOperatorType;
		this.compareRangeEx = compareRangeEx;
		this.compareSingleValueEx = compareSingleValueEx;
		this.listItemID = listItemID;
	}

	

	
	
}
