package nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.sysfixedcheckcondition.monthlyunconfirmed;

import java.util.Optional;

import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.fixedcheckitem.checkprincipalunconfirm.ValueExtractAlarmWR;
/**
 * 1:月次未確認
 * @author tutk
 *
 */

public interface MonthlyUnconfirmedService {
	Optional<ValueExtractAlarmWR> checkMonthlyUnconfirmed(String employeeID,int yearMonth);
}
