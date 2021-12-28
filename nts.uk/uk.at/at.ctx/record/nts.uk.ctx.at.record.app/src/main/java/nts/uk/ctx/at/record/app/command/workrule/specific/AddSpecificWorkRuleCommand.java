package nts.uk.ctx.at.record.app.command.workrule.specific;

import lombok.Data;
import nts.uk.ctx.at.record.app.find.workrule.specific.TimeOffVacationPriorityOrderDto;

/**
 * Add specific work rule data command
 * @author HoangNDH
 *
 */
@Data
public class AddSpecificWorkRuleCommand {
	// 総拘束時間の計算.計算方法
	private int constraintCalcMethod;
	// 総労働時間の上限値制御.設定
	private int upperLimitSet;
	
	// 時間休暇相殺優先順位
	private TimeOffVacationPriorityOrderDto offVacationPriorityOrder;
}
