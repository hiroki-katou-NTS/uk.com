package nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.checkremainnumber;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.CheckedCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.TypeCheckVacation;
/**
 * 月別実績の残数チェック
 * @author tutk
 *
 */

@Getter
public class CheckRemainNumberMon extends AggregateRoot {
	/**ID*/
	private String errorAlarmCheckID;
	/**チェックする休暇*/
	private TypeCheckVacation checkVacation;
	/**チェック条件*/
	private CheckedCondition checkCondition;
	//classify  single value and range value
	private CheckOperatorType checkOperatorType;
	/**特別休暇*/
	Optional<List<Integer>> listAttdID ;
	public CheckRemainNumberMon(String errorAlarmCheckID, TypeCheckVacation checkVacation, CheckedCondition checkCondition, CheckOperatorType checkOperatorType, List<Integer> listAttdID) {
		super();
		this.errorAlarmCheckID = errorAlarmCheckID;
		this.checkVacation = checkVacation;
		this.checkCondition = checkCondition;
		this.checkOperatorType = checkOperatorType;
		this.listAttdID = Optional.ofNullable(listAttdID);
	}	
	

	

}
