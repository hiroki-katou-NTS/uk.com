package nts.uk.ctx.at.function.dom.adapter.monthlyremain;

import java.util.List;
import java.util.Optional;

import lombok.Getter;


@Getter
public class CheckRemainNumberMonImport {
	/**ID*/
	private String errorAlarmCheckID;
	/**チェックする休暇*/
	private TypeCheckVacationImport checkVacation;
	//classify  single value and range value
	private CheckOperatorTypeImport checkOperatorType;
	/**特別休暇*/
	Optional<List<Integer>> listAttdID ;
	public CheckRemainNumberMonImport(String errorAlarmCheckID, TypeCheckVacationImport checkVacation, CheckOperatorTypeImport checkOperatorType, List<Integer> listAttdID) {
		super();
		this.errorAlarmCheckID = errorAlarmCheckID;
		this.checkVacation = checkVacation;
		this.checkOperatorType = checkOperatorType;
		this.listAttdID = Optional.ofNullable(listAttdID);
	}	
	
}
