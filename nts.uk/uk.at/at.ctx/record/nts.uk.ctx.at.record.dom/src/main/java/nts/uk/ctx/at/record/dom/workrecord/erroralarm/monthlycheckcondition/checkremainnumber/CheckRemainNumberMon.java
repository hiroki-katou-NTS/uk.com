package nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.checkremainnumber;

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
	
	public CheckRemainNumberMon(String errorAlarmCheckID, TypeCheckVacation checkVacation, CheckedCondition checkCondition) {
		super();
		this.errorAlarmCheckID = errorAlarmCheckID;
		this.checkVacation = checkVacation;
		this.checkCondition = checkCondition;
	}
	
	
	
	

}
