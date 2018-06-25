package nts.uk.ctx.at.function.dom.adapter.sysfixedcheckcondition;

import java.util.Optional;

import nts.uk.ctx.at.function.dom.alarm.alarmdata.ValueExtractAlarm;

public interface SysFixedCheckConMonAdapter {
	//1:月次未確認
	public Optional<ValueExtractAlarm>  checkAgreement(String employeeID,int yearMonth);
	//5:36協定のチェック
	public Optional<ValueExtractAlarm>  checkMonthlyUnconfirmed(String employeeID,int yearMonth);
}
