package nts.uk.ctx.at.function.dom.adapter.sysfixedcheckcondition;

import java.util.Optional;

import nts.uk.ctx.at.function.dom.alarm.alarmdata.ValueExtractAlarm;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;

public interface SysFixedCheckConMonAdapter {
	//5:36協定のチェック
	public Optional<ValueExtractAlarm>  checkAgreement(String employeeID,int yearMonth,int closureId,ClosureDate closureDate);
	//1:月次未確認
	public Optional<ValueExtractAlarm>  checkMonthlyUnconfirmed(String employeeID,int yearMonth);
}
